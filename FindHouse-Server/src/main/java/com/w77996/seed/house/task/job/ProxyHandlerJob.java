package com.w77996.seed.house.task.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @ClassName ProxyJob
 * @Description
 * @author w77996
 * @date 2020/11/13 13:14
 */
@Slf4j
@Component
public class ProxyHandlerJob {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String url ="获取代理接口";

    @XxlJob("proxyHandlerJob")
    public ReturnT<String> proxyHandlerJob(String param) {
        String forEntity = restTemplate.getForObject(url, String.class);
        log.info(forEntity);
        if (forEntity.contains("False")) {
            log.error("请求代理错误");
            return ReturnT.FAIL;
        }
        String[] split = forEntity.split("\r\n");
        log.info(Arrays.toString(split));
        redisTemplate.opsForSet().add("proxy_ip", split);
        return ReturnT.SUCCESS;
    }
}
