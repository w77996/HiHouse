package com.w77996.seed.house;

import com.w77996.seed.house.core.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName ConfigTest
 * @Description
 * @author w77996
 * @date 2020/12/14 10:27
 */
@SpringBootTest
@Slf4j
public class ConfigTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void pushConfig() {
        //0.关闭 1.开启
        redisTemplate.opsForValue().set(RedisConstant.PUSH_CONFIG,"1");
    }

    @Test
    public void userIdSet() {
        redisTemplate.opsForValue().set(RedisConstant.USER_ID_INDEX, 7300 + "");
    }

}
