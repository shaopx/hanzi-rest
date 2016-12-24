package com.spx.gushi.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/12/24.
 */
public interface BaikeRepository extends MongoRepository<Baike, String> {

    public Baike findByPid(String pid);
}
