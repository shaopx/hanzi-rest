package app;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by shaopengxiang on 2016/11/9.
 */
public interface TestcollectionsRepository extends MongoRepository<Testcollections, String> {
}
