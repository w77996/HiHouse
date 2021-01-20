package com.w77996.seed.house.service;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.model.bean.OrderBean;
import com.w77996.seed.house.core.base.Service;


/**
 * Created by CodeGenerator on 2020/05/16.
 */
public interface IOrderService extends Service<OrderBean> {

    Result payWxNotify(WxPayOrderNotifyResult notifyResult);


    void createOrder(OrderBean orderBean);
}
