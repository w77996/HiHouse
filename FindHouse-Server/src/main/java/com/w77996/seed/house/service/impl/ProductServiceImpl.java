package com.w77996.seed.house.service.impl;

import com.w77996.seed.house.core.base.AbstractService;
import com.w77996.seed.house.dao.ProductMapper;
import com.w77996.seed.house.model.bean.ProductBean;
import com.w77996.seed.house.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/11/08.
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractService<ProductBean> implements IProductService {
    @Resource
    private ProductMapper productMapper;

}
