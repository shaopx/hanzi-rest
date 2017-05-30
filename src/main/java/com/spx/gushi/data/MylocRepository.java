package com.spx.gushi.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */
public interface MylocRepository extends MongoRepository<Myloc, String> {

    List<Myloc> findTop40ByUidOrderByUptimeDesc(String uid);

    Page<Myloc> findByUidOrderByUptimeDesc(String uid, org.springframework.data.domain.Pageable pageable);
}
