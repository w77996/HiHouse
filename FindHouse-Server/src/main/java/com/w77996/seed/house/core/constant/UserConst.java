package com.w77996.seed.house.core.constant;

/**
 * @author w77996
 * @description
 * @date 2020/5/17 17:55
 */
public class UserConst {
    /**
     * 用户标识，按位表示
     */
    public static enum Flag {
        /**
         * 用户标记，第1位：1会员用户
         */
        IS_VIP(0x1L),
        /**
         * 用户标记，第2位：2是否开启推送
         */
        IS_OPEN_PUSH(0x2L);

        private long value;

        private Flag(long value) {
            this.value = value;
        }

        public static boolean isFlag(Flag flagConst, Long flag) {
            if (flag == null) {
                flag = 0L;
            }
            return ((flag & flagConst.getValue()) == flagConst.getValue() ? true : false);
        }




        public long getValue() {
            return value;
        }
    }

}
