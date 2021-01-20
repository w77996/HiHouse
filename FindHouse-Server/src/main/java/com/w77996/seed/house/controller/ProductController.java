package com.w77996.seed.house.controller;

import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.ProductBean;
import com.w77996.seed.house.service.IProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/11/08.
*/
@Api("套餐")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private IProductService productService;

    @PostMapping("/add")
    public Result add(ProductBean product) {
        productService.save(product);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        productService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(ProductBean product) {
        productService.update(product);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        ProductBean product = productService.findById(id);
        return ResultGenerator.genSuccessResult(product);
    }

    @GetMapping("/list")
    public Result list() {
        List<ProductBean> list = productService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
}
