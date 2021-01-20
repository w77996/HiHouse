package com.w77996.seed.house.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.event.NotifyPhoneDto;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CrawlerExecutorTool
 * @Description
 * @author w77996
 * @date 2020/12/17 14:00
 */
@Component
@Slf4j
public class CrawlerDocumentTool {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static CrawlerDocumentTool crawlerDocumentTool;

    @PostConstruct  //该注解的方法在构造函数执行后执行
    public void init() {
        crawlerDocumentTool = this;
    }


    public Document getHouseDocument(String url) {
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
        log.info("【解析】获取的代理ip:{}", data);
        try {

            ip = data.split(":")[0];
            port = data.split(":")[1];
//            ip = "222.185.28.38";
//            port = "32374";
            document = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000).get();
//            document = Jsoup.connect(url).timeout(3000).get();
//            Connection connect = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000);
//            Connection.Response response = connect.proxy(ip, Integer.parseInt(port)).execute();
//            log.info("【解析】response statusCode {}", response.statusCode());
//            log.info("【解析】cookie {}", JSON.toJSONString(response.cookies()));
//            document = response.parse();
        } catch (Exception e) {
            log.error("【解析】获取document出错，ip：{} 错误：{}", ip, e.getMessage());
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
                retryCount ++;
                return document;
//            document = Jsoup.connect(url).timeout(3000).get();
//                Connection connect = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000);
//                Connection.Response response = connect.proxy(ip, Integer.parseInt(port)).execute();
//                log.info("【解析detail】response statusCode {}", response.statusCode());
//                log.info("【解析detail】cookie {}", JSON.toJSONString(response.cookies()));
//                document = response.parse();
            } catch (Exception e) {
                retryCount++;
                log.error("【解析detail】获取document出错，ip：{} 错误：{}", ip, e.getMessage());
                String eMsg = e.getMessage();
//                if (eMsg.contains("timed out") || eMsg.contains("Unexpected")) {
//                    String countStr = redisTemplate.opsForValue().get(RedisConstant.IP_ERROR_COUNT + data);
//                    int count = NumberUtils.toInt(countStr, 0);
//                    if (count > 5) {
//                        log.info("ip：{} remove", ip);
//                        redisTemplate.opsForSet().remove(RedisConstant.proxy_ip, data);
//                    } else {
//                        log.info("ip count{}", count);
//                        redisTemplate.opsForValue().increment(RedisConstant.IP_ERROR_COUNT + data);
//                    }
//                }
            }

        }
        return document;
    }
}
