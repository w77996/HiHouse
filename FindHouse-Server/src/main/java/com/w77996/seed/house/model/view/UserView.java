package com.w77996.seed.house.model.view;

import com.w77996.seed.house.model.bean.city.CityInfoBean;
import com.w77996.seed.house.model.bean.user.UserBean;
import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import com.w77996.seed.house.model.dto.SubscribeCityDto;
import lombok.*;

import java.util.List;

/**
 * @author w77996
 * @description
 * @date 2020/5/18 23:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView extends UserBean {

    /**
     * 第一次注册
     */
    private boolean isFirstRegister;
    /**
     * 是否为vip
     */
    private boolean isVip;
    /**
     *是否关注公众号（未关注不允许进入页面）
     */
    private boolean isSubscribe;
    /**
     * 是否开启推送订阅
     */
    private boolean isOpenMsgPush;
    /**
     * 是否需要充值
     */
    private boolean isNeedCharge;
    /**
     * 是否三天免费
     */
    private boolean isThirdDayFree;
    /**
     * 订阅的区域
     */
    private UserSubscribeBean subscribeInfo;
    /**
     * 剩余天数
      */
    private String leftDate;


}
