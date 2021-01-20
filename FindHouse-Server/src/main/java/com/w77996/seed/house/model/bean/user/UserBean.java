package com.w77996.seed.house.model.bean.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.w77996.seed.house.core.constant.UserConst;
import lombok.*;

import java.util.Date;
import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class UserBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    @Column(name = "head_image")
    private String headImage;

    /**
     * 值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    private String unionId;
    /**
     * 用户flag
     */
    private Long flag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_push_time")
    private Date lastPushTime;


    /**
     * 增加标识位
     *
     * @param flag
     */
    public long addFlag(UserConst.Flag flag) {
        if (this.flag == null) {
            this.flag = 0L;
        }
        if (flag != null) {
            this.flag = this.flag | flag.getValue();
        }
        return this.flag;
    }

    /**
     * 增加标识位
     *
     * @param flag
     */
    public long removeFlag(UserConst.Flag flag) {
        if (this.flag == null) {
            this.flag = 0L;
        }

        if (flag != null) {
            this.flag &= ~flag.getValue();
        }
        return this.flag;
    }
}