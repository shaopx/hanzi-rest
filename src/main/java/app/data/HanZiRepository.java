package app.data;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by shaopengxiang on 2016/11/9.
 */
public interface HanZiRepository extends MongoRepository<HanZi, String> {

    public HanZi findByYin(String yin);
//    public List<HanZi> findByLastName(String lastName);
}
