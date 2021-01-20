package com.w77996.seed.house.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.w77996.seed.house.core.annotation.NeedToken;
import com.w77996.seed.house.core.base.BaseController;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.city.CityInfoBean;
import com.w77996.seed.house.model.bean.city.SubscribeCityConfigBean;
import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import com.w77996.seed.house.model.dto.SubscribeCityDto;
import com.w77996.seed.house.model.view.SubscribeCityConfigView;
import com.w77996.seed.house.model.view.SubscribeCityInfoView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author w77996
 * @description
 * @date 2020/5/18 23:21
 */
@Api("城市相关")
@RestController
@RequestMapping("/city")
@Slf4j
public class CityController extends BaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation(value = "获取所有可以订阅的城市")
    @GetMapping("/config")
    public Result getSubscribeCityConfig() {
        List<SubscribeCityConfigBean> subscribeCityConfigBeanList = mongoTemplate.findAll(SubscribeCityConfigBean.class);
        Map<String, List<SubscribeCityConfigBean>> collect = subscribeCityConfigBeanList.stream().collect(Collectors.groupingBy(SubscribeCityConfigBean::getProvince));
        List<SubscribeCityConfigView> subscribeCityConfigViewList = Lists.newArrayList();
        collect.forEach((s, subscribeCityConfigBeans) -> {
            Map<String, List<SubscribeCityConfigBean>> cityMap = subscribeCityConfigBeans.stream().collect(Collectors.groupingBy(SubscribeCityConfigBean::getCity));
            List<SubscribeCityConfigBean> cityConfig = Lists.newArrayList();
            cityMap.forEach((city, subscribeCityConfig) -> {
                cityConfig.add(subscribeCityConfig.get(0));
            });
            SubscribeCityConfigView subscribeCityConfigView  = SubscribeCityConfigView.builder().provinceName(s).cityInfo(cityConfig).build();
            subscribeCityConfigViewList.add(subscribeCityConfigView);
        });
        return ResultGenerator.genSuccessResult(subscribeCityConfigViewList);
    }

    @ApiOperation(value = "通过城市ID获取区域")
    @GetMapping("/getBusinessByCityId")
    public Result getAreaByCityId(@ApiParam(name = "cityId", value = "城市ID", required = true) @RequestParam String cityId) {
        Query query = new Query(Criteria.where("city_id").is(cityId));
        List<CityInfoBean> cityInfoBeanList = mongoTemplate.find(query, CityInfoBean.class);
        Set<CityInfoBean> cityInfoBeanSet = new TreeSet<>(Comparator.comparing(CityInfoBean::getArea));
        cityInfoBeanSet.addAll(cityInfoBeanList);
        return ResultGenerator.genSuccessResult(cityInfoBeanSet);
    }

    @NeedToken
    @ApiOperation(value = "用户上传订阅城市")
    @PostMapping("/subscribe")
    public Result subscribeCity(@RequestBody @ApiParam(name = "SubscribeCityDto对象", value = "传入json格式", required = true) SubscribeCityDto subscribeCityDto) {
        log.info("request {}", JSON.toJSONString(subscribeCityDto));
        Long userId = this.getUserIdByToken();
        Query query = new Query(Criteria.where("userId").is(userId));
        UserSubscribeBean dbUserSubscribe = mongoTemplate.findOne(query, UserSubscribeBean.class);
        if (dbUserSubscribe != null) {
            log.info("更新用户订阅 dbUserSubscribe {}",dbUserSubscribe);
            mongoTemplate.remove(dbUserSubscribe);
        }
        UserSubscribeBean userSubscribeBean = UserSubscribeBean.builder()
                .userId(userId)
                .publishTypes(subscribeCityDto.getPublishTypes())
                .houseTypes(subscribeCityDto.getHouseTypes())
                .cityId(subscribeCityDto.getCityId())
                .cityName(subscribeCityDto.getCityName())
                .subscribeCityInfo(subscribeCityDto.getSubscribeCityInfo())
                .build();
        mongoTemplate.save(userSubscribeBean);
        return ResultGenerator.genSuccessResult();
    }


}
