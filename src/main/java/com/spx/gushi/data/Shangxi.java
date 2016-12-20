package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SHAOPENGXIANG on 2016/11/23.
 */
@Document
public class Shangxi {

    @Id
    public String id;

    public String pid;
    public String sid;
    public String shangxi;
    public String src;
}
