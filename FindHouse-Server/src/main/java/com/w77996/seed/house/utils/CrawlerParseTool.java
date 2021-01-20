package com.w77996.seed.house.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.w77996.seed.house.model.bean.wuba.WubaHouseMiniAppDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CrawlerExecutorTool
 * @Description
 * @author w77996
 * @date 2020/12/17 14:00
 */
@Component
@Slf4j
public class CrawlerParseTool {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CrawlerPhoneTool crawlerPhoneTool;

    private static CrawlerParseTool crawlerParseTool;

    @PostConstruct  //该注解的方法在构造函数执行后执行
    public void init() {
        crawlerParseTool = this;
    }


    public List<WubaHouseDetail> crawler(Document document) {
        List<WubaHouseDetail> list = Lists.newArrayList();
        Integer count = 0;
        try {
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
                String username = "";
                if(jjrInfo.size() >=4){
                    username =  jjrInfo.get(3).text();
                }
                if(username.contains("58") || username.contains("认证") || username.contains("商家")){
                    count ++;
                    continue;
                }


                Elements priceInfo = element.select("div[class=price]");
                String price = priceInfo.select("p[class=sum]").text();
                String danjia = priceInfo.select("p[class=unit]").text();

                Query query = Query.query(Criteria.where("infoId").is(infoId));
                WubaHouseDetail one = mongoTemplate.findOne(query, WubaHouseDetail.class);
                if (Objects.nonNull(one)) {
                    count ++;
                    continue;
                }

                String phone = crawlerPhoneTool.getPhone(infoId);
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
                list.add(wubaHouseDetail);
            }
            log.info("【解析】重复房源数:{}", count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public WubaHouseDetail parseHouseDetail(String infoId, Document document) {
        Query query = Query.query(Criteria.where("infoId").is(infoId));
        WubaHouseDetail wubaHouseDetail = mongoTemplate.findOne(query, WubaHouseDetail.class);
        if (Objects.isNull(wubaHouseDetail)) {
            return wubaHouseDetail;
        }
        try {

            Elements e = document.select("ul[class=house-basic-item3]");
            Elements houseElLi = e.select("li").select("a[class=c_000]");
            String business = houseElLi.get(1).text();
            String desc = document.select("p[class=pic-desc-word]").text();
            List<WubaHouseMiniAppDetailResult.InfodetailBean.HousedetailBean> houseDetailList = Arrays.asList(WubaHouseMiniAppDetailResult.InfodetailBean.HousedetailBean.builder().tit("核心卖点").des(desc).build());
            wubaHouseDetail.setBusiness(business);
            wubaHouseDetail.setHouseDetailDesc(houseDetailList);
            Update update = new Update();
            update.set("business", business);
            update.set("houseDetailDesc", houseDetailList);
            mongoTemplate.updateFirst(query, update, WubaHouseDetail.class);
        } catch (Exception e) {
            log.info("解析房源详细数据错误{}", e.getMessage());
        }
        return wubaHouseDetail;
    }
}
