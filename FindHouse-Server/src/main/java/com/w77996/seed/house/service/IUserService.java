package com.w77996.seed.house.service;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.core.base.Service;
import com.w77996.seed.house.model.view.UserView;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2020/05/16.
 */
public interface IUserService extends Service<UserBean> {

    /**
     * 通过unionId获取用户信息
     *
     * @param unionId
     * @return
     */
    UserBean getUserByUnionId(String unionId);
    /**
     * 通过公众号openId获取用户信息
     *
     * @param openId
     * @return
     */
    UserBean getUserByMpOpenId(String openId);

    UserView registerMpUser(WxMpUser wxMpUser);

    UserBean getUserByUserId(Long id);

    UserView getUserLoginInfoById(Long userId);

    /**
     * 获取用户
     * @param userBean
     * @param wxMpUser
     * @return
     */
    UserView getUserViewByCode(UserBean userBean, WxMpUser wxMpUser);

    List<UserBean> findByUserIdList(List<Long> userIds);
}
