package com.w77996.seed.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CreateOrderDto
 * @Description
 * @author w77996
 * @date 2020/11/9 12:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderDto implements Serializable {

    private Long productId;


}
