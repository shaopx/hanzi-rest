package com.spx.gushi.controller;

import com.spx.gushi.data.*;
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
    private ShiyiRepository shiyiRepository;

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

    @RequestMapping("/postword")
    public String postword(@RequestParam(value = "word", defaultValue = "wwww") String word) {
        logger.info("postword  word:" + word);
//        List<HanZi> all = repository.findAll();
//
//        for (HanZi hanZi : all) {
//
//        }


        return "postword Finished!";
    }

    @RequestMapping("/hanzi")
    public HanZi hanzi(@RequestParam(value = "yin", defaultValue = "a") String yin) {
        HanZi byYin = repository.findByYin(yin);
        if (byYin == null) {
            return new HanZi("No", null);
        }
        return byYin;
    }

    @RequestMapping("/zi")
    public Shiyi zi(@RequestParam(value = "word", defaultValue = "美") String word) {
        Shiyi shiyi = shiyiRepository.findByWord(word);
        if (shiyi == null) {
            return new Shiyi("", "" , "");
        }
        return shiyi;
    }

    @RequestMapping("/pinyin")
    public List<Shiyi> pinyin(@RequestParam(value = "pinyin", defaultValue = "ha") String pinyin) {
        List<Shiyi> shiyiList = shiyiRepository.findByYin(pinyin);
        if (shiyiList == null) {
            shiyiList = new ArrayList<>();
        }
        return shiyiList;
    }

//    @RequestMapping("/")
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }

    @RequestMapping("/poet")
    public Poet poet() {
        return null;
    }

}
