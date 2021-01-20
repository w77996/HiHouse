package com.w77996.seed.house.event;

import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName NotifyPhoneDto
 * @Description
 * @author w77996
 * @date 2020/11/14 21:20
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyPhoneDto {

    WubaHouseDetail wubaHouseDetail;
}
