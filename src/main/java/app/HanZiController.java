package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class HanZiController {

    static Logger logger = Logger.getLogger("HanZiController");

    @Autowired
    private HanZiRepository repository;

    @Autowired
    private TestcollectionsRepository testDataRepository;

//    @RequestMapping("/upload")
//    public List<HanZi> upload() {
//        logger.info("on start up running..... ");
//
//        repository.deleteAll();
//
//        List<Testcollections> all = testDataRepository.findAll();
//
//        List<HanZi> hanzis = new ArrayList<>();
//
//        for (Testcollections testcollections : all) {
//            repository.save(new HanZi(testcollections.key, testcollections.words));
//            //sb.append(testcollections.key).append(":").append(testcollections.words).append("\r\n");
//            //hanzis.add(new HanZi(testcollections.key, testcollections.words));
//        }
//
//        return hanzis;
//    }

//    @RequestMapping("/test")
//    public List<HanZi> test() {
//
//        List<HanZi> all = repository.findAll();
//        logger.info("all.size:" + all.size());
//        StringBuilder sb = new StringBuilder("all data size:" + all.size() + "\r\n");
//
//        List<HanZi> hanzis = new ArrayList<>();
//
//        for (HanZi hanZi : all) {
//            //repository.save(new HanZi(testcollections.key, testcollections.words));
//            //sb.append(testcollections.key).append(":").append(testcollections.words).append("\r\n");
//            hanzis.add(new HanZi(hanZi.yin, hanZi.zi));
//        }
//
//        return hanzis;
//    }

    @RequestMapping("/hanzi")
    public HanZi hanzi(@RequestParam(value = "yin", defaultValue = "a") String yin) {
        HanZi byYin = repository.findByYin(yin);
        if (byYin == null) {
            return new HanZi("No", null);
        }
        return byYin;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
