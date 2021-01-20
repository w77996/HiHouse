package com.w77996.seed.house.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.w77996.seed.house.core.constant.RedisConstant;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName CrawlerPhoneTool
 * @Description
 * @author w77996
 * @date 2020/12/17 16:19
 */
@Component
@Slf4j
public class CrawlerPhoneTool {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public String getPhone(String infoId) {
        Integer retryCount = 0;
        while (true) {
            if (retryCount >= 3) {
                return "";
            }
            Object o = redisTemplate.opsForSet().randomMember(RedisConstant.S_USER_ID_POOL);
            if (Objects.isNull(o)) {
                log.error("用户id中没有数据啦");
                return "";
            }
            String userId = o.toString();
            Object ipO = redisTemplate.opsForSet().randomMember(RedisConstant.proxy_ip);
            if (Objects.isNull(ipO)) {
                log.error("代理池中没有数据啦");
                return "";
            }
            String data = ipO.toString();
            log.info("[getPhone]获取的代理ip:{}", data);
            String ip = data.split(":")[0];
            String port = data.split(":")[1];

            HttpGet httpGet = new HttpGet("https://miniapp.58.com/landlord/getprivacyphone?user_id=" + userId + "&info_id=" + infoId);
            CloseableHttpClient client = setProxy(httpGet, ip, Integer.parseInt(port));
            try {
                // 创建httpget实例
                //设置请求头消息
                httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
                // 执行http get请求  也可以使用psot
                CloseableHttpResponse response = client.execute(httpGet);
                // 获取返回实体
                if (response != null) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        String content = EntityUtils.toString(entity, "utf-8");
                        log.info("[getPhone]获取手机号码 重试次数{} Result {}", retryCount, content);
                        WubaPhoneResult wuPhoneResult = JSONObject.toJavaObject(JSON.parseObject(content), WubaPhoneResult.class);
                        if ("0".equals(wuPhoneResult.getStatus())) {
                            //获取手机号码
                            String phone = wuPhoneResult.getData().getPhone();
                            log.info(phone);
                            if (StrUtil.isBlank(phone)) {
                                continue;
                            } else {
                                return phone;
                            }
                        }
                    }
                }
                //关闭response
                response.close();

            } catch (Exception e) {
                log.error("ip {} 获取手机号码失败{}", ip + ":" + port, e.getMessage());
            } finally {
                retryCount++;
                try {
                    //关闭httpClient
                    client.close();
                } catch (IOException e) {

                    log.error("ip {} 获取手机号码失败{}", ip + ":" + port, e.getMessage());
                }
            }
            return "";
        }
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
