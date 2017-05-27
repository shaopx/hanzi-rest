package com.spx.gushi.data;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2017/5/28.
 */
@Document
public class Myloc {
    String uid;
    String loc;
    String uptime;

    public Myloc(String uid, String loc, String uptime) {
        this.uid = uid;
        this.loc = loc;
        this.uptime = uptime;
    }
}
