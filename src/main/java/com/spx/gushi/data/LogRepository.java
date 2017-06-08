package com.spx.gushi.data;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by SHAOPENGXIANG on 2017/6/5.
 */
public interface LogRepository extends MongoRepository<Log, String> {
    public Page<Log> findByUidOrderByUptimeDesc(String uid, org.springframework.data.domain.Pageable pageable);
//    public List<Log> findDistinctLogByUid();
}
