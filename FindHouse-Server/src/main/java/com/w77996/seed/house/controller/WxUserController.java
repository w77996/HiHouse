package com.w77996.seed.house.controller;

import com.google.common.collect.Maps;
import com.w77996.seed.house.core.annotation.IgnoreToken;
import com.w77996.seed.house.core.annotation.NeedToken;
import com.w77996.seed.house.core.base.BaseController;
import com.w77996.seed.house.core.constant.CommonConstant;
import com.w77996.seed.house.core.constant.UserConst;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.view.UserView;
import com.w77996.seed.house.service.IUserService;
import com.w77996.seed.house.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author w77996
 * @description
 * @date 2020/5/15 22:56
 */
@Api("微信公众号用户相关接口")
@RestController
@Slf4j
@RequestMapping("/mp/user")
public class WxUserController extends BaseController {


    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private IUserService userService;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 通过code获得基本用户信息
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param code code
     */
    @ApiOperation(value = "通过code获得基本用户信息")
    @GetMapping(value = "/getWxUserInfoByCode")
    public Result getWxUserInfoByCode(@RequestParam(value = "code") String code) {
        Result result = new Result();
        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            wxMpUser = this.wxMpService.getUserService()
                    .userInfo(accessToken.getOpenId(), CommonConstant.LAND.getValue());
            UserBean userBean = userService.getUserByMpOpenId(wxMpUser.getOpenId());
            UserView userView = new UserView();
            if (Objects.isNull(userBean)) {
                //创建用户
                userView = userService.registerMpUser(wxMpUser);
                resultMap.put("user", userView);
                resultMap.put("token", jwtTokenUtil.generateToken(userView));
                return ResultGenerator.genSuccessResult(resultMap);
            } else {
                //获取用户
                userView = userService.getUserViewByCode(userBean, wxMpUser);
                jwtTokenUtil.generateToken(userBean);
                resultMap.put("user", userView);
                resultMap.put("token", jwtTokenUtil.generateToken(userView));
                return ResultGenerator.genSuccessResult(resultMap);
            }
        } catch (WxErrorException e) {
            result = ResultGenerator.genFailResult(e.getError().toString());
            log.error(e.getError().toString());
        }
        return result;
    }

    /**
     * 通过code获得基本用户信息
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param code code
     */
    @ApiOperation(value = "通过code获得微信基本用户信息")
    @GetMapping(value = "/getOAuth2UserInfo")
    public Result getOAuth2UserInfo(@RequestParam(value = "code") String code) {
        Result result = new Result();
        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;
        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            wxMpUser = this.wxMpService.getUserService()
                    .userInfo(accessToken.getOpenId(), CommonConstant.LAND.getValue());
            return ResultGenerator.genSuccessResult(wxMpUser);
        } catch (WxErrorException e) {
            result = ResultGenerator.genFailResult(e.getError().toString());
            log.error(e.getError().toString());
        }
        return result;
    }

    /**
     * 用code换取oauth2的openid
     * 详情请见: http://mp.weixin.qq.com/wiki/1/8a5ce6257f1d3b2afb20f83e72b72ce9.html
     *
     * @param code code
     */
    @ApiOperation(value = "用code换取oauth2的openid")
    @GetMapping(value = "/getOpenid")
    public Result getOpenid(@RequestParam(value = "code") String code) {
        Result result = new Result();
        WxMpOAuth2AccessToken accessToken;
        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            return ResultGenerator.genSuccessResult(accessToken);
        } catch (WxErrorException e) {
            result = ResultGenerator.genFailResult(e.getError().toString());
            log.error(e.getError().toString());
        }
        return result;
    }

    /**
     * 通过openid获得基本用户信息
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param openid openid
     */
    @ApiOperation(value = "通过openid获得微信用户信息")
    @GetMapping(value = "/getWxUserInfo")
    public Result getWxUserInfo(@RequestParam(value = "openid") String openid) {
        Result returnModel = new Result();
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = this.wxMpService.getUserService().userInfo(openid, CommonConstant.LAND.getValue());
            return ResultGenerator.genSuccessResult(wxMpUser);
        } catch (WxErrorException e) {
            returnModel = ResultGenerator.genFailResult(e.getError().toString());
            log.error(e.getError().toString());
        }
        return returnModel;
    }

    /**
     * 通过token获取用户信息
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     */
    @NeedToken
    @ApiOperation(value = "通过token获取用户信息")
    @GetMapping(value = "/getUserInfoByToken")
    public Result getUserInfoByToken() {
        Long userId = this.getUserIdByToken();
        UserView userView = userService.getUserLoginInfoById(userId);
        if (userView == null) {
            return ResultGenerator.genFailResult("用户不存在");
        }
        return ResultGenerator.genSuccessResult(userView);
    }

    /**
     * 设置用户设置推送
     * @param open
     * @return
     */
    @NeedToken
    @ApiOperation(value = "设置推送")
    @GetMapping(value = "/openPush")
    public Result openPush(@RequestParam("open") boolean open) {
        Long userId = this.getUserIdByToken();

        UserBean userBean = userService.getUserByUserId(userId);
        if (open) {
            userBean.addFlag(UserConst.Flag.IS_OPEN_PUSH);
        } else {
            userBean.removeFlag(UserConst.Flag.IS_OPEN_PUSH);
        }
        userService.update(userBean);
        return ResultGenerator.genSuccessResult();
    }
}
