package com.w77996.seed.house.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.w77996.seed.house.core.annotation.NeedToken;
import com.w77996.seed.house.core.base.BaseController;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.city.CityInfoBean;
import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.w77996.seed.house.reponsitory.UserSubscribeRepository;
import com.w77996.seed.house.reponsitory.WuBaHouseRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author w77996
 * @description
 * @date 2020/5/24 21:26
 */
@Api("出售相关接口")
@RestController
@RequestMapping("/sale")
public class HouseSaleController extends BaseController {

    @Autowired
    private WuBaHouseRepository wuBaHouseRepository;

    @Autowired
    private UserSubscribeRepository userSubscribeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;



    @ApiOperation(value = "获取出售列表")
    @NeedToken
    @GetMapping("/list")
    public Result getSaleList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Long userId = getUserIdByToken();
//        //获取用户订阅的城市
//
//
        UserSubscribeBean userSubscribeModel = userSubscribeRepository.findByUserId(userId);
        List<String> areaList = userSubscribeModel.getSubscribeCityInfo().stream().map(CityInfoBean::getArea).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(areaList)){
            return ResultGenerator.genSuccessResult(CollectionUtils.EMPTY_COLLECTION);
        }
        //        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC);
//
//        Query query = new Query(Criteria.where("name").is(name).and("size").is(size).and("age").is(age))
//                .skip((page.getPageNum()-1)*page.getPageSize())
//                .limit(page.getPageSize());
//        Query query = new Query(Criteria.where("area").in(areaList)).addCriteria(Criteria.where("phone").ne(""));
        Query query = new Query(Criteria.where("area").in(areaList)).addCriteria(Criteria.where("phone").ne(""));
//        Query query = new Query();
        query.skip((page - 1) * size);
        query.limit(size);
        query.with(Sort.by(
                Sort.Order.desc("createTime")));
        List<WubaHouseDetail> wubaHouseDetails = mongoTemplate.find(query, WubaHouseDetail.class);
//        List<WubaHouseDetail> wubaHouseDetailList = wuBaHouseRepository.findByBusinessIn(userSubscribeModel.getBusiness());


        return ResultGenerator.genSuccessResult(wubaHouseDetails);
    }

    @ApiOperation(value = "获取出售房屋详细")
    @NeedToken
    @GetMapping("/detail")
    public Result houseDetail(@RequestParam("id") String id) {
        Long userId = getUserIdByToken();
        //获取用户订阅的城市

//        Query query = new Query(Criteria.where("info").is(infoId));
        WubaHouseDetail wubaHouseDetails = mongoTemplate.findById(id, WubaHouseDetail.class);
        if(Objects.isNull(wubaHouseDetails)){
            return ResultGenerator.genFailResult("请求资源不存在");
        }

        return ResultGenerator.genSuccessResult(wubaHouseDetails);
    }
}
