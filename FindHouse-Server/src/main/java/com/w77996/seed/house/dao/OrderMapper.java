package com.w77996.seed.house.dao;

import com.w77996.seed.house.core.base.Mapper;
import com.w77996.seed.house.model.bean.OrderBean;

public interface OrderMapper extends Mapper<OrderBean> {
    OrderBean selectByOrderNo(long orderNo);
}