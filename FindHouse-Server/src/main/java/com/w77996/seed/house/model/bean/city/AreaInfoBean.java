package com.w77996.seed.house.model.bean.city;

import lombok.Data;

import java.util.List;

/**
 * @author w77996
 * @description
 * @date 2020/5/29 16:31
 */
@Data
public class AreaInfoBean {


    private Integer areaId;

    private String areaName;

    private String areaUrl;

    private List<BusinessCircleInfoBean> businessCircleList;
}
