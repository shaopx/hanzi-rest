package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/24.
 */
@Document
public class ResultFiled {

    //    public String pid;
//    public String content;
    public String s;
    public String sd;
    public String t;


    public Map<String, String> d = new HashMap<>();

    public void put(String key, String value) {
        d.put(key, value);
    }

    public static ResultFiled build() {
        return new ResultFiled();
    }

    public static ResultFiled build(String type, String ss) {
        return build().src(ss).type(type);
    }

    public ResultFiled src(String src) {
        this.s = src;
        if (src.equals("tsjs")) {
            sd = "<<唐诗鉴赏>>";
        } else if (src.equals("baidu_baike")) {
            sd = "'百度百科'";
        } else if (src.equals("gscd")) {
            sd = "<<古诗词典>>";
        } else {
            sd = "";
        }
        return this;
    }

    public ResultFiled type(String type) {
        this.t = type;
        return this;
    }


}
