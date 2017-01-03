package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SHAOPENGXIANG on 2017/1/3.
 */
public class Zuozhe {

    @Id
    public String id;

    @Field("zid")
    public String zid;

    @Field("xingming")
    public String xingming;

    @Field("jieshao")
    public String jieshao;
}
