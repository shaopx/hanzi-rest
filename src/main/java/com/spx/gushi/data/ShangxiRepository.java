package com.spx.gushi.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by SHAOPENGXIANG on 2017/1/18.
 */
public interface ShangxiRepository extends MongoRepository<Shangxi, String> {

    public List<Shangxi> findByPid(String pid);
}
