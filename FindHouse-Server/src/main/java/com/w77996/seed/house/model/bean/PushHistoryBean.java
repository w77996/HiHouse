package com.w77996.seed.house.model.bean;

import lombok.*;

import java.util.Date;
import javax.persistence.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t_push_history")
public class PushHistoryBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "resource_id")
    private String resourceId;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    private String remarks;

}