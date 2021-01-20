package com.w77996.seed.house.model.bean.wuba;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WuPhoneResult
 * @Description
 * @author w77996
 * @date 2020/11/14 20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WubaPhoneResult {

    //{
    //    "status": "0",
    //    "msg": "OK",
    //    "message": "OK",
    //    "requestTime": "182",
    //    "data": {
    //        "phone": "13086258544"
    //    },
    //    "server_time": "2020-11-14 18:44:51"
    //}

    //{
    //    "status": "403",
    //    "msg": "获取失败，请稍后重试",
    //    "message": "获取失败，请稍后重试",
    //    "requestTime": "6",
    //    "data": null,
    //    "server_time": "2020-11-14 20:12:35"
    //}
    String status;

    String message;

    PhoneData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneData{
        String phone;
    }
}
