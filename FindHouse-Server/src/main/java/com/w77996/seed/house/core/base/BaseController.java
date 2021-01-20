package com.w77996.seed.house.core.base;

import com.w77996.seed.house.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author w77996
 * @description
 * @date 2020/5/26 21:40
 */
public class BaseController {

    @Autowired
    private ITokenService tokenService;

    public Long getUserIdByToken(){
        return tokenService.getUserIdByToken();
    }
}
