package com.spx.gushi.controller;

import com.spx.gushi.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/5/28.
 */

@RestController
@RequestMapping(value = "/loc")
public class LocationController {
    static Logger logger = Logger.getLogger("LocationController");
    @Autowired
    private MylocRepository mylocRepository;

    @RequestMapping(value = "/myloc", method = RequestMethod.POST)
    public String myloc(@RequestParam(value = "uid", required = true) String uid,
                        @RequestParam(value = "loc", required = true) String loc,
                        @RequestParam(value = "uptime", required = true) String uptime,
                        @RequestParam(value = "reason", required = true) String reason,
                        @RequestParam(value = "me", required = true) String me,
                        @RequestParam(value = "pid", required = true) String pid,
                        @RequestParam(value = "puid", required = true) String puid) {
        System.out.println("/myloc  id:" + uid + ", loc:" + loc + ", uptime:" + uptime);

        mylocRepository.save(new Myloc(uid, loc, uptime, reason, me, pid, puid));
        return "ok";
    }

    @RequestMapping("/{uid}")
    public DataResult where(@PathVariable String uid, @RequestParam(value = "before", defaultValue = "0") long before) {
        logger.info("where  uid:" + uid + ", before:" + before);
        List<Myloc> mylocs = mylocRepository.findTop40ByUidOrderByUptimeDesc(uid);
        logger.info("where  uid:" + uid + ", mylocs:" + mylocs.size());

        List<Result> data = new ArrayList<>();

        try {

            return DataResult.buildResult(mylocs);
        } finally {
        }
//        return mylocs;
    }

    @RequestMapping("/p/{uid}")
    public Page<Myloc> pageWhere(@PathVariable String uid, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("p where  uid:" + uid + ", page:" + page);
        Page<Myloc> pageData = mylocRepository.findByUidOrderByUptimeDesc(uid, new PageRequest(page, 10));
        logger.info("p where  uid:" + uid + ", mylocs:" + pageData.getTotalElements());

       return pageData;
    }
}
