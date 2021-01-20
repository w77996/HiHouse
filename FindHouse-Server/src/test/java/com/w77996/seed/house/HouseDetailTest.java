package com.w77996.seed.house;

import com.alibaba.fastjson.JSON;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName HouseDetailTest
 * @Description
 * @author w77996
 * @date 2020/12/15 12:56
 */
@SpringBootTest
@Slf4j
public class HouseDetailTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void houseDetailTest(){
        Query query = Query.query(Criteria.where("infoId").is("1768895452226563"));
        WubaHouseDetail one = mongoTemplate.findOne(query, WubaHouseDetail.class);
        if (Objects.isNull(one)) {
            return;
        }
        this.getHouseDetail(one);

    }

    private void getHouseDetail(WubaHouseDetail wubaHouseDetail){
        AtomicInteger retryCount = new AtomicInteger();
//        while (retryCount.get() <= 5){
            try {
                String sourceUrl = wubaHouseDetail.getSourceUrl();
//                String sourceUrl = "https://sz.58.com/ershoufang/44536193532701x.shtml";
                Document document = this.getDocument(sourceUrl);
                if (Objects.isNull(document)) {
                    retryCount.incrementAndGet();
                    log.error("获取house detail document错误");
                    return;
                }

                Elements e = document.select("ul[class=house-basic-item3]");
                Elements houseEl = e.select("li");
                System.out.println(JSON.toJSONString(houseEl));
            }catch (Exception e){
                retryCount.incrementAndGet();
                log.error("获取house detail document错误{}",e.getMessage());
            }

//        }


    }

    String[] userAgent = {"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1",
            "Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24"};

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
//            ip = "222.185.28.38";
//            port ="33547";
            String userAgentS = userAgent[(int)(Math.random()*userAgent.length)];
//            document = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000).get();
            document = Jsoup.connect(url).timeout(3000).get();
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
}
