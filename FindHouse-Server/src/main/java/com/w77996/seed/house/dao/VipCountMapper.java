package com.w77996.seed.house.dao;

import com.w77996.seed.house.core.base.Mapper;
import com.w77996.seed.house.model.bean.user.VipCountBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VipCountMapper extends Mapper<VipCountBean> {
    int reduceCount(@Param("userId") Long userId);

    List<VipCountBean> findAllExpire();
}