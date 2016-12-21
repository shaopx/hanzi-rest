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
    public List poems = new ArrayList<>();

    public PoemResult() {
    }


    public static PoemResult buildResult(List<Poem> poems) {
        return buildResult(-1, poems);
    }

    public static PoemResult buildResult(int pageNum, List poems) {
        PoemResult pr = new PoemResult();
        pr.retcode = 0;
        pr.errorMsg = "";
        pr.total = poems.size();
        pr.pageNum = pageNum;

        //-1说明需要一次性全部返回
        if (pageNum == -1) {
            pr.pages = 1;
            pr.poems.addAll(poems);
        } else {
            if (poems.size() == 0) {
                pr.pages = 1;
            } else {
                pr.pages = (poems.size() - 1) / COUNT_PER_PAGE + 1;
            }

            int startIndex = pageNum * COUNT_PER_PAGE;
            int endIndex = (pageNum + 1) * COUNT_PER_PAGE;
            for (int i = startIndex; i < poems.size() && i < endIndex; i++) {
                pr.poems.add(poems.get(i));
            }
        }


        return pr;
    }
}
