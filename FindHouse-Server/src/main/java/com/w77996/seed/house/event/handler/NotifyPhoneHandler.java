package com.w77996.seed.house.event.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.core.constant.UserConst;
import com.w77996.seed.house.event.NotifyPhoneDto;
import com.w77996.seed.house.model.bean.PushHistoryBean;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.w77996.seed.house.service.IPushHistoryService;
import com.w77996.seed.house.service.IUserService;
import com.w77996.seed.house.service.IVipCountService;
import com.w77996.seed.house.utils.CrawlerDocumentTool;
import com.w77996.seed.house.utils.CrawlerParseTool;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author w77996
 * @ClassName NotifyPhoneHandler
 * @Description
 * @date 2020/11/14 21:19
 */
@Slf4j
@Component
public class NotifyPhoneHandler {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVipCountService vipCountService;

    @Autowired
    private CrawlerDocumentTool crawlerDocumentTool;

    @Autowired
    private CrawlerParseTool crawlerParseTool;

    @Autowired
    private IPushHistoryService pushHistoryService;

    @Async
    @EventListener
    public void notifyPhoneHandler(NotifyPhoneDto event) {
        log.info("收到notifyPhoneHandler{}", JSON.toJSONString(event));
        //判断是否需要推送
        String pushConfigStr = redisTemplate.opsForValue().get(RedisConstant.PUSH_CONFIG);
        if (StrUtil.isBlank(pushConfigStr)) {
            log.debug("pushConfig 关闭");
            return;
        }
        Integer pushConfig = NumberUtils.toInt(pushConfigStr, 0);
        if (pushConfig == 0) {
            log.debug("pushConfig 关闭");
            return;
        }
        Query query = Query.query(Criteria.where("infoId").is(event.getWubaHouseDetail().getInfoId()));
        WubaHouseDetail one = mongoTemplate.findOne(query, WubaHouseDetail.class);
        if (Objects.isNull(one)) {
            return;
        }
        //获取手机号码
//        if (StrUtil.isBlank(one.getPhone())) {
//            return;
//        }
        Document houseDocument = crawlerDocumentTool.getHouseDetailDocument(one.getSourceUrl());
        if (Objects.nonNull(houseDocument)) {
            one = crawlerParseTool.parseHouseDetail(one.getInfoId(), houseDocument);
            log.info("推送的房源信息{}", JSON.toJSONString(one));
        }else{
            log.info("解析详情为空");
        }

        //获取订阅区域的所有用户
        List<String> list = Stream.builder().add(one.getArea()).build().map(s -> s.toString()).collect(Collectors.toList());
        Query subscribeQuery = Query.query(Criteria.where("subscribeCityInfo.area").in(list));
        List<UserSubscribeBean> userSubscribeBeanList = mongoTemplate.find(subscribeQuery, UserSubscribeBean.class);
        if (CollectionUtil.isEmpty(userSubscribeBeanList)) {
            log.info("推送的用户为空");
            return;
        }

//        ArrayList<UserSubscribeBean> userSubList = userSubscribeBeanList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
//                () -> new TreeSet<>(Comparator.comparing(UserSubscribeBean::getUserId))), ArrayList::new));
        Set<Long> userIdList = userSubscribeBeanList.stream().map(UserSubscribeBean::getUserId).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(userIdList)) {
            log.info("推送的用户为空");
            return;
        }
        log.info("订阅的用户ID {}",JSON.toJSONString(userIdList));

        //通知
        List<UserBean> byUserIdList = userService.findByUserIdList(new ArrayList<>(userIdList));
        for (UserBean userBean : byUserIdList) {
            if (UserConst.Flag.isFlag(UserConst.Flag.IS_VIP, userBean.getFlag()) &&
                    UserConst.Flag.isFlag(UserConst.Flag.IS_OPEN_PUSH, userBean.getFlag())
                    && StrUtil.isNotBlank(userBean.getMpOpenId())) {
                this.sendKefuMessage(userBean.getId(), userBean.getMpOpenId(), one);
            }
        }


    }

    public Document getHouseDetailDocument(String url) {
        Integer retryCount = 0;
        Document document = null;
        while (retryCount < 5) {
            String ip = null;
            String port = null;
            Object o = redisTemplate.opsForSet().randomMember(RedisConstant.proxy_ip);
            if (Objects.isNull(o)) {
                log.error("代理池中没有数据啦");
                return null;
            }
            String data = o.toString();
            log.info("【解析detail】获取的代理ip:{}", data);
            try {
                ip = data.split(":")[0];
                port = data.split(":")[1];
                document = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000).get();
            } catch (Exception e) {
                retryCount++;
                log.error("【解析detail】获取document出错，ip：{} 错误：{}", ip, e.getMessage());
                String eMsg = e.getMessage();
            }
        }
        return document;
    }

    /**
     * 通知&&扣除次数
     *
     * @param userId
     * @param openId
     * @param wubaHouseDetail
     */
    private void sendKefuMessage(Long userId, String openId, WubaHouseDetail wubaHouseDetail) {
        boolean pushSuccess = false;
        //判断已推送
        String infoId = wubaHouseDetail.getInfoId();
        infoId = infoId.substring(7);
        Boolean hasPush = redisTemplate.opsForValue().getBit(RedisConstant.B_USER_PUSH_PREFIX + openId, NumberUtil.parseInt(infoId));
        if (hasPush) {
            return;
        }
        String errorMsg ="";
        //通知
        try {
//            String a = "<a href=\"weixin://bizmsgmenu?msgmenucontent=分享感受&msgmenuid=1\">""</a>";
            String detail = "<a href=\"http://你的公众号域名地址/house/detail?id=" + wubaHouseDetail.getId() + "\">查看详情</a>";
            StringBuilder sb = new StringBuilder();
            sb.append("【最新房源】" + wubaHouseDetail.getTitle() + " " + wubaHouseDetail.getHuxing() + '\n' +
                    "位置：" + wubaHouseDetail.getXiaoquName() + "\n" +
                    "面积：" + wubaHouseDetail.getMainji() + "\n" +
                    "价格：" + wubaHouseDetail.getDanjia() + "\n\n" + detail);
            WxMpKefuMessage build = WxMpKefuMessage
                    .TEXT()
                    .toUser(openId)
                    .content(sb.toString())
                    .build();
            pushSuccess = wxMpService.getKefuService().sendKefuMessage(
                    build);
        } catch (WxErrorException e) {
            errorMsg = e.getMessage();
            log.error("用户推送失败{}", e.getMessage());
        }
        PushHistoryBean pushHistoryBean = new PushHistoryBean();
        pushHistoryBean.setUserId(userId);
        pushHistoryBean.setResourceId(wubaHouseDetail.getId());
        pushHistoryBean.setCreateTime(DateUtil.date());
        if(StrUtil.isNotBlank(errorMsg) || !pushSuccess){
            pushHistoryBean.setStatus(2);
            pushHistoryBean.setRemarks(errorMsg);
        }
        if (pushSuccess) {
            pushHistoryBean.setStatus(1);
            //记录推送
            redisTemplate.opsForValue().setBit(RedisConstant.B_USER_PUSH_PREFIX, Integer.parseInt(infoId), false);
            //扣除
            log.info("用户{}推送成功{}扣除", userId, wubaHouseDetail.getInfoId());
            int i = vipCountService.reduceCount(userId);
            log.info("扣除结果{}", i);
        }
        pushHistoryService.save(pushHistoryBean);
    }

    private void getHouseDetail(WubaHouseDetail wubaHouseDetail) {
        String sourceUrl = wubaHouseDetail.getSourceUrl();
        Document document = this.getDocument(sourceUrl);
        if (Objects.isNull(document)) {
            log.error("获取house detail document错误");
            return;
        }

    }

    private Document getDocument(String url) {
        Document document = null;
        String ip = null;
        String port = null;
        Object o = redisTemplate.opsForSet().randomMember(RedisConstant.proxy_ip);
        if (Objects.isNull(o)) {
            log.error("代理池中没有数据啦");
            XxlJobLogger.log("代理池没有数据");
            return null;
        }
        String data = o.toString();
        log.info("获取House Detail 的代理ip:{}", data);
        try {

            ip = data.split(":")[0];
            port = data.split(":")[1];
            Connection connect = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000);
            Connection.Response resp = connect.execute();
            Map<String, String> cookies = resp.cookies();

            document = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000).get();
        } catch (Exception e) {
            log.error("获取House Detail Document出错，ip：{} 错误：{}", ip, e.getMessage());
            String eMsg = e.getMessage();
            if (eMsg.contains("timed out") || eMsg.contains("Unexpected")) {
                String countStr = redisTemplate.opsForValue().get(RedisConstant.IP_ERROR_COUNT + data);
                int count = NumberUtils.toInt(countStr, 0);
                if (count > 5) {
                    log.info("ip：{} remove", ip);
                    redisTemplate.opsForSet().remove(RedisConstant.proxy_ip, data);
                } else {
                    log.info("ip count{}", count);
                    redisTemplate.opsForValue().increment(RedisConstant.IP_ERROR_COUNT + data);
                }
            }
            return null;
        }
        return document;
    }

    public static void main(String[] args) {
        System.out.println(NumberUtil.parseInt("452226563"));
    }
}
