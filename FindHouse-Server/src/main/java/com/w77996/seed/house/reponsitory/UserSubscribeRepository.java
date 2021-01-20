package com.w77996.seed.house.reponsitory;

import com.w77996.seed.house.model.bean.user.UserSubscribeBean;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author w77996
 * @description
 * @date 2020/5/28 22:29
 */
public interface UserSubscribeRepository extends MongoRepository<UserSubscribeBean, String> {


    UserSubscribeBean findByUserId(Long userId);
}
