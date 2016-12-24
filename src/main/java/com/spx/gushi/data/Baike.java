package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2016/12/24.
 */
@Document
public class Baike {
    @Id
    public String id;

    @Field("pid")
    public String pid;

    public String beijing;
    public String sum;

    public String shangxi;
    public String shangxi1;
    public String shangxi2;


    public String zhushi;
    public String zhujie;
    public String yiwen;

    public String zuozhe;
}
