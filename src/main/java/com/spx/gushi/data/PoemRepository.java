package com.spx.gushi.data;

import com.mongodb.DBObject;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
public interface PoemRepository extends MongoRepository<Poem, String> {

    public List<Poem> findByZuozhe(String zuozhe);
    public List<Poem> findByChaodai(String chaodai);
    public List<Poem> findByFenlei(String fenlei);
    public List<Poem> findByCongshu(String congshu);
    public List<Poem> findByZhaiyao(String zhaiyao);
    public List<Poem> findByName(String mingcheng);
    public List<Poem> findByZhujieNotNull();
    public List<Poem> findByNameContains(String namePiece);
    public List<Poem> findByYuanwenContains(String yuanwen);
    public Page<Poem> findByYuanwenContains(String yuanwen, org.springframework.data.domain.Pageable pageable);

    @Query("?0")
    public List<Poem> findPoems(DBObject queryCondition);
    public Poem findByPid(String pid);
}
