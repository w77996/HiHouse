package com.w77996.seed.house.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.w77996.seed.house.core.base.AbstractService;
import com.w77996.seed.house.core.constant.UserConst;
import com.w77996.seed.house.core.constant.VipConstant;
import com.w77996.seed.house.dao.VipCountMapper;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.bean.user.VipCountBean;
import com.w77996.seed.house.service.IUserService;
import com.w77996.seed.house.service.IVipCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author CodeGenerator
 * @date 2020/11/30
 */
@Slf4j
@Service
@Transactional
public class VipCountServiceImpl extends AbstractService<VipCountBean> implements IVipCountService {
    @Resource
    private VipCountMapper vipCountMapper;

    @Autowired
    private IUserService userService;

    @Override
    public void handlerExpire() {
        log.info("handlerExpire");
        List<VipCountBean> vipBeanList = vipCountMapper.findAllExpire();
        if (CollectionUtil.isNotEmpty(vipBeanList)) {
            for (VipCountBean vipBean : vipBeanList) {
                if (vipBean.getCount() == 0) {
                    //过期
                    vipBean.setStatus(VipConstant.Status.EXPIRE.getValue());
                    vipCountMapper.updateByPrimaryKey(vipBean);
                    UserBean userBean = userService.getUserByUserId(vipBean.getUserId());
                    userBean.removeFlag(UserConst.Flag.IS_VIP);
                    userService.update(userBean);
                }
            }
        }

    }

    @Override
    public VipCountBean getVipByUserId(Long userId) {
        VipCountBean query = VipCountBean.builder().userId(userId).build();
        VipCountBean vipCountBean = vipCountMapper.selectOne(query);
        if (Objects.isNull(vipCountBean)) {
            vipCountBean = VipCountBean.builder().userId(userId).count(0).build();
            return vipCountBean;
        }
        return vipCountBean;
    }

    @Override
    public int insertVip(Long userId, Integer count) {
        log.info("vip add userId {} count {}",userId,count);
        int result = 0;
        VipCountBean query = VipCountBean.builder().userId(userId).build();
        VipCountBean vipCountBean = vipCountMapper.selectOne(query);
        if (Objects.isNull(vipCountBean)) {
            vipCountBean = VipCountBean.builder()
                    .userId(userId)
                    .count(count)
                    .status(VipConstant.Status.VIP.getValue())
                    .createTime(DateUtil.date())
                    .updateTime(DateUtil.date())
                    .build();
            result = vipCountMapper.insert(vipCountBean);
        } else {
            if (vipCountBean.getCount() < 0) {
                vipCountBean.setCount(0);
            }
            int countRet = vipCountBean.getCount() + count;
            vipCountBean.setStatus(VipConstant.Status.VIP.getValue());
            vipCountBean.setCount(countRet);
            vipCountBean.setUpdateTime(DateUtil.date());
            result = vipCountMapper.updateByPrimaryKey(vipCountBean);
        }
        return result;
    }

    @Override
    public int registerVip(Long userId) {
        return this.insertVip(userId, 100);
    }

    @Override
    public VipCountBean selectByUserId(Long userId) {
        VipCountBean query = VipCountBean.builder().userId(userId).build();
        VipCountBean vipCountBean = vipCountMapper.selectOne(query);
        if (Objects.isNull(vipCountBean)) {
            vipCountBean = VipCountBean.builder().userId(userId).count(0).build();
            return vipCountBean;
        }
        return vipCountBean;
    }

    @Override
    public int reduceCount(Long userId) {
        return vipCountMapper.reduceCount(userId);
    }
}
