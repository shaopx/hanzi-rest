package com.spx.gushi.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by shaopengxiang on 2016/11/10.
 */
public interface ShiyiRepository extends MongoRepository<Shiyi, String> {

    public List<Shiyi> findByYin(String yin);
    public Shiyi findByWord(String word);
}
