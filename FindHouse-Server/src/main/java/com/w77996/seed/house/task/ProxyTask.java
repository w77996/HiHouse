package com.w77996.seed.house.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @ClassName ProxyTask
 * @Description http://ip.16yun.cn:817/myip/pl/2ee7dd37-f189-443a-b630-fa574f5d2225/?s=ymhhoqplbl&u=limuzi1203
 * @author w77996
 * @date 2020/10/14 12:33
 */
@Slf4j
@Component
//@Component
public class ProxyTask {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String url ="获取代理池ip";

//    @Scheduled(cron = "15 * *  * * * ")
    public void proxyTask(){
        String forEntity = restTemplate.getForObject(url, String.class);
        log.info(forEntity);
        if (forEntity.contains("False")) {
            log.error("请求代理错误");
            return;
        }
        String[] split = forEntity.split("\r\n");
        log.info(Arrays.toString(split));
//        List<String> proxyIpList = Arrays.asList(split);

        redisTemplate.opsForSet().add("proxy_ip", split);
    }
}
