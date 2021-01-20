package com.w77996.seed.house.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.w77996.seed.house.core.constant.RedisConstant;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.event.NotifyPhoneDto;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.w77996.seed.house.utils.CrawlerDocumentTool;
import com.w77996.seed.house.utils.CrawlerParseTool;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.log.XxlJobLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * @ClassName CrawlerTestController
 * @Description
 * @author w77996
 * @date 2020/10/9 15:57
 */
@Api("爬虫测试接口")
@RestController
@RequestMapping("/crawler/test")
@Slf4j
public class CrawlerTestController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CrawlerDocumentTool crawlerDocumentTool;

    @Autowired
    private CrawlerParseTool crawlerParseTool;

    @ApiOperation(value = "测试")
    @GetMapping
    public Result crawlerTest()  {
        List<WubaHouseDetail> list = Lists.newArrayList();
        String url = "https://yili.58.com/ershoufang/1768895452226563x.shtml?utm_source=&spm=&cityListName=yili&curListName=yili&landlord=1&referinfo=FALSE&typecode=201";
        try {
            Document document = crawlerDocumentTool.getHouseDetailDocument(url);
            if(Objects.isNull(document)){
                return ResultGenerator.genFailResult("获取详细错误");
            }
            crawlerParseTool.parseHouseDetail(1768895452226563L+"",document);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "测试")
    @GetMapping("/new")
    public Result crawlerNewTest()  {
        String url = "https://yili.58.com/ershoufang/0/";
        handler(url);
//        List<WubaHouseDetail> list = Lists.newArrayList();
//        String url = "https://yili.58.com/ershoufang/1768895452226563x.shtml?utm_source=&spm=&cityListName=yili&curListName=yili&landlord=1&referinfo=FALSE&typecode=201";
//        try {
//            Document document = Jsoup.connect(url).timeout(3000).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "测试")
    @GetMapping("/get")
    public Result crawlerNewGetTest()  {
        List<WubaHouseDetail> list = Lists.newArrayList();
        String url = "https://yili.58.com/ershoufang/0/";
//        this.getDocument(url);
        try {
            Document document = Jsoup.connect(url).timeout(3000).get();
            log.info(document.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult();
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
        log.info("获取的代理ip:{}", data);
        try {

            ip = data.split(":")[0];
            port = data.split(":")[1];
            Map<String,String> cookie = new HashMap<>();
            cookie.put("f","n");
            cookie.put("id58","");
            cookie.put("JSESSIONID","");
            document = Jsoup.connect(url).proxy(ip,Integer.parseInt(port)).cookies(cookie).timeout(3000).get();
//            Connection connect = Jsoup.connect(url).timeout(3000);
//            Connection.Response response = connect.execute();
//            Map<String, String> cookies = response.cookies();
//            log.info("cookie {}", JSON.toJSONString(cookies));
//            document =  response.parse();
        } catch (Exception e) {
            log.error("获取document出错，ip：{} 错误：{}", ip, e.getMessage());
//            String eMsg = e.getMessage();
//            if (eMsg.contains("timed out") || eMsg.contains("Unexpected")) {
//                String countStr = redisTemplate.opsForValue().get(RedisConstant.IP_ERROR_COUNT + data);
//                int count = NumberUtils.toInt(countStr, 0);
//                if (count > 5) {
//                    log.info("ip：{} remove", ip);
//                    redisTemplate.opsForSet().remove(RedisConstant.proxy_ip, data);
//                } else {
//                    log.info("ip count{}", count);
//                    redisTemplate.opsForValue().increment(RedisConstant.IP_ERROR_COUNT + data);
//                }
//            }
            return null;
        }
        return document;
    }


    private ReturnT crawler(String url) {
        try {
            List<WubaHouseDetail> list = Lists.newArrayList();
            Document document = this.getDocument(url);
            if (Objects.isNull(document)) {
                log.error("获取document错误");
                return ReturnT.FAIL;
            }
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
                    continue;
                }

                String phone = "";
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
            }
//            log.info(Arrays.toString(list.toArray()));
        } catch (Exception e) {
//            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ReturnT.SUCCESS;
    }

    private ReturnT handler(String url) {
        Document houseDocument = crawlerDocumentTool.getHouseDocument(url);
        if (Objects.isNull(houseDocument)) {
            log.error("【解析】获取document错误");
            return ReturnT.FAIL;
        }
        List<WubaHouseDetail> wubaHouseDetailList = crawlerParseTool.crawler(houseDocument);
        if (CollectionUtil.isNotEmpty(wubaHouseDetailList)) {
        }
        return ReturnT.SUCCESS;
    }
}
