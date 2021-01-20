package com.w77996.seed.house;

import com.w77996.seed.house.core.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ProxyTest
 * @Description
 * @author w77996
 * @date 2020/10/14 12:36
 */
@SpringBootTest
@Slf4j
public class ProxyTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "代理池";

    @Test
    public void test() {
        String s = redisTemplate.opsForValue().get("1");
        log.info(s);
    }
    @Test
    public void pushConfig() {
//        redisTemplate.opsForValue().set(RedisConstant.PUSH_CONFIG,"0");
        String s = redisTemplate.opsForValue().get(RedisConstant.PUSH_CONFIG);
        log.info(s);
    }

    @Test
    public void request() {

        String forEntity = restTemplate.getForObject(url, String.class);
        log.info(forEntity);
        if (forEntity.contains("白名单")) {
            log.error("");
            return;
        }
        String[] split = forEntity.split("\r\n");
        log.info(Arrays.toString(split));
//        List<String> proxyIpList = Arrays.asList(split);

        redisTemplate.opsForSet().add("proxy_ip", split);
    }
}
