package com.w77996.seed.house.model.dto;

import com.w77996.seed.house.model.bean.city.CityInfoBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author w77996
 * @description
 * @date 2020/5/26 21:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SubscribeCityDto对象", description = "用户订阅城市对象")
public class SubscribeCityDto implements Serializable {
    /**
     * 发布类型1.出售 2.出租（多选则逗号分隔 1,2）
     */
    @ApiModelProperty(value = "发布类型 1.出售 2.出租（多选则逗号分隔 1,2）", name = "publishTypes", example = "1", required = true)
    private List<Integer> publishTypes;
    /**
     * 1.住宅 2.写字楼 （暂时只有住宅，多选则逗号分隔 1,2）
     */
    @ApiModelProperty(value = "房屋类型 1.住宅 2.写字楼 （暂时只有住宅，多选则逗号分隔 1,2）", name = "houseTypes", example = "1", required = true)
    private List<Integer> houseTypes;
    /**
     * 城市ID
     */
    @ApiModelProperty(value = "城市ID", name = "cityId", example = "9427", required = true)
    private Long cityId;

    @ApiModelProperty(value = "城市名", name = "cityName", example = "伊犁", required = true)
    private String cityName;

    @ApiModelProperty(value = "城市对象", name = "subscribeCityInfo", example = " {\n" +
            "            \"province\" : \"新疆\",\n" +
            "            \"city\" : \"伊犁\",\n" +
            "            \"cityId\" : \"9472\",\n" +
            "            \"cityPinyin\" : \"yili\",\n" +
            "            \"cityUrl\" : \"https://yili.58.com\",\n" +
            "            \"area\" : \"伊宁市\",\n" +
            "            \"areaId\" : \"9484\",\n" +
            "            \"areaPinyin\" : \"yiningshi\",\n" +
            "            \"business\" : \"伊宁城区\"\n" +
            "        }" ,required = true)
    private List<CityInfoBean> subscribeCityInfo;


}
