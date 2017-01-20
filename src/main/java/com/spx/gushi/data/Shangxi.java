package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SHAOPENGXIANG on 2017/1/18.
 */
public class Shangxi {

    @Id
    public String id;

    @Field("pid")
    public String pid;

    @Field("yuanwen")
    public String yuanwen;

    @Field("author")
    public String zuozhe;

    @Field("name")
    public String name;

    public String src;
    public String srcDesc;
    public String shangxi;
}
