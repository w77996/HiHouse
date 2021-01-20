package com.w77996.seed.house.service;

import org.springframework.stereotype.Service;

/**
 * @description: token业务
 * @author: straw
 **/

public interface ITokenService {

    /**
     * 通过token获取用户信息
     *
     * @return
     */
    public Long getUserIdByToken();
}
