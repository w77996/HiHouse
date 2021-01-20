package com.w77996.seed.house.model.dto;

import lombok.Data;

/**
 * @author w77996
 * @description
 * @date 2020/6/7 16:34
 */
@Data
public class SaleSearchDto {

    /**
     * 1.出售 2.出租
     */
    int publishType;
    /**
     * true.订阅的区域 false.自选区域
     */
    boolean showSubscribeArea;
    /**
     * showSubscribeArea为false选择整个区域的id
     */
    String selectAreaIds;
    /**
     * showSubscribeArea为false选择商圈的id
     */
    String selectBusinessCircle;
    /**
     * 销售最低总价
     */
    int salePriceMin;
    /**
     * 销售最高总价
     */
    int salePriceMax;
    /**
     * 最小面积
     */
    int houseAreaMin;
    /**
     * 最大面积
     */
    int houseAreaMax;
    /**
     * 户型1.一室 2.两室 3.三室 4.四室 5.五室 6.五室以上  (多选则逗号分隔 1,2）
     */
    int apartmentType;
    /**
     * 1.住宅 2.写字楼 （暂时只有住宅，多选则逗号分隔 1,2）
     */
    int houseType;
    /**
     * 1.今日 2.一周内 3.一个月内 4.一个月前
     */
    int dateRange;
    /**
     * 过滤1.虚假
     */
    int filter;
}
