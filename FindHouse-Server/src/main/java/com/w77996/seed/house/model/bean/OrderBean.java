package com.w77996.seed.house.model.bean;

import io.swagger.models.auth.In;
import lombok.*;

import java.util.Date;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t_order")
public class OrderBean {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 订单父类型 1 会员2 邀请营销
     */
    @Column(name = "order_type")
    private Integer orderType;

    private Long orderNo;

    /**
     * 订单名
     */
    private String name;

    /**
     * 子项目num和
     */
    @Column(name = "goods_count")
    private Integer goodsCount;

    /**
     * 总价格(人民币表示，精确到分)
     */
    @Column(name = "total_fee")
    private Integer totalFee;

    /**
     * 订单状态 0未付款 1已付款 2失败 3作废
     */
    private Integer status;

    /**
     * 1:ios 2:android
     */
    private Integer os;

    /**
     * 订单描述
     */
    private String remark;

    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 通知时间
     */
    @Column(name = "notify_time")
    private Date notifyTime;

    /**
     * 更新时间（ 版本并发控制）
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}