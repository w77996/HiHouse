package com.w77996.seed.house.model.view;

import com.w77996.seed.house.model.bean.city.CityInfoBean;
import com.w77996.seed.house.model.bean.city.SubscribeCityConfigBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SubscribeCityConfigView
 * @Description
 * @author w77996
 * @date 2020/10/13 14:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeCityConfigView implements Serializable {

    private String provinceName;

    private List<SubscribeCityConfigBean> cityInfo;
}
