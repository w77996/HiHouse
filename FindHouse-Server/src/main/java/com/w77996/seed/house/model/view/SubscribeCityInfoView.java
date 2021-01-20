package com.w77996.seed.house.model.view;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author w77996
 * @description
 * @date 2020/5/24 13:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeCityInfoView {


    private String provinceName;

    private Integer cityId;

    private String cityName;
}
