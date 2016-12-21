package com.spx.gushi;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.spx.gushi.data.Poem;
import com.spx.gushi.data.PoemRepository;
import com.spx.gushi.data.PoemResult;
import com.spx.gushi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static com.spx.gushi.util.Utils.isNotNull;
import static com.spx.gushi.util.Utils.isNull;

/**
 * Created by Administrator on 2016/11/19.
 */
@RestController
public class PoemController {

    static final int POEM_MAX = 250000;

    static Logger logger = Logger.getLogger("PoemController");

    @Autowired
    private PoemRepository repository;

    @Autowired
    MongoOperations mongo;

    @Autowired
    GCacheManager cacheManager;


    @RequestMapping("/poems/{id}")
    public Poem poemById(@PathVariable String id) {
        logger.info("poemById  id:" + id);
        Poem poem = repository.findByPid(id);
        logger.info("poemById  poem:" + poem);

        return poem;
    }


    @RequestMapping("/poems/random/{num}")
    public PoemResult randomPoems(@PathVariable String num) {
        logger.info("randomPoems  num:" + num);

        int number = Integer.parseInt(num);
        List<Poem> poems = new ArrayList<>();
        Random ra = new Random();

        while (poems.size() < number) {
            int index = ra.nextInt(POEM_MAX);
            Poem poem = repository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                poems.add(poem);
            }

        }
        return PoemResult.buildResult(poems);
    }


    @RequestMapping("/poems")
    public PoemResult poemByName(@RequestParam(value = "name", defaultValue = "") String name,
                                 @RequestParam(value = "ciyu", defaultValue = "") String ciyu,
                                 @RequestParam(value = "zuozhe", defaultValue = "") String zuozhe,
                                 @RequestParam(value = "congshu", defaultValue = "") String congshu,
                                 @RequestParam(value = "chaodai", defaultValue = "") String chaodai,
                                 @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("poemByName  name:" + name + ", ciyu:" + ciyu
                + ", zuozhe:" + zuozhe + ", congshu:" + congshu
                + ", chaodai:" + chaodai + ", page:" + page);

        logger.info("cacheManager:" + cacheManager);
        List<Poem> poems = new ArrayList<>();

        String key = "0000000" + name + ciyu + zuozhe + congshu + chaodai;

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    poems = list;
                    return PoemResult.buildResult(page, poems);
                }
            }


            if (isNull(name) && isNull(ciyu) && isNull(zuozhe) && isNull(congshu)) {
                return PoemResult.buildResult(page, poems);
            }

            if (isNotNull(ciyu)) {
                poems = repository.findByYuanwenContains(ciyu);
                if (poems.size() == 0) {
                    return PoemResult.buildResult(page, poems);
                }
            }

            if (isNotNull(ciyu) && poems.size() > 0) {
                for (int i = poems.size() - 1; i >= 0; i--) {
                    Poem poem = poems.get(i);
                    if (isNotNull(name) && !poem.name.contains(name)) {
                        poems.remove(i);
                        continue;
                    }

                    if (isNotNull(zuozhe) && !zuozhe.equalsIgnoreCase(poem.zuozhe)) {
                        poems.remove(i);
                        continue;
                    }

                    if (isNotNull(congshu) && !congshu.equalsIgnoreCase(poem.congshu)) {
                        poems.remove(i);
                        continue;
                    }
                }

                return PoemResult.buildResult(page, poems);
            }


            if (isNotNull(name)) {
                poems = repository.findByNameContains(name);
                if (poems.size() == 0) {
                    return PoemResult.buildResult(page, poems);
                }
            }


            if (isNotNull(name) && poems.size() > 0) {

                for (int i = poems.size() - 1; i >= 0; i--) {
                    Poem poem = poems.get(i);

                    if (isNotNull(zuozhe) && !zuozhe.equalsIgnoreCase(poem.zuozhe)) {
                        poems.remove(i);
                        continue;
                    }

                    if (isNotNull(congshu) && !congshu.equalsIgnoreCase(poem.congshu)) {
                        poems.remove(i);
                        continue;
                    }
                }

                return PoemResult.buildResult(page, poems);
            }


            DBObject dbObject = new BasicDBObject();

            if (Utils.isNotNull(zuozhe)) {
                dbObject.put("zuozhe", zuozhe);
            }
            if (Utils.isNotNull(congshu)) {
                dbObject.put("congshu", congshu);
            }

            if (dbObject.keySet().size() == 0) {
                return PoemResult.buildResult(page, poems);
            }

            poems = repository.findPoems(dbObject);


            return PoemResult.buildResult(page, poems);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, poems);
            }
        }
    }
}
