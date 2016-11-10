package app;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by shaopengxiang on 2016/11/10.
 */
public interface ShiyiRepository extends MongoRepository<Shiyi, String> {

    public Shiyi findByYin(String yin);
    public Shiyi findByWord(String word);
}
