package com.w77996.seed.house.model.dto;

import com.w77996.seed.house.model.bean.user.VipBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName VipView
 * @Description
 * @author w77996
 * @date 2020/11/3 21:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipDto extends VipBean {

    String leftDate;
}
