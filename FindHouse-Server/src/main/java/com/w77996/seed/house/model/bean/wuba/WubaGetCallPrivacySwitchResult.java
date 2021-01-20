package com.w77996.seed.house.model.bean.wuba;

import lombok.Data;

/**
 * @ClassName WubaGetcallPrivacySwitchResult
 * @Description
 * @author w77996
 * @date 2020/8/6 22:17
 */
@Data
public class WubaGetCallPrivacySwitchResult {
    //{
    //    "status": "0",
    //    "msg": "OK",
    //    "message": "OK",
    //    "requestTime": "6",
    //    "data": {
    //        "is_privacy": 0,
    //        "called_phone": "13309999987"
    //    },
    //    "server_time": "2020-08-06 22:13:13"
    //}

    private String status;

    private String msg;

    private String requestTime;

    private String mssage;

    private String serverTime;


    @Data
    public static class WubaGetCallPrivacySwitchData{

        private Integer isPrivacy;

        private String calledPhone;

    }



}
