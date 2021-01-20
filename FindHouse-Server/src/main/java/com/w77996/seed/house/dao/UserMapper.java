package com.w77996.seed.house.dao;

import com.w77996.seed.house.core.base.Mapper;
import com.w77996.seed.house.model.bean.user.UserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends Mapper<UserBean> {

    /**
     * 通过公众号openId获取用户信息
     *
     * @param openId
     * @return
     */
    UserBean getUserByMpOpenId(String openId);

    /**
     * 通过公众号unionId获取用户信息
     *
     * @param unionId
     * @return
     */
    UserBean getUserByUnionId(String unionId);

    UserBean getUserByUserId(Long id);

    List<UserBean> findByUserIdList(@Param("userIds") List<Long> userIds);
}