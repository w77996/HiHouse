package com.w77996.seed.house.model.bean.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_vip")
public class VipBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 1.会员 2.过期
     */
    private Integer status;

    /**
     * 现金购买会员到期时间
     */
    @Column(name = "expire_buy_time")
    private Date expireBuyTime;

    /**
     * 全局会员到期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 创建时间（成为会员时间）
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}