package com.spx.gushi;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.spx.gushi.data.*;
import com.spx.gushi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
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

    static String GUSHICIDIAN = "<<古诗词典>>";
    static String BAIDUBAIKE = "'百度百科'";
    static String TAGNSHIJIANSHANG = "<<唐诗鉴赏>>";

    @Autowired
    private PoemRepository poemRepository;

    @Autowired
    private ZuozheRepository zuozheRepository;

    @Autowired
    private BaikeRepository baikeRepository;

    @Autowired
    private ShangxiRepository shangxiRepository;

    @Autowired
    MongoOperations mongo;

    @Autowired
    GCacheManager cacheManager;


    @RequestMapping("/poems/{id}")
    public Poem poemById(@PathVariable String id) {
        logger.info("poemById  id:" + id);
        Poem poem = poemRepository.findByPid(id);
        logger.info("poemById  poem:" + poem);

        return poem;
    }

    @RequestMapping("/poems/zuozhe/{name}")
    public PoemResult zuozhe(@PathVariable String name, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("zuozhe  name:" + name + ", page:" + page);
        List<Zuozhe> zuozheList = zuozheRepository.findByXingming(name);
        //logger.info("zuozhe:" + zuozheList);

        String key = "0000000_zuozhe" + name;

        List<Result> data = new ArrayList<>();

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    data = list;
                    return PoemResult.buildResult(page, data);
                }
            }

            for (Zuozhe zuozhe : zuozheList) {
                Result pf = Result.build().src("gscd").type("zuozhe");
                pf.put("zid", zuozhe.zid);
                pf.put("content", zuozhe.jieshao);
                data.add(pf);
            }



            return PoemResult.buildResult(data);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, data);
            }
        }
    }


    @RequestMapping("/poems/zhushi/{id}")
    public PoemResult zhushi(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("zhushi  id:" + id + ", page:" + page);
        Poem poem = poemRepository.findByPid(id);
        logger.info("poemById  poem:" + poem);

        String key = "0000000_zhushi" + id;

        List<Result> data = new ArrayList<>();

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    data = list;
                    return PoemResult.buildResult(page, data);
                }
            }


            if (poem.zhujie != null) {
                if (Utils.isNotNull(poem.zhujie)) {
//                    Result pf = Result.build().src("gscd").type("zhushi");
//                    pf.put("pid", id);
//                    pf.put("content", poem.zhujie);
//                    pf.put("yuanwen", poem.yuanwen);
//                    data.add(pf);

                    ZhushiHandler.makeZhushiResult(poem.yuanwen, poem.zhujie, data);
                }

            }

            Baike baike = baikeRepository.findByPid(id);
            if (baike != null) {
                if (Utils.isNotNull(baike.zhujie)) {
//                    addBaikeData(id, "zhushi", baike.zhujie, data);

//                    String zhushi = Utils.getSubstring(baike.zhujie, "注解", poem.name+"白话译文");
//                    ZhushiHandler.makeZhushiResult(zhushi, data);
                }
                if (Utils.isNotNull(baike.zhushi)) {
                    String zhushi = Utils.getSubstring(baike.zhushi, "词句注释", poem.name+"白话译文");
                    ZhushiHandler.makeZhushiResult(poem.yuanwen, zhushi, data);


                    //addBaikeData(id, "zhushi", baike.zhushi, data);
                }
//                if (Utils.isNotNull(baike.yiwen)) {
//                    addBaikeData(id, baike.yiwen, d);
//                }
            }

            logger.info("yiwen:"+poem.yiwen);
            if (Utils.isNotNull(poem.yiwen)) {
                Result pf = Result.build().src("gscd").type("yiwen");
                pf.put("pid", id);
                pf.put("content", poem.yiwen);

                data.add(pf);
            }

            return PoemResult.buildResult(data);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, data);
            }
        }
    }


    @RequestMapping("/poems/yiwen/{id}")
    public PoemResult yiwen(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("yiwen  id:" + id + ", page:" + page);
        Poem poem = poemRepository.findByPid(id);
        logger.info("poemById  poem:" + poem);

        String key = "0000000_yiwen" + id;

        List<Result> data = new ArrayList<>();

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    data = list;
                    return PoemResult.buildResult(page, data);
                }
            }


            if (poem.yiwen != null) {
                if (Utils.isNotNull(poem.yiwen)) {
                    Result pf = Result.build().src("gscd").type("yiwen");
                    pf.put("pid", id);
                    pf.put("content", poem.yiwen);
                    data.add(pf);
                }
            }

            Baike baike = baikeRepository.findByPid(id);
            if (baike != null) {
                if (Utils.isNotNull(baike.yiwen)) {
                    addBaikeData(id, "yiwen", baike.yiwen, data);
                }
            }

            return PoemResult.buildResult(data);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, data);
            }
        }
    }

    @RequestMapping("/poems/shangxi/{id}")
    public PoemResult shangxi(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("shangxi  id:" + id + ", page:" + page);
        Poem poem = poemRepository.findByPid(id);
        logger.info("poemById  poem:" + poem);

        String key = "0000000_sx" + id;

        List<Result> shangxis = new ArrayList<>();

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    shangxis = list;
                    return PoemResult.buildResult(page, shangxis);
                }
            }

            List<Shangxi> shangxiList = shangxiRepository.findByPid(id);
            for (Shangxi shangxi : shangxiList) {
                Result pf = Result.build().src(shangxi.src.trim()).type("shangxi");
                pf.put("pid", id);
                pf.put("content",  shangxi.shangxi.trim());
                shangxis.add(pf);
            }


//            if (poem.shangxis != null && poem.shangxis.size() > 0) {
//                for (Poem.Shangxi shangxi : poem.shangxis) {
//                    Result pf = Result.build().src(shangxi.src.trim()).type("shangxi");
//                    pf.put("pid", id);
//                    pf.put("content",  shangxi.shangxi.trim());
//                    shangxis.add(pf);
//                }
//            }
//
//            Baike baike = baikeRepository.findByPid(id);
//            if (baike != null) {
//                if (Utils.isNotNull(baike.beijing)) {
//                    addBaikeData(id, "shangxi", baike.beijing, shangxis);
//                }
//                if (Utils.isNotNull(baike.sum)) {
//                    addBaikeData(id, "shangxi", baike.sum, shangxis);
//                }
//                if (Utils.isNotNull(baike.shangxi1)) {
//                    addBaikeData(id, "shangxi", baike.shangxi1, shangxis);
//                }
//                if (Utils.isNotNull(baike.shangxi2)) {
//                    addBaikeData(id, "shangxi", baike.shangxi2, shangxis);
//                }
//            }

            return PoemResult.buildResult(shangxis);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, shangxis);
            }
        }
    }

    private void addBaikeData(String pid, String type, String text, List<Result> results) {
        Result pf = Result.build().src("baidu_baike").type(type);
        pf.put("pid", pid);
        pf.put("content", text);
        results.add(pf);
    }

    @RequestMapping("/poems/random/{num}")
    public PoemResult randomPoems(@PathVariable String num) {
        logger.info("randomPoems  num:" + num);

        int number = Integer.parseInt(num);
        List results = new ArrayList<>();
        Random ra = new Random();

        {
            int index = 13952;
            Poem poem = poemRepository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                Result rf = Result.build().type("poem");
                rf.put("yuanwen", poem.yuanwen);
                rf.put("zuozhe", poem.zuozhe);
                rf.put("chaodai", poem.chaodai);
                rf.put("name", poem.name);
                rf.put("pid", poem.pid);
                results.add(rf);
            }
        }

        {
            int index = 9542;
            Poem poem = poemRepository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                Result rf = Result.build().type("poem");
                rf.put("yuanwen", poem.yuanwen);
                rf.put("zuozhe", poem.zuozhe);
                rf.put("chaodai", poem.chaodai);
                rf.put("name", poem.name);
                rf.put("pid", poem.pid);
                results.add(rf);
            }
        }


        while (results.size() < number) {
            int index = ra.nextInt(POEM_MAX);
            Poem poem = poemRepository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                Result rf = Result.build().type("poem");
                rf.put("yuanwen", poem.yuanwen);
                rf.put("zuozhe", poem.zuozhe);
                rf.put("chaodai", poem.chaodai);
                rf.put("name", poem.name);
                rf.put("pid", poem.pid);
                results.add(rf);
            }

        }
        return PoemResult.buildResult(results);
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

//        logger.info("cacheManager:" + cacheManager);
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
                poems = poemRepository.findByYuanwenContains(ciyu);
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
                poems = poemRepository.findByNameContains(name);
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

            poems = poemRepository.findPoems(dbObject);


            return PoemResult.buildResult(page, poems);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, poems);
            }
        }
    }
}
