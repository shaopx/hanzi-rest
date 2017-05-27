package com.spx.gushi;

import com.spx.gushi.data.Myloc;
import com.spx.gushi.data.MylocRepository;
import com.spx.gushi.data.Poem;
import com.spx.gushi.data.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/5/28.
 */

@RestController
@RequestMapping(value = "/loc")
public class LocationController {

    @Autowired
    private MylocRepository mylocRepository;

    @RequestMapping(value = "/myloc", method = RequestMethod.POST)
    public String myloc(@RequestParam(value = "uid", required = true) String uid,
                        @RequestParam(value = "loc", required = true) String loc,
                        @RequestParam(value = "uptime", required = true) String uptime) {
        System.out.println("/myloc  id:" + uid + ", loc:" + loc+", uptime:"+uptime);

        mylocRepository.save(new Myloc(uid, loc, uptime));
        return "ok";
    }
}
