package com.w77996.seed.house;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.model.bean.city.CityInfoBean;
import com.w77996.seed.house.model.bean.city.SubscribeCityConfigBean;
import com.w77996.seed.house.model.bean.wuba.WubaPhoneResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @ClassName ProxyTest
 * @Description
 * @author w77996
 * @date 2020/10/14 12:36
 */
@SpringBootTest
@Slf4j
public class CityConfigTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void test() {
//            Object o = redisTemplate.opsForSet().add(RedisConstant.CONFIG_CITY,"https://yili.58.com/ershoufang/0/");
//            Object o = redisTemplate.opsForSet().add(RedisConstant.CONFIG_CITY,"https://xj.58.com/ershoufang/0/");
//            Object o = redisTemplate.opsForSet().add(RedisConstant.CONFIG_CITY,"https://changji.58.com/ershoufang/0/");
//            Object o = redisTemplate.opsForSet().add(RedisConstant.CONFIG_CITY,"https://gz.58.com/ershoufang/0/");
            Object o = redisTemplate.opsForSet().add(RedisConstant.CONFIG_CITY,"https://nn.58.com/ershoufang/0/");
    }

    @Test
    public void subCity() {
        List<CityInfoBean> cityInfoBeans = mongoTemplate.find(Query.query(new Criteria("city").is("南宁")), CityInfoBean.class);
        log.info(cityInfoBeans.toArray().toString());
        List<SubscribeCityConfigBean> subscribeCityConfigBeanList = Lists.newArrayList();
        for (CityInfoBean cityInfoBean : cityInfoBeans) {
            SubscribeCityConfigBean subscribeCityConfigBean = new SubscribeCityConfigBean();
            BeanUtil.copyProperties(cityInfoBean,subscribeCityConfigBean);
            subscribeCityConfigBean.setCityId(cityInfoBean.getCityId());
            subscribeCityConfigBean.setCityPinyin(cityInfoBean.getCityPinyin());
            subscribeCityConfigBean.setCityUrl(cityInfoBean.getCityUrl());
            subscribeCityConfigBean.setAreaId(cityInfoBean.getAreaId());
            subscribeCityConfigBean.setAreaPinyin(cityInfoBean.getAreaPinyin());
            subscribeCityConfigBean.setBusinessId(cityInfoBean.getBusinessId());
            subscribeCityConfigBean.setBusinessPinyin(cityInfoBean.getBusinessPinyin());
            subscribeCityConfigBeanList.add(subscribeCityConfigBean);

        }
        subscribeCityConfigBeanList.forEach(item->{
            log.info(item.toString());
        });
        mongoTemplate.insert(subscribeCityConfigBeanList,SubscribeCityConfigBean.class);
    }
}
