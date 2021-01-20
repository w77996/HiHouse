package com.w77996.seed.house.controller;

import com.w77996.seed.house.core.base.BaseController;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2020/05/16.
 * @author w77996
 */
@Api("用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    @PostMapping("/add")
    public Result add(UserBean userBean) {
        userService.save(userBean);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(UserBean userBean) {
        userService.update(userBean);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        UserBean userBean = userService.findById(id);
        return ResultGenerator.genSuccessResult(userBean);
    }

    @ApiOperation(value = "获取所有用户")
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<UserBean> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/getUserByMpOpenId")
    public Result getUserByMpOpenId(@RequestParam("openId") String openId) {
        UserBean userByMpOpenId = userService.getUserByMpOpenId(openId);
        return ResultGenerator.genSuccessResult(userByMpOpenId);
    }
}
