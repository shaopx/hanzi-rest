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

    @Field("reason")
    public String reason;

    @Field("me")
    public String me;

    @Field("pid")
    public String pid;

    @Field("puid")
    public String puid;

    public Myloc(){

    }

    public Myloc(String uid, String loc, String uptime, String reason, String me, String pid, String puid) {
        this.uid = uid;
        this.loc = loc;
        this.uptime = uptime;
        this.reason = reason;
        this.me = me;
        this.pid = pid;
        this.puid = puid;
    }
}
