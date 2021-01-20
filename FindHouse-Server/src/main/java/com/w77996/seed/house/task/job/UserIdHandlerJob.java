package com.w77996.seed.house.task.job;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.model.bean.wuba.WubaPhoneResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName UserIdHandlerJob
 * @Description
 * @author w77996
 * @date 2020/12/11 14:34
 */
@Slf4j
@Component
public class UserIdHandlerJob {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @XxlJob("userIdHandlerJob")
    public ReturnT<String> UserIdHandlerJob(String param) {
        Object indexObj = redisTemplate.opsForValue().get(RedisConstant.USER_ID_INDEX);
        if (Objects.isNull(indexObj)) {
            log.info("userId index为空");
            return ReturnT.SUCCESS;
        }
        Integer index = NumberUtil.parseInt(indexObj.toString());

        Object o = redisTemplate.opsForSet().randomMember(RedisConstant.proxy_ip);
        if (Objects.isNull(o)) {
            log.error("代理池中没有数据啦");
            return ReturnT.SUCCESS;
        }
        String data = o.toString();
        log.info("获取的代理ip:{}", data);
        String ip = data.split(":")[0];
        String port = data.split(":")[1];

        String infoId = "";
        try {
//            headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            Map<String, Object> params = new HashMap<>();
            params.put("info_id", infoId);
            params.put("user_id", index + "");
            // 创建httpget实例
            HttpGet httpGet = new HttpGet("获取手机号码参数接口" + index + "&info_id=" + infoId);

            CloseableHttpClient client = setProxy(httpGet, ip, Integer.parseInt(port));
            //设置请求头消息
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            // 执行http get请求  也可以使用psot
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取返回实体
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity, "utf-8");
                    log.info("index{} ret{}", index, content);
                    WubaPhoneResult wuPhoneResult = JSONObject.toJavaObject(JSON.parseObject(content), WubaPhoneResult.class);
                    if ("0".equals(wuPhoneResult.getStatus())) {
                        //获取手机号码
                        String phone = wuPhoneResult.getData().getPhone();
                        redisTemplate.opsForSet().add("USER_ID_POOL", index + "");
                        log.info(wuPhoneResult.toString());
                        log.info(phone);
                    }
                }
            }
            //关闭response
            response.close();
//                //关闭httpClient
//                client.close();

        } catch (Exception e) {
            log.error("ip {} 获取手机号码失败{}", ip + ":" + port, e.getMessage());
        }
        redisTemplate.opsForValue().increment(RedisConstant.USER_ID_INDEX);
        return ReturnT.SUCCESS;

    }

    /**
     * 设置代理
     * @param httpGet
     * @param proxyIp
     * @param proxyPort
     * @return
     */
    public CloseableHttpClient setProxy(HttpGet httpGet, String proxyIp, int proxyPort) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置代理IP、端口
        HttpHost proxy = new HttpHost(proxyIp, proxyPort, "http");
        //也可以设置超时时间   RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).setSocketTimeout(3000).setConnectionRequestTimeout(3000).build();
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);
        return httpClient;
    }
}
