package com.spx.gushi.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by SHAOPENGXIANG on 2017/1/3.
 */
public interface ZuozheRepository extends MongoRepository<Zuozhe, String> {

    public List<Zuozhe> findByXingming(String xingming);
}
