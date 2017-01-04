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

        List<PoemField> data = new ArrayList<>();

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    data = list;
                    return PoemResult.buildResult(page, data);
                }
            }

            for (Zuozhe zuozhe : zuozheList) {
                PoemField pf = new PoemField();
                pf.pid = zuozhe.zid;
                pf.content = zuozhe.jieshao;
                pf.src = "gscd";
                pf.srcDesc = GUSHICIDIAN;
                pf.type = "zuozhe";
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

        List<PoemField> data = new ArrayList<>();

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
                    PoemField pf = new PoemField();
                    pf.pid = id;
                    pf.content = poem.zhujie;
                    pf.src = "gscd";
                    pf.srcDesc = GUSHICIDIAN;
                    data.add(pf);
                }

            }

            Baike baike = baikeRepository.findByPid(id);
            if (baike != null) {
                if (Utils.isNotNull(baike.zhujie)) {
                    addBaikeData(id, "zhushi", baike.zhujie, data);
                }
                if (Utils.isNotNull(baike.zhushi)) {
                    addBaikeData(id, "zhushi", baike.zhushi, data);
                }
//                if (Utils.isNotNull(baike.yiwen)) {
//                    addBaikeData(id, baike.yiwen, data);
//                }
            }


            if (Utils.isNotNull(poem.yiwen)) {
                PoemField pf = new PoemField();
                pf.pid = id;
                pf.content = poem.yiwen;
                pf.type = "yiwen";
                pf.src = "gscd";
                pf.srcDesc = GUSHICIDIAN;
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

        List<PoemField> data = new ArrayList<>();

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
                    PoemField pf = new PoemField();
                    pf.pid = id;
                    pf.content = poem.yiwen;
                    pf.src = "gscd";
                    pf.srcDesc = GUSHICIDIAN;
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

        List<PoemField> shangxis = new ArrayList<>();

        try {
            if (cacheManager != null) {
                List list = cacheManager.get(key);
                if (list != null) {
                    shangxis = list;
                    return PoemResult.buildResult(page, shangxis);
                }
            }


            if (poem.shangxis != null && poem.shangxis.size() > 0) {
                for (Poem.Shangxi shangxi : poem.shangxis) {
                    PoemField pf = new PoemField();
                    pf.pid = id;
                    pf.content = shangxi.shangxi;
                    pf.src = shangxi.src.trim();
                    pf.type = "shangxi";
                    if ("tsjs".equals(pf.src.trim())) {
                        pf.srcDesc = TAGNSHIJIANSHANG;
                    } else if ("gscd".equals(pf.src.trim())) {
                        pf.srcDesc = GUSHICIDIAN;
                    }
                    shangxis.add(pf);
                }
            }

            Baike baike = baikeRepository.findByPid(id);
            if (baike != null) {
                if (Utils.isNotNull(baike.beijing)) {
                    addBaikeData(id, "shangxi", baike.beijing, shangxis);
                }
                if (Utils.isNotNull(baike.sum)) {
                    addBaikeData(id, "shangxi", baike.sum, shangxis);
                }
                if (Utils.isNotNull(baike.shangxi1)) {
                    addBaikeData(id, "shangxi", baike.shangxi1, shangxis);
                }
                if (Utils.isNotNull(baike.shangxi2)) {
                    addBaikeData(id, "shangxi", baike.shangxi2, shangxis);
                }
            }

            return PoemResult.buildResult(shangxis);
        } finally {
            if (cacheManager != null) {
                cacheManager.put(key, shangxis);
            }
        }
    }

    private void addBaikeData(String pid, String type, String text, List<PoemField> shangxis) {
        PoemField sx = new PoemField();
        sx.pid = pid;
        sx.content = text;
        sx.type = type;
        sx.src = "baidu_baike";
        sx.srcDesc = BAIDUBAIKE;
        shangxis.add(sx);
    }

    @RequestMapping("/poems/random/{num}")
    public PoemResult randomPoems(@PathVariable String num) {
        logger.info("randomPoems  num:" + num);

        int number = Integer.parseInt(num);
        List<Poem> poems = new ArrayList<>();
        Random ra = new Random();

        {
            int index = 13952;
            Poem poem = poemRepository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                Poem p = new Poem();
                p.yuanwen = poem.yuanwen;
                p.zuozhe = poem.zuozhe;
                p.chaodai = poem.chaodai;
                p.name = poem.name;
                p.id = poem.id;
                p.pid = poem.pid;
                poems.add(p);
            }
        }

        {
            int index = 9542;
            Poem poem = poemRepository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                Poem p = new Poem();
                p.yuanwen = poem.yuanwen;
                p.zuozhe = poem.zuozhe;
                p.chaodai = poem.chaodai;
                p.name = poem.name;
                p.id = poem.id;
                p.pid = poem.pid;
                poems.add(p);
            }
        }


        while (poems.size() < number) {
            int index = ra.nextInt(POEM_MAX);
            Poem poem = poemRepository.findByPid("" + index);
            if (poem != null && !poem.isEmpty()) {
                Poem p = new Poem();
                p.yuanwen = poem.yuanwen;
                p.zuozhe = poem.zuozhe;
                p.chaodai = poem.chaodai;
                p.name = poem.name;
                p.id = poem.id;
                p.pid = poem.pid;
                poems.add(p);
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
