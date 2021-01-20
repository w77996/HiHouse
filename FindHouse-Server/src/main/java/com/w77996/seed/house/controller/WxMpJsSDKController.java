package com.w77996.seed.house.controller;

import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author w77996
 * @description
 * @date 2020/5/24 13:41
 */
@Api("JS_SDK相关接口")
@Slf4j
@RestController
@RequestMapping("/jsSDK")
public class WxMpJsSDKController {

    @Autowired
    private WxMpService wxMpService;

    @ApiOperation(value = "获取JsApiTicket")
    @GetMapping("/getJsApiTicket")
    public Result getJsApiTicket(){
        try {
            String jsapiTicket = wxMpService.getJsapiTicket();
            return ResultGenerator.genSuccessResult(jsapiTicket);
        } catch (WxErrorException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }


    @ApiOperation(value = "获取JsApiTicket(forceRefresh)")
    @GetMapping("/getJsApiTicketForceRefresh")
    public Result getJsApiTicketForceRefresh(@RequestParam("forceRefresh") boolean forceRefresh){
        try {
            String jsapiTicket = wxMpService.getJsapiTicket(forceRefresh);
            return ResultGenerator.genSuccessResult(jsapiTicket);
        } catch (WxErrorException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @ApiOperation(value = "获取JsApi签名")
    @GetMapping("/createJsApiSignature")
    public Result createJsApiSignature(@RequestParam("url") String url){
        try {
            WxJsapiSignature jsApiSignature = wxMpService.createJsapiSignature(url);
            return ResultGenerator.genSuccessResult(jsApiSignature);
        } catch (WxErrorException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
