package com.w77996.seed.house.core.constant;

import io.swagger.models.auth.In;

/**
 * @ClassName VipConstant
 * @Description
 * @author w77996
 * @date 2020/10/14 12:23
 */
public class VipConstant {

    public enum Status{
        /**
         * 会员
         */
        VIP(1),
        /**
         * 过期
         */
        EXPIRE(2);

        private Integer value;

        private Status(Integer value) {
            this.value = value;
        }


        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
