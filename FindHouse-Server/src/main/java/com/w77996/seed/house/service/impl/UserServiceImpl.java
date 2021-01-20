package com.w77996.seed.house.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.w77996.seed.house.core.base.ServiceException;
import com.w77996.seed.house.core.constant.UserConst;
import com.w77996.seed.house.dao.UserMapper;
import com.w77996.seed.house.dao.VipMapper;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import com.w77996.seed.house.model.bean.user.VipBean;
import com.w77996.seed.house.model.bean.user.VipCountBean;
import com.w77996.seed.house.model.view.UserView;
import com.w77996.seed.house.service.IUserService;
import com.w77996.seed.house.core.base.AbstractService;
import com.w77996.seed.house.service.IVipCountService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Created by CodeGenerator on 2020/05/16.
 * @author w77996
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserBean> implements IUserService {
    @Resource
    private UserMapper userMapper;

//    @Resource
//    private IVipService vipService;
    @Resource
    private IVipCountService vipCountService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${snowflake.workId}")
    Integer workId;

    @Value("${snowflake.datacenterId}")
    Long datacenterId;

    @Override
    public UserBean getUserByUnionId(String unionId) {
        return userMapper.getUserByUnionId(unionId);
    }

    @Override
    public UserBean getUserByMpOpenId(String openId) {
        return userMapper.getUserByMpOpenId(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserView registerMpUser(WxMpUser wxMpUser) {
        UserBean userBean = UserBean.builder()
                .id(IdUtil.getSnowflake(workId, datacenterId).nextId())
                .nickname(wxMpUser.getNickname())
                .headImage(wxMpUser.getHeadImgUrl())
                .sex(wxMpUser.getSex())
                .mpOpenId(wxMpUser.getOpenId())
                .unionId(wxMpUser.getUnionId())
                .createTime(DateUtil.date())
                .updateTime(DateUtil.date()).build();
        userBean.addFlag(UserConst.Flag.IS_OPEN_PUSH);
        userBean.addFlag(UserConst.Flag.IS_VIP);
        int ret = userMapper.insert(userBean);
        UserView userView = new UserView();
        BeanUtil.copyProperties(userBean, userView);
        //三天免费试用
        userView.setFirstRegister(true);
        //查看用户是否为vip
        //是否关注公众号
        boolean subscribe = wxMpUser.getSubscribe();
        userView.setSubscribe(subscribe);
        if (!subscribe) {
            return userView;
        }
        userView.setOpenMsgPush(true);
        userView.setThirdDayFree(true);
        //查询订阅地区
        UserSubscribeBean userSubscribeBean = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userBean.getId())), UserSubscribeBean.class);
        userView.setSubscribeInfo(userSubscribeBean);
        vipCountService.registerVip(userBean.getId());
        VipCountBean vipDto = vipCountService.getVipByUserId(userBean.getId());
        userView.setLeftDate(vipDto.getCount() + "");
        return userView;


    }

    @Override
    public UserBean getUserByUserId(Long id) {
        UserBean userBean = userMapper.getUserByUserId(id);
        if (Objects.isNull(userBean)) {
            throw new ServiceException("用户不存在");
        }
        return userBean;
    }

    @Override
    public UserView getUserLoginInfoById(Long userId) {
        UserBean userBean = this.getUserByUserId(userId);
        if (Objects.isNull(userBean)) {
            return null;
        }
        UserView userView = new UserView();
        BeanUtil.copyProperties(userBean, userView);
        long betweenDay = DateUtil.between(userBean.getCreateTime(), new Date(), DateUnit.DAY);
        if (betweenDay <= 3) {
            userView.setThirdDayFree(true);
        }
        //判断用户是否开启订阅
        boolean isOpenPush = UserConst.Flag.isFlag(UserConst.Flag.IS_OPEN_PUSH, userBean.getFlag());
        boolean isVip = UserConst.Flag.isFlag(UserConst.Flag.IS_VIP, userBean.getFlag());
        if (!isVip) {
            userView.setNeedCharge(true);
        } else {
            userView.setVip(true);
        }
        userView.setOpenMsgPush(isOpenPush);
        userView.setSubscribe(true);
        UserSubscribeBean userSubscribeBean = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userBean.getId())), UserSubscribeBean.class);
        userView.setSubscribeInfo(userSubscribeBean);
        if (Objects.isNull(userSubscribeBean)) {
            userView.setFirstRegister(true);
        }
//        VipDto vipDto = vipService.getVipByUserId(userId);
        VipCountBean vipDto = vipCountService.getVipByUserId(userId);
        userView.setLeftDate(vipDto.getCount() + "");
        return userView;
    }

    @Override
    public UserView getUserViewByCode(UserBean userBean, WxMpUser wxMpUser) {
        UserView userView = new UserView();
        BeanUtil.copyProperties(userBean, userView);
        long betweenDay = DateUtil.between(userBean.getCreateTime(), new Date(), DateUnit.DAY);
        if (betweenDay <= 3) {
            userView.setThirdDayFree(true);
        }
        //判断用户是否开启订阅
        boolean isOpenPush = UserConst.Flag.isFlag(UserConst.Flag.IS_OPEN_PUSH, userBean.getFlag());
        userView.setOpenMsgPush(isOpenPush);
        boolean isVip = UserConst.Flag.isFlag(UserConst.Flag.IS_VIP, userBean.getFlag());
        if (!isVip) {
            userView.setNeedCharge(true);
        } else {
            userView.setVip(true);
        }
        userView.setSubscribe(wxMpUser.getSubscribe());
        UserSubscribeBean userSubscribeBean = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userBean.getId())), UserSubscribeBean.class);
        userView.setSubscribeInfo(userSubscribeBean);
        if (Objects.isNull(userSubscribeBean)) {
            userView.setFirstRegister(true);
        }
        VipCountBean vipDto = vipCountService.getVipByUserId(userBean.getId());
        userView.setLeftDate(vipDto.getCount() + "");
        return userView;
    }


    @Override
    public  List<UserBean> findByUserIdList(List<Long> userIds) {
        return userMapper.findByUserIdList(userIds);
    }
}
