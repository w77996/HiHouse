package com.w77996.seed.house.service.impl;

import com.w77996.seed.house.core.base.AbstractService;
import com.w77996.seed.house.dao.PushHistoryMapper;
import com.w77996.seed.house.model.bean.PushHistoryBean;
import com.w77996.seed.house.service.IPushHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/12/30.
 */
@Service
@Transactional
public class PushHistoryServiceImpl extends AbstractService<PushHistoryBean> implements IPushHistoryService {
    @Resource
    private PushHistoryMapper pushHistoryMapper;

}
