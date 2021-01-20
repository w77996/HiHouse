package com.w77996.seed.house.controller;/**
 * @ClassName GetWxSginController
 * @Description
 * @author w77996
 * @date 2020/10/12 17:39
 */

import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName GetWxSginController
 * @Description
 * @author w77996
 * @date 2020/10/12 17:39
 */

@Slf4j
@Controller
@RequestMapping("/sign")
public class GetWxSginController {


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    @ApiOperation(value = "测试微信公众号的接口配置信息", notes = "接口配置信息", httpMethod = "GET")
    public String getWxUserInfo(HttpServletRequest request,
                                @ApiParam(value = "微信求的 echostr") @RequestParam(required = true) String echostr
    ) {
        try {
            //只需要把微信请求的 echostr, 返回给微信就可以了
            log.info("测试来过===================" + echostr);
            return echostr;
        } catch (Exception e) {
            log.info("测试微信公众号的接口配置信息发生异常：", e);
            return "esultGenerator.genFailResult()";
        }
    }
}
