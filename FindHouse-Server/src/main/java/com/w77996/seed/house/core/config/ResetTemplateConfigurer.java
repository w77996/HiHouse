package com.w77996.seed.house.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @ClassName ResetTemplateConfigurer
 * @Description
 * @author w77996
 * @date 2020/10/14 15:31
 */
@Configuration
public class ResetTemplateConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        SimpleClientHttpRequestFactory reqfac = new SimpleClientHttpRequestFactory();
//        reqfac.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));
//        restTemplate.setRequestFactory(reqfac);
        return restTemplate;
    }
}
