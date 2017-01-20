package com.spx.gushi.data;

import com.spx.gushi.util.Utils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
@Document
public class Poem {
    @Id
    public String id;

    @Field("pid")
    public String pid;

    @Field("mingcheng")
    public String name;

    public String zuozhe;
    public String shipin;
    public String ticai;
    public String chaodai;
    public String guojia;
    public String fenlei;
    public String jieduan;
    public String keben;
    public String congshu;
    public String chuchu;
    public String zhaiyao;
    public String yuanwen;
    public String zhujie;
    public String yiwen;

    public boolean isEmpty() {
        return Utils.isNull(yuanwen);
    }

    public Poem() {
    }

}
