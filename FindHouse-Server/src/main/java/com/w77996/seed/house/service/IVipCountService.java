package com.w77996.seed.house.service;


import com.w77996.seed.house.core.base.Service;
import com.w77996.seed.house.model.bean.user.VipCountBean;

/**
 * Created by CodeGenerator on 2020/11/30.
 */
public interface IVipCountService extends Service<VipCountBean> {

    void handlerExpire();


    VipCountBean getVipByUserId(Long userId);


    int insertVip(Long userId,Integer count);

    int registerVip(Long userId);

    VipCountBean selectByUserId(Long userId);

    int reduceCount(Long userId);
}
