package com.w77996.seed.house.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.w77996.seed.house.core.annotation.NeedToken;
import com.w77996.seed.house.core.base.BaseController;
import com.w77996.seed.house.core.config.WxPayConfigurer;
import com.w77996.seed.house.core.constant.OrderConstant;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.OrderBean;
import com.w77996.seed.house.model.bean.ProductBean;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.dto.CreateOrderDto;
import com.w77996.seed.house.service.IOrderService;
import com.w77996.seed.house.service.IProductService;
import com.w77996.seed.house.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;

/**
 * @author w77996
 * @description
 * @date 2020/5/15 23:07
 */
@Api("微信支付")
@RestController
@Slf4j
@RequestMapping("/mp/pay")
public class WxMpPaymentController extends BaseController {

    @Autowired
    private WxPayService payService;

    @Autowired
    private WxPayConfigurer payConfig;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Value("${snowflake.workId}")
    Integer workId;

    @Value("${snowflake.datacenterId}")
    Long datacenterId;


    /**
     * 用于返回预支付的结果 WxMpPrepayIdResult，一般不需要使用此接口
     *
     * @param response
     * @param request
     */
//    @GetMapping(value = "getPrepayIdResult")
//    public void getPrepayId(HttpServletResponse response,
//                            HttpServletRequest request) throws WxPayException {
//        WxPayUnifiedOrderRequest payInfo = WxPayUnifiedOrderRequest.newBuilder()
//                .openid(request.getParameter("openid"))
//                .outTradeNo(request.getParameter("out_trade_no"))
//                .totalFee(Integer.valueOf(request.getParameter("total_fee")))
//                .body(request.getParameter("body"))
//                .tradeType(request.getParameter("trade_type"))
//                .spbillCreateIp(request.getParameter("spbill_create_ip"))
//                .notifyUrl("")
//                .build();
//        this.logger
//                .info("PartnerKey is :" + this.payConfig.getMchKey());
//        WxPayUnifiedOrderResult result = this.payService.unifiedOrder(payInfo);
//        this.logger.info(new Gson().toJson(result));
//        renderString(response, result);
//    }

//    /**
//     * 返回前台H5调用JS支付所需要的参数，公众号支付调用此接口
//     *
//     * @param response
//     * @param request
//     */
//    @RequestMapping(value = "getJSSDKPayInfo")
//    public void getJSSDKPayInfo(@RequestParam("openid") String openId,
//                                @RequestParam("out_trade_no") String outTradeNo,
//                                @RequestParam("total_fee") String total_fee,
//                                @RequestParam("body")String body,
//                                @RequestParam("trade_type") String trade_type,
//                                @RequestParam("spbill_create_ip") String spbill_create_ip) {
//        Result returnModel = new Result();
//        WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
//                .openid(openId)
//                .outTradeNo(outTradeNo)
//                .totalFee(Integer.valueOf(total_fee))
//                .body(body)
//                .tradeType(trade_type)
//                .spbillCreateIp(spbill_create_ip)
//                .notifyUrl("// TODO 填写通知回调地址")
//                .build();
//
//        try {
//            Map<String, String> payInfo = this.payService.get(prepayInfo);
//            returnModel.setResult(true);
//            returnModel.setDatum(payInfo);
//            renderString(response, returnModel);
//        } catch (WxPayException e) {
//            returnModel.setResult(false);
//            returnModel.setReason(e.getErrCodeDes());
//            renderString(response, returnModel);
//            this.logger.error(e.getErrCodeDes());
//        }
//    }

//    /**
//     * 微信通知支付结果的回调地址，notify_url
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "getJSSDKCallbackData")
//    public void getJSSDKCallbackData(HttpServletRequest request,
//                                     HttpServletResponse response) {
//        try {
//            synchronized (this) {
//                Map<String, String> kvm = XMLUtil.parseRequestXmlToMap(request);
//                if (SignUtils.checkSign(kvm, null, this.payConfig.getMchKey())) {
//                    if (kvm.get("result_code").equals("SUCCESS")) {
//                        //TODO(user) 微信服务器通知此回调接口支付成功后，通知给业务系统做处理
//                        logger.info("out_trade_no: " + kvm.get("out_trade_no") + " pay SUCCESS!");
//                        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>");
//                    } else {
//                        this.logger.error("out_trade_no: "
//                                + kvm.get("out_trade_no") + " result_code is FAIL");
//                        response.getWriter().write(
//                                "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[result_code is FAIL]]></return_msg></xml>");
//                    }
//                } else {
//                    response.getWriter().write(
//                            "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[check signature FAIL]]></return_msg></xml>");
//                    this.logger.error("out_trade_no: " + kvm.get("out_trade_no")
//                            + " check signature FAIL");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(value = "entPay")
//    public void payToIndividual(HttpServletResponse response,
//                                HttpServletRequest request) {
//        EntPayRequest wxEntPayRequest = new EntPayRequest();
//        wxEntPayRequest.setAppid(payConfig.getAppId());
//        wxEntPayRequest.setMchId(payConfig.getMchId());
//        wxEntPayRequest.setNonceStr(Sha1Util.getNonceStr());
//        wxEntPayRequest.setPartnerTradeNo(request.getParameter("partner_trade_no"));
//        wxEntPayRequest.setOpenid(request.getParameter("openid"));
//        wxEntPayRequest.setCheckName("NO_CHECK");
//        wxEntPayRequest.setAmount(Integer.valueOf(request.getParameter("amount")));
//        wxEntPayRequest.setDescription(request.getParameter("desc"));
//        wxEntPayRequest.setSpbillCreateIp(request.getParameter("spbill_create_ip"));
//
//        try {
//            EntPayResult wxEntPayResult = payService.getEntPayService().entPay(wxEntPayRequest);
//            if ("SUCCESS".equals(wxEntPayResult.getResultCode().toUpperCase())
//                    && "SUCCESS".equals(wxEntPayResult.getReturnCode().toUpperCase())) {
//                this.logger.info("企业对个人付款成功！\n付款信息：\n" + wxEntPayResult.toString());
//            } else {
//                this.logger.error("err_code: " + wxEntPayResult.getErrCode()
//                        + "  err_code_des: " + wxEntPayResult.getErrCodeDes());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @ApiOperation(value = "支付回调通知处理")
    @PostMapping("/notify/order")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = this.payService.parseOrderNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        String resultCode = notifyResult.getResultCode();
        orderService.payWxNotify(notifyResult);
        return WxPayNotifyResponse.success("成功");
    }

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param request 统一下单请求参数
     * @param <T>     请使用{@link com.github.binarywang.wxpay.bean.order}包下的类
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     */
    @ApiOperation(value = "统一下单，并组装所需支付参数")
    @PostMapping("/createOrder")
    public <T> T createOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.payService.createOrder(request);
    }

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param request 统一下单请求参数
     * @param <T>     请使用{@link com.github.binarywang.wxpay.bean.order}包下的类
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     */
    @NeedToken
    @ApiOperation(value = "统一下单，并组装所需支付参数")
    @PostMapping("/js/createOrder")
    public Result createOrder(@RequestBody CreateOrderDto createOrderDto) throws WxPayException {
        log.info("[createOrder]参数：{}", JSON.toJSONString(createOrderDto));
        Long userId = getUserIdByToken();
        UserBean userBean = userService.getUserByUserId(userId);
        Assert.notNull(userBean, "用户不存在请重新登陆");
        ProductBean productBean = productService.findById(createOrderDto.getProductId());
        Assert.notNull(productBean, "套餐不存在");
        Long orderNo = IdUtil.getSnowflake(workId, datacenterId).nextId();
        final WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                .tradeType("JSAPI")
                //调起支付的人的 openId
                .openid(userBean.getMpOpenId())
                //订单编号
                .outTradeNo(orderNo + "")
                //订单金额
                .totalFee(productBean.getPrice())
                //商品描述
                .body(productBean.getProductName())
                //获取本地IP
                .spbillCreateIp(InetAddress.getLoopbackAddress().getHostAddress())
                //回调的 URL 地址
                .notifyUrl("回调地址")
                .build();
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = null;
        OrderBean orderBean = OrderBean.builder()
                .orderNo(orderNo)
                .userId(userId)
                .name(productBean.getProductName())
                .orderType(OrderConstant.OrderType.Recharge.getType())
                .status(OrderConstant.STATUS.WAIT_PAY.getStatus())
                .totalFee(productBean.getPrice())
                .payType(OrderConstant.PayType.WX.getType())
                .goodsCount(productBean.getAmount())
                .createTime(DateUtil.date())
                .build();
        //创建订单
        orderService.createOrder(orderBean);
        try {
            wxPayUnifiedOrderResult = payService.unifiedOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            e.printStackTrace();
            throw new RuntimeException("微信支付调起失败！");
        }
        return ResultGenerator.genSuccessResult(this.payService.createOrder(wxPayUnifiedOrderRequest));
    }

    /**
     * 统一下单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param request 请求对象，注意一些参数如appid、mchid等不用设置，方法内会自动从配置对象中获取到（前提是对应配置中已经设置）
     */
    @ApiOperation(value = "原生的统一下单接口")
    @PostMapping("/unifiedOrder")
    public WxPayUnifiedOrderResult unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.payService.unifiedOrder(request);
    }

    /**
     * <pre>
     * 企业付款业务是基于微信支付商户平台的资金管理能力，为了协助商户方便地实现企业向个人付款，针对部分有开发能力的商户，提供通过API完成企业付款的功能。
     * 比如目前的保险行业向客户退保、给付、理赔。
     * 企业付款将使用商户的可用余额，需确保可用余额充足。查看可用余额、充值、提现请登录商户平台“资金管理”https://pay.weixin.qq.com/进行操作。
     * 注意：与商户微信支付收款资金并非同一账户，需要单独充值。
     * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers
     * </pre>
     *
     * @param request 请求对象
     */
    @ApiOperation(value = "企业付款到零钱")
    @PostMapping("/entPay")
    public EntPayResult entPay(@RequestBody EntPayRequest request) throws WxPayException {
        return this.payService.getEntPayService().entPay(request);
    }

    /**
     * <pre>
     * 查询企业付款API
     * 用于商户的企业付款操作进行结果查询，返回付款操作详细结果。
     * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo
     * </pre>
     *
     * @param partnerTradeNo 商户订单号
     */
    @ApiOperation(value = "查询企业付款到零钱的结果")
    @GetMapping("/queryEntPay/{partnerTradeNo}")
    public EntPayQueryResult queryEntPay(@PathVariable String partnerTradeNo) throws WxPayException {
        return this.payService.getEntPayService().queryEntPay(partnerTradeNo);
    }

}
