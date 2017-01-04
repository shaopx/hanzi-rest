package com.spx.gushi.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/24.
 */
@Document
public class Result extends LinkedHashMap{

    //    public String pid;
//    public String content;
//    public String s;
//    public String sd;
//    public String t;


//    public Map<String, String> d = new HashMap<>();

//    public void put(String key, String value) {
//        d.put(key, value);
//    }

    public static Result build() {
        return new Result();
    }

    public static Result build(String type, String ss) {
        return build().src(ss).type(type);
    }

    public Result src(String src) {
//        this.s = src;
        put("s", src);
        String sd = "";
        if (src.equals("tsjs")) {
            sd = "<<唐诗鉴赏>>";
        } else if (src.equals("baidu_baike")) {
            sd = "'百度百科'";
        } else if (src.equals("gscd")) {
            sd = "<<古诗词典>>";
        } else {
            sd = "";
        }
        put("sd", sd);
        return this;
    }

    public Result type(String type) {
        put("t", type);
        return this;
    }


}
