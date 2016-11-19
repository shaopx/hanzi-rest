package com.spx.gushi.data;


import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by shaopengxiang on 2016/11/9.
 */
public class HanZi {

    @Id
    public String id;

    public String yin;
    public List<String> zi;

    public HanZi() {
    }


    public HanZi(String yin, List<String> zi) {
        this.yin = yin;
        this.zi = zi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYin() {
        return yin;
    }

    public void setYin(String yin) {
        this.yin = yin;
    }


}
