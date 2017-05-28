package com.spx.gushi.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */
public interface MylocRepository extends MongoRepository<Myloc, String> {

    List<Myloc> findTop20ByUidOrderByUptimeDesc(String uid);
}
