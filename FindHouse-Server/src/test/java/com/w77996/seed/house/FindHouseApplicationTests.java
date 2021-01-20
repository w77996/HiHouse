package com.w77996.seed.house;

import com.google.common.io.CharStreams;
import com.w77996.seed.house.model.bean.city.SubscribeCityConfigBean;
import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class FindHouseApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void find() throws Exception {
        SubscribeCityConfigBean subscribeCityConfigBean =new SubscribeCityConfigBean();
        List<SubscribeCityConfigBean> subscribeCityConfigBeanList =mongoTemplate.findAll(SubscribeCityConfigBean.class);
        subscribeCityConfigBeanList.forEach(u -> {
            log.info(u.toString());
        });
    }

    @Test
    public void createConfig() throws Exception {
//        SubscribeCityConfig subscribeCityConfig = new SubscribeCityConfig();
//        subscribeCityConfig.setProvinceId(1);
//        subscribeCityConfig.setProvinceName("新疆");
//        subscribeCityConfig.setProvinceUrl("http://www.58.com/新疆");
//
//        //市
//        List<CityInfo> cityList = Lists.newArrayList();
//        CityInfo subscribeCity = new CityInfo();
//        subscribeCity.setCityId(1001);
//        subscribeCity.setCityName("伊犁");
//        subscribeCity.setCityUrl("http://yili.58.com/");
//
//
//        //区
//        List<AreaInfo> areaList = Lists.newArrayList();
//        AreaInfo yinin = new AreaInfo();
//        yinin.setAreaId(100101);
//        yinin.setAreaName("伊宁市");
//        yinin.setAreaUrl("https://yili.58.com/yiningshi");
//        areaList.add(yinin);
//
//
//        AreaInfo kutun = new AreaInfo();
//        kutun.setAreaId(100102);
//        kutun.setAreaName("奎屯");
//        kutun.setAreaUrl("https://yili.58.com/kuitunshi");
//        areaList.add(kutun);

//        SubscribeCityConfig.AreaInfo 察布查尔 = new SubscribeCityConfig.AreaInfo();
//        察布查尔.setAreaId(100103);
//        察布查尔.setAreaName("察布查尔");
//        察布查尔.setAreaUrl("https://yili.58.com/cbceyl");
//        areaList.add(察布查尔);
//
//        SubscribeCityConfig.AreaInfo 新源 = new SubscribeCityConfig.AreaInfo();
//        新源.setAreaId(100104);
//        新源.setAreaName("新源");
//        新源.setAreaUrl("https://yili.58.com/xyyl");
//        areaList.add(新源);
//
//        SubscribeCityConfig.AreaInfo 霍尔果斯 = new SubscribeCityConfig.AreaInfo();
//        霍尔果斯.setAreaId(100105);
//        霍尔果斯.setAreaName("霍尔果斯");
//        霍尔果斯.setAreaUrl("https://yili.58.com/hegsyl");
//        areaList.add(霍尔果斯);
//
//        SubscribeCityConfig.AreaInfo 霍城 = new SubscribeCityConfig.AreaInfo();
//        霍城.setAreaId(100106);
//        霍城.setAreaName("霍尔果斯");
//        霍城.setAreaUrl("https://yili.58.com/hegsyl");
//        areaList.add(霍城);
//
//        SubscribeCityConfig.AreaInfo 巩留 = new SubscribeCityConfig.AreaInfo();
//        巩留.setAreaId(100107);
//        巩留.setAreaName("巩留");
//        巩留.setAreaUrl("https://yili.58.com/glyl");
//        areaList.add(巩留);
//
//        SubscribeCityConfig.AreaInfo 特克斯 = new SubscribeCityConfig.AreaInfo();
//        特克斯.setAreaId(100107);
//        特克斯.setAreaName("特克斯");
//        特克斯.setAreaUrl("https://yili.58.com/tksyl");
//        areaList.add(特克斯);
//
//        SubscribeCityConfig.AreaInfo 尼勒克 = new SubscribeCityConfig.AreaInfo();
//        尼勒克.setAreaId(100107);
//        尼勒克.setAreaName("尼勒克");
//        尼勒克.setAreaUrl("https://yili.58.com/nlkyl");
//        areaList.add(尼勒克);
//
//        SubscribeCityConfig.AreaInfo 昭苏 = new SubscribeCityConfig.AreaInfo();
//        昭苏.setAreaId(100107);
//        昭苏.setAreaName("昭苏");
//        昭苏.setAreaUrl("https://yili.58.com/zhaosuyl");
//        areaList.add(昭苏);
//
//        SubscribeCityConfig.AreaInfo 伊犁周边 = new SubscribeCityConfig.AreaInfo();
//        伊犁周边.setAreaId(100107);
//        伊犁周边.setAreaName("昭苏");
//        伊犁周边.setAreaUrl("https://yili.58.com/yilizhoubian");
//        areaList.add(伊犁周边);
//        subscribeCity.setAreaList(areaList);



//        cityList.add(subscribeCity);
//        mongoTemplate.save(subscribeCityConfig);


    }

    @Test
    public void subscribe() throws Exception {
        UserSubscribeBean userSubscribe = new UserSubscribeBean();
        userSubscribe.setUserId(1L);

//      /serSubscribe);






    }

    @Test
    public void getSubscribe(){
        List<String> list = Stream.builder().add("伊宁城区").build().map(s->s.toString()).collect(Collectors.toList());
        Query subscribeQuery = Query.query(Criteria.where("subscribeCityInfo.business").in(list));
        List<UserSubscribeBean> userSubscribeBeanList = mongoTemplate.find(subscribeQuery, UserSubscribeBean.class);
        System.out.println(userSubscribeBeanList);
    }



}
