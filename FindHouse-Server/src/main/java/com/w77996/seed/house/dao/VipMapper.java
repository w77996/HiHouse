package com.w77996.seed.house.dao;

import com.w77996.seed.house.core.base.Mapper;
import com.w77996.seed.house.model.bean.user.VipBean;

import java.util.List;

public interface VipMapper extends Mapper<VipBean> {
    VipBean selectByUserId(Long userId);

    List<VipBean> findAllExpire();
}