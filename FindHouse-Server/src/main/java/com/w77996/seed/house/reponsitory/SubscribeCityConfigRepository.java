package com.w77996.seed.house.reponsitory;

import com.w77996.seed.house.model.bean.city.SubscribeCityConfigBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/**
 * @author w77996
 * @description
 * @date 2020/5/29 15:57
 */
@Service
public interface SubscribeCityConfigRepository extends MongoRepository<SubscribeCityConfigBean, String> {
}
