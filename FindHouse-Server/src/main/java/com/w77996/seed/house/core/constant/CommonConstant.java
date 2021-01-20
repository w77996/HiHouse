package com.w77996.seed.house.core.constant;

/**
 * @author w77996
 * @description
 * @date 2020/5/16 12:46
 */
public enum CommonConstant {


    LAND("zh_CN");

    private String value;

    CommonConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
