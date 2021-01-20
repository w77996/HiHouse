package com.w77996.seed.house.model.bean.city;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @ClassName CityInfoBean
 * @Description
 * @author w77996
 * @date 2020/10/9 16:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "wuba_city")
@ApiModel(value = "CityInfoBean对象", description = "城市对象")
public class CityInfoBean implements Serializable {

    @Id
    private String _id;

    private String province;

    private String city;

    @Field("city_id")
    private String cityId;
    @Field("city_pinyin")
    private String cityPinyin;
    @Field("city_url")
    private String cityUrl;

    private String area;
    @Field("area_id")
    private String areaId;
    @Field("area_pinyin")
    private String areaPinyin;

    private String business;
    @Field("business_id")
    private String businessId;
    @Field("business_pinyin")
    private String businessPinyin;
}