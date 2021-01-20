//package com.w77996.seed.house.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUnit;
//import cn.hutool.core.date.DateUtil;
//import com.w77996.seed.house.core.constant.UserConst;
//import com.w77996.seed.house.core.constant.VipConstant;
//import com.w77996.seed.house.dao.UserMapper;
//import com.w77996.seed.house.dao.VipMapper;
//import com.w77996.seed.house.model.bean.user.UserBean;
//import com.w77996.seed.house.model.bean.user.VipBean;
//import com.w77996.seed.house.model.dto.VipDto;
//import com.w77996.seed.house.service.IUserService;
//import com.w77996.seed.house.service.IVipService;
//import com.w77996.seed.house.core.base.AbstractService;
//import io.swagger.models.auth.In;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * Created by CodeGenerator on 2020/10/13.
// */
//@Slf4j
//@Service
//@Transactional
//public class VipServiceImpl extends AbstractService<VipBean> implements IVipService {
//    @Resource
//    private VipMapper vipMapper;
//
//    @Resource
//    private IUserService userService;
//
//    @Override
//    public void handlerExpire() {
//        log.info("handlerExpire");
//        List<VipBean> vipBeanList = vipMapper.findAllExpire();
//        if (CollectionUtil.isNotEmpty(vipBeanList)) {
//            for (VipBean vipBean : vipBeanList) {
//                if (vipBean.getExpireTime().before(DateUtil.date())) {
//                    //过期
//                    vipBean.setStatus(VipConstant.Status.EXPIRE.getValue());
//                    vipMapper.updateByPrimaryKey(vipBean);
//                    UserBean userBean = userService.getUserByUserId(vipBean.getUserId());
//                    userBean.removeFlag(UserConst.Flag.IS_VIP);
//                    userService.update(userBean);
//                }
//            }
//        }
//
//    }
//
//
//    @Override
//    public VipDto getVipByUserId(Long userId) {
//        VipBean vipBean = vipMapper.selectByUserId(userId);
//        if (Objects.isNull(vipBean)) {
////            VipDto vipDto = VipDto.builder().leftDate("0").build();
//            VipDto vipDto = new VipDto();
//            vipDto.setLeftDate("0");
//            return vipDto;
//        }
//
//        VipDto vipDto = new VipDto();
//        BeanUtil.copyProperties(vipBean, vipDto);
//        boolean before = vipBean.getExpireTime().before(DateUtil.date());
//        if (before) {
//
//            vipDto.setLeftDate("0");
//        } else {
//            long between = DateUtil.between(vipBean.getExpireTime(), DateUtil.date(), DateUnit.DAY);
//            vipDto.setLeftDate(between + "");
//        }
//
//        return vipDto;
//    }
//
//    @Override
//    public int insertVip(Long userId, Integer day) {
//        int result = 0;
//        VipBean vipBean = vipMapper.selectByUserId(userId);
//        DateTime expireTime = DateUtil.offsetDay(DateUtil.date(), day);
//        if (Objects.isNull(vipBean)) {
//            VipBean newVip = VipBean.builder()
//                    .status(VipConstant.Status.VIP.getValue())
//                    .userId(userId)
//                    .expireBuyTime(expireTime)
//                    .expireTime(expireTime)
//                    .createTime(DateUtil.date())
//                    .updateTime(DateUtil.date())
//                    .build();
//            result = vipMapper.insert(newVip);
//        } else {
//            boolean before = vipBean.getExpireTime().before(DateUtil.date());
//            if (before) {
//                vipBean.setStatus(VipConstant.Status.VIP.getValue());
//                vipBean.setExpireBuyTime(expireTime);
//                vipBean.setExpireTime(expireTime);
//                vipBean.setUpdateTime(DateUtil.date());
//
//            } else {
//                //加时间
//                DateTime addExpireTime = DateUtil.offsetDay(vipBean.getExpireTime(), day);
//                vipBean.setStatus(VipConstant.Status.VIP.getValue());
//                vipBean.setExpireBuyTime(addExpireTime);
//                vipBean.setExpireTime(addExpireTime);
//                vipBean.setUpdateTime(DateUtil.date());
//            }
//            vipBean.setStatus(VipConstant.Status.VIP.getValue());
//            result = vipMapper.updateByPrimaryKey(vipBean);
//        }
//        return result;
//    }
//
//    @Override
//    public int registerVip(Long userId) {
//        int result = 0;
//        VipBean vipBean = vipMapper.selectByUserId(userId);
//        DateTime expireTime = DateUtil.offsetDay(DateUtil.date(), 3);
//        if (Objects.isNull(vipBean)) {
//            VipBean newVip = VipBean.builder()
//                    .status(VipConstant.Status.VIP.getValue())
//                    .userId(userId)
//                    .expireBuyTime(expireTime)
//                    .expireTime(expireTime)
//                    .createTime(DateUtil.date())
//                    .updateTime(DateUtil.date())
//                    .build();
//            result = vipMapper.insert(newVip);
//        } else {
//            log.error("用户已注册，赠送过vip");
//        }
//        return result;
//    }
//
//    @Override
//    public VipBean selectByUserId(Long userId) {
//        return vipMapper.selectByUserId(userId);
//    }
//}
