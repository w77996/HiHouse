package com.w77996.seed.house.reponsitory;

import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author w77996
 * @description
 * @date 2020/6/7 16:31
 */
public interface WuBaHouseRepository extends MongoRepository<WubaHouseDetail, String> {

//    List<WubaHouseDetail> findByBusinessIn(List<String> business,Pageable pageable);
}
