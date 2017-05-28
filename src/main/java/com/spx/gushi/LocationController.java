package com.spx.gushi;

import com.spx.gushi.data.*;
import org.springframework.beans.factory.annotation.Autowired;
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
                        @RequestParam(value = "uptime", required = true) String uptime) {
        System.out.println("/myloc  id:" + uid + ", loc:" + loc + ", uptime:" + uptime);

        mylocRepository.save(new Myloc(uid, loc, uptime));
        return "ok";
    }

    @RequestMapping("/{uid}")
    public DataResult where(@PathVariable String uid, @RequestParam(value = "page", defaultValue = "0") int page) {
        logger.info("where  uid:" + uid + ", page:" + page);
        List<Myloc> mylocs = mylocRepository.findTop20ByUidOrderByUptimeDesc(uid);
        logger.info("where  uid:" + uid + ", mylocs:" + mylocs.size());

        List<Result> data = new ArrayList<>();

        try {

            return DataResult.buildResult(mylocs);
        } finally {
        }
//        return mylocs;
    }
}
