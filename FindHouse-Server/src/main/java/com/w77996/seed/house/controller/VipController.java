//package com.w77996.seed.house.web;
//import com.w77996.seed.house.core.result.Result;
//import com.w77996.seed.house.core.result.ResultGenerator;
//import com.w77996.seed.house.model.bean.user.VipBean;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
//* Created by CodeGenerator on 2020/10/13.
//*/
//@RestController
//@RequestMapping("/vip")
//public class VipController {
//    @Resource
//    private IVipService vipService;
//
//    @PostMapping("/add")
//    public Result add(VipBean vip) {
//        vipService.save(vip);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @PostMapping("/delete")
//    public Result delete(@RequestParam Long id) {
//        vipService.deleteById(id);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @PostMapping("/update")
//    public Result update(VipBean vip) {
//        vipService.update(vip);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @PostMapping("/detail")
//    public Result detail(@RequestParam Long id) {
//        VipBean vip = vipService.findById(id);
//        return ResultGenerator.genSuccessResult(vip);
//    }
//
//    @PostMapping("/list")
//    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
//        PageHelper.startPage(page, size);
//        List<VipBean> list = vipService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultGenerator.genSuccessResult(pageInfo);
//    }
//}
