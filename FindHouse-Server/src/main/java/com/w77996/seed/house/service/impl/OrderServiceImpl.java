package com.w77996.seed.house.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.w77996.seed.house.core.constant.OrderConstant;
import com.w77996.seed.house.core.constant.UserConst;
import com.w77996.seed.house.core.constant.VipConstant;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.dao.OrderMapper;
import com.w77996.seed.house.dao.UserMapper;
import com.w77996.seed.house.dao.VipMapper;
import com.w77996.seed.house.model.bean.OrderBean;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.bean.user.VipBean;
import com.w77996.seed.house.service.IOrderService;
import com.w77996.seed.house.core.base.AbstractService;
import com.w77996.seed.house.service.IUserService;
import com.w77996.seed.house.service.IVipCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;


/**
 * Created by CodeGenerator on 2020/05/16.
 */
@Slf4j
@Service
@Transactional
public class OrderServiceImpl extends AbstractService<OrderBean> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IUserService userService;

//    @Resource
//    private IVipService vipService;
    @Resource
    private IVipCountService vipCountService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result payWxNotify(WxPayOrderNotifyResult notifyResult) {
        log.info("收到回调通知{}",JSON.toJSONString(notifyResult));
        OrderBean orderBean = orderMapper.selectByOrderNo(Long.parseLong(notifyResult.getOutTradeNo()));
        if (Objects.isNull(orderBean)) {
            log.error("订单为空错误 {}", JSON.toJSONString(notifyResult));
            return ResultGenerator.genSuccessResult();
        }
        if(orderBean.getStatus() == OrderConstant.STATUS.PAY.getStatus().intValue()){
            log.error("订单重复通知 {}", JSON.toJSONString(notifyResult));
            return ResultGenerator.genSuccessResult();
        }
        orderBean.setNotifyTime(DateUtil.date());
        orderBean.setUpdateTime(DateUtil.date());
        orderBean.setStatus(OrderConstant.STATUS.PAY.getStatus());
        orderMapper.updateByPrimaryKey(orderBean);
//        int i = vipService.insertVip(orderBean.getUserId(), orderBean.getGoodsCount());
        int i = vipCountService.insertVip(orderBean.getUserId(), orderBean.getGoodsCount());
        if (i > 0) {
            UserBean userBean = userService.getUserByUserId(orderBean.getUserId());
            userBean.addFlag(UserConst.Flag.IS_VIP);
            userBean.setUpdateTime(DateUtil.date());
            userService.update(userBean);
        } else {
            log.error("订单购买错误{}", orderBean.getOrderNo());
        }
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public void createOrder(OrderBean orderBean) {
        orderMapper.insert(orderBean);
    }
}
