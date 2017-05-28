package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2017/5/28.
 */
@Document
public class Myloc {

    @Id
    public String id;

    @Field("uid")
    public String uid;

    @Field("loc")
    public String loc;

    @Field("uptime")
    public String uptime;

    public Myloc(){

    }

    public Myloc(String uid, String loc, String uptime) {
        this.uid = uid;
        this.loc = loc;
        this.uptime = uptime;
    }
}
