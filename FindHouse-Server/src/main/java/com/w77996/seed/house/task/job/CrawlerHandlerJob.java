package com.w77996.seed.house.task.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.event.NotifyPhoneDto;
import com.w77996.seed.house.model.bean.wuba.WubaPhoneResult;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.w77996.seed.house.utils.CrawlerDocumentTool;
import com.w77996.seed.house.utils.CrawlerParseTool;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CrawlerHanlderJob
 * @Description
 * @author w77996
 * @date 2020/11/13 13:23
 */
@Slf4j
@Component
public class CrawlerHandlerJob {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private CrawlerDocumentTool crawlerDocumentTool;

    @Autowired
    private CrawlerParseTool crawlerExecutorTool;

    @XxlJob("crawlerHandlerJob")
    public ReturnT<String> CrawlerHandlerJob(String param) {
        long startTime = System.currentTimeMillis();
        log.info("【爬虫】开始执行");
//        String url = "https://yili.58.com/ershoufang/0/";
        Set<String> configList = redisTemplate.opsForSet().distinctRandomMembers(RedisConstant.CONFIG_CITY, 1000);
        for (String url : configList) {
            log.info("【爬虫】爬取地址：{}",url);
            handler(url);
        }
        long endTime = System.currentTimeMillis();
        log.info("【爬虫】执行结束，耗时{}", endTime - startTime);
        return ReturnT.SUCCESS;

    }

    private ReturnT handler(String url) {
        Document houseDocument = crawlerDocumentTool.getHouseDocument(url);
        if (Objects.isNull(houseDocument)) {
            log.error("【解析】获取document错误");
            return ReturnT.FAIL;
        }
        List<WubaHouseDetail> wubaHouseDetailList = crawlerExecutorTool.crawler(houseDocument);
        if (CollectionUtil.isNotEmpty(wubaHouseDetailList)) {
            for (WubaHouseDetail wubaHouseDetail : wubaHouseDetailList) {
                mongoTemplate.insert(wubaHouseDetail);
                if(StringUtils.isNotBlank(wubaHouseDetail.getPhone())){
                    applicationEventPublisher.publishEvent(NotifyPhoneDto.builder().wubaHouseDetail(wubaHouseDetail).build());
                }
            }
        }
        return ReturnT.SUCCESS;
    }


    private ReturnT crawler(String url) {
        try {
            List<WubaHouseDetail> list = Lists.newArrayList();
            Document document = this.getDocument(url);
            if (Objects.isNull(document)) {
                log.error("【解析】获取document错误");
                return ReturnT.FAIL;
            }
            AtomicInteger count = new AtomicInteger();
            Elements e = document.select("ul[class=house-list-wrap]");
            Elements houseEl = e.select("li");
            for (Element element : houseEl) {
                String dataTrace = element.attr("data-trace");
                String replace = dataTrace.replace("\n", "").trim();
                String substring = replace.substring(1, replace.length() - 1).trim();
                String[] dataTraceElArray = substring.split(",");
                String[] esfIdArray = dataTraceElArray[0].split(":");
                String infoId = esfIdArray[1];
//                log.info("获取的infoId {}",infoId);
                Elements pic = element.select("div[class=pic]");
                String imgUrl = pic.select("img").attr("data-src");
                Elements listInfoArray = element.select("div[class=list-info]");

                Element listInfo = listInfoArray.first();
                String sourceUrl = listInfo.children().get(0).getAllElements().first().selectFirst("a").attr("href");

                String title = listInfo.children().get(0).getAllElements().first().selectFirst("a").text();
                Elements baseInfo1 = listInfo.children().get(1).getAllElements();
                String huxing = baseInfo1.get(1).text();
                String mianji = baseInfo1.get(2).text();
                String towards = baseInfo1.get(3).text();
                String floor = baseInfo1.get(4).text();

                Elements baseInfo2 = listInfo.children().get(2).getAllElements();
                String xiaoquName = baseInfo2.get(2).text();
                String area = baseInfo2.get(3).text();
                String location = baseInfo2.get(4).text();
                Elements jjrInfo = listInfo.children().get(3).getAllElements();
                String username = jjrInfo.get(3).text();

                Elements priceInfo = element.select("div[class=price]");
                String price = priceInfo.select("p[class=sum]").text();
                String danjia = priceInfo.select("p[class=unit]").text();

                Query query = Query.query(Criteria.where("infoId").is(infoId));
                WubaHouseDetail one = mongoTemplate.findOne(query, WubaHouseDetail.class);
                if (Objects.nonNull(one)) {
                    count.incrementAndGet();
                    continue;
                }

                String phone = this.getPhone(infoId);
                if (StrUtil.isBlank(phone)) {
                    log.info("infoId{}获取手机号码为空", infoId);
                } else {
                    log.info("获取的手机号码:{}", phone);
                }
                WubaHouseDetail wubaHouseDetail = WubaHouseDetail.builder()
                        .pic(imgUrl)
                        .infoId(infoId)
                        .trueName(username)
                        .phone(phone)
                        .chanquan("70年")
                        .fittype("简单装修")
                        .louxing("暂无信息")
                        .objectType("普通住宅")
                        .title(title)
                        .price(price)
                        .huxing(huxing)
                        .floor(floor)
                        .area(area)
                        .xiaoquName(xiaoquName)
                        .business("")
                        .images(Lists.newArrayList())
                        .cityid("")
                        .toward(towards)
                        .dianti("")
                        .mainji(mianji)
                        .danjia(danjia)
                        .sourceUrl(sourceUrl)
                        .createTime(DateUtil.date())
                        .build();
                log.info(wubaHouseDetail.toString());
                mongoTemplate.insert(wubaHouseDetail);
                list.add(wubaHouseDetail);
                applicationEventPublisher.publishEvent(NotifyPhoneDto.builder().wubaHouseDetail(wubaHouseDetail).build());
            }
            log.info("【解析】重复房源数:{}", count.get());
//            log.info(Arrays.toString(list.toArray()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

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
        log.info("【解析】获取的代理ip:{}", data);
        try {

            ip = data.split(":")[0];
            port = data.split(":")[1];

//            document = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000).get();
            Connection connect = Jsoup.connect(url).proxy(ip, Integer.parseInt(port)).timeout(3000);
            Connection.Response response = connect.proxy(ip, Integer.parseInt(port)).execute();
            log.info("【解析】response statusCode {}", response.statusCode());
            log.info("【解析】cookie {}", JSON.toJSONString(response.cookies()));
            document = response.parse();
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


//    public String getPhone(String infoId) {
//        Integer retryCount = 1;
//        while (true){
//            if(retryCount >= 3){
//                return "";
//            }
//            Object o = redisTemplate.opsForSet().randomMember(RedisConstant.S_USER_ID_POOL);
//            if (Objects.isNull(o)) {
//                log.error("用户id中没有数据啦");
//                return "";
//            }
//            String userId = o.toString();
//            try {
//                Map<String, Object> params = new HashMap<>();
//                params.put("info_id", infoId);
//                params.put("user_id", userId);
//                String ret = HttpUtil.get("https://miniapp.58.com/landlord/getprivacyphone", params, 2000);
//                log.info(ret);
//                WubaPhoneResult wuPhoneResult = JSONObject.toJavaObject(JSON.parseObject(ret), WubaPhoneResult.class);
//                if ("0".equals(wuPhoneResult.getStatus())) {
//                    //获取手机号码
//                    String phone = wuPhoneResult.getData().getPhone();
//                    log.info(phone);
//                    return phone;
//                } else {
//                    log.error("获取手机号码失败,用户id{}", userId);
//                }
//            } catch (Exception e) {
//                log.error("用户 {} 获取手机号码失败{}", userId, e.getMessage());
//            }
//            return "";
//        }
//
//
//    }


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

            HttpGet httpGet = new HttpGet("模拟获取手机号码" + userId + "&info_id=" + infoId);
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

    public static void main(String[] args) {
        //            System.setProperty("https.proxySet", "true");
//            System.getProperties().setProperty("http.proxyHost", ip);
//            System.getProperties().setProperty("http.proxyPort", port);
//            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//            connection.setConnectTimeout(60000); //设置连接时间
//            connection.setReadTimeout(60000); //设 置读取时间
//            connection.setUseCaches(false);
//            InputStream is = connection.getInputStream();
//        //防止读取的数据有中文乱码，可以设置编码
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8" ) );
//            StringBuffer bs = new StringBuffer();
//            String l = null;
//            while ((l = buffer.readLine()) != null) {
//                bs.append(l);
//
//            }
        String ip = "114.236.138.69";
        int port = 44236;
        String infoId = "1553086633649161";
        try {
//            headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            Map<String, Object> params = new HashMap<>();
            params.put("info_id", infoId);
            params.put("user_id", "6890");
            String ret = HttpUtil.get("https://miniapp.58.com/landlord/getprivacyphone", params);
            log.info(ret);
            WubaPhoneResult wuPhoneResult = JSONObject.toJavaObject(JSON.parseObject(ret), WubaPhoneResult.class);
            if ("0".equals(wuPhoneResult.getStatus())) {
                //获取手机号码
                String phone = wuPhoneResult.getData().getPhone();
                log.info(wuPhoneResult.toString());
                log.info(phone);
            }
        } catch (Exception e) {
            log.error("ip {} 获取手机号码失败{}", ip + ":" + port, e.getMessage());
        }
    }
}
