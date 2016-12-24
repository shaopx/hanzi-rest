package com.spx.gushi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2016/12/24.
 */
@Document
public class PoemField {

    public String pid;
    public String content;
    public String src;
}
