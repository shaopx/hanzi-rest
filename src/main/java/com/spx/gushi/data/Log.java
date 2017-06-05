package com.spx.gushi.data;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by SHAOPENGXIANG on 2017/6/5.
 */
@Document
public class Log {

    public String uid;
    public String log;
    public String uptime;

    public Log(String uid, String log, String uptime) {
        this.uid = uid;
        this.log = log;
        this.uptime = uptime;
    }
}
