package com.w77996.seed.house.model.bean.user;


import com.w77996.seed.house.model.bean.city.CityInfoBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author w77996
 * @description
 * @date 2020/5/24 17:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "db_user_subscribe")
public class UserSubscribeBean {

    @Id
    private String _id;

    private Long userId;

    /**
     * 发布类型1.出售 2.出租（多选则逗号分隔 1,2）
     */
    private List<Integer> publishTypes;
    /**
     * 1.住宅 2.写字楼 （暂时只有住宅，多选则逗号分隔 1,2）
     */
    private List<Integer> houseTypes;
    /**
     * 城市ID
     */
    private Long cityId;

    private String cityName;

    private List<CityInfoBean> subscribeCityInfo;


}
