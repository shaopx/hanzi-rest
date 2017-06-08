package com.spx.gushi.controller;

import com.spx.gushi.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by SHAOPENGXIANG on 2017/6/5.
 */

@RestController
@RequestMapping(value = "/log")
public class LogController {

    static Logger logger = Logger.getLogger("LogController");

    @Autowired
    private LogRepository logRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String uploadLog(@RequestParam(value = "uid", required = true) String uid,
                        @RequestParam(value = "log", required = true) String log,
                        @RequestParam(value = "uptime", required = true) String uptime) {
        System.out.println("/myloc  id:" + uid + ", loc:" + log+", uptime:"+uptime);

        logRepository.save(new Log(uid, log, uptime));
        return "ok";
    }


    @RequestMapping("/{uid}")
    public Object list(@PathVariable String uid, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("list  uid:" + uid +", page:"+page);
        Page<Log> logs = logRepository.findByUidOrderByUptimeDesc(uid, new PageRequest(page, 30));
        //logger.info("poemById  poem:" + logs.toString());

        return logs;
    }

//    @RequestMapping("/uids")
//    public Object uids() {
//        logger.info("uids ....");
//        List<String> logs = logRepository.findDistinctLogByUid();
//        logger.info("uids:" + logs.toString());
//
//        return logs;
//    }

}
