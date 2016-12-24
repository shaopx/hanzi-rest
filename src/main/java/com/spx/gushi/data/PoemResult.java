package com.spx.gushi.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHAOPENGXIANG on 2016/12/21.
 */
@Document
public class PoemResult {

    private static final int COUNT_PER_PAGE = 10;

    public int retcode;
    public String errorMsg;
    public int total;
    public int pages;
    public int pageNum;
    public List data = new ArrayList<>();

    public PoemResult() {
    }


    public static PoemResult buildResult(List datalist) {
        return buildResult(-1, datalist);
    }

    public static PoemResult buildResult(int pageNum, List datalist) {
        PoemResult pr = new PoemResult();
        pr.retcode = 0;
        pr.errorMsg = "";
        pr.total = datalist.size();
        pr.pageNum = pageNum;

        //-1说明需要一次性全部返回
        if (pageNum == -1) {
            pr.pages = 1;
            pr.data.addAll(datalist);
        } else {
            if (datalist.size() == 0) {
                pr.pages = 1;
            } else {
                pr.pages = (datalist.size() - 1) / COUNT_PER_PAGE + 1;
            }

            int startIndex = pageNum * COUNT_PER_PAGE;
            int endIndex = (pageNum + 1) * COUNT_PER_PAGE;
            for (int i = startIndex; i < datalist.size() && i < endIndex; i++) {
                pr.data.add(datalist.get(i));
            }
        }


        return pr;
    }
}
