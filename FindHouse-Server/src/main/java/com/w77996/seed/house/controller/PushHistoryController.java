package com.w77996.seed.house.controller;

import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.PushHistoryBean;
import com.w77996.seed.house.service.IPushHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/12/30.
*/
@RestController
@RequestMapping("/push/history")
public class PushHistoryController {
    @Resource
    private IPushHistoryService pushHistoryService;

    @PostMapping("/add")
    public Result add(PushHistoryBean pushHistory) {
        pushHistoryService.save(pushHistory);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        pushHistoryService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(PushHistoryBean pushHistory) {
        pushHistoryService.update(pushHistory);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        PushHistoryBean pushHistory = pushHistoryService.findById(id);
        return ResultGenerator.genSuccessResult(pushHistory);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<PushHistoryBean> list = pushHistoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
