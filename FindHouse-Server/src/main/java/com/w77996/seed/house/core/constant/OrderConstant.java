package com.w77996.seed.house.core.constant;

/**
 * @author w77996
 * @description
 * @date 2020/5/16 12:43
 */
public class OrderConstant {


    public enum STATUS {
        /**
         * 等待支付
         */
        WAIT_PAY(0),
        /**
         * 已支付
         */
        PAY(1);

        private Integer status;

        STATUS(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    public enum PayType {
        /**
         * 微信
         */
        WX(1),
        /**
         * 支付宝
         */
        ALI_PAY(2);

        private Integer type;

        PayType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    public enum OrderType{
        /**
         * 微信
         */
        Recharge(1);

        private Integer type;

        OrderType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }


}
