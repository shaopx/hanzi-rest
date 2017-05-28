package com.spx.gushi.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */
public class DataResult {
    private static final int COUNT_PER_PAGE = 10;

    public int retcode;
    public String errorMsg;
    public int total;
    public int pages;
    public int pageNum;
    public List data = new ArrayList<>();

    public DataResult() {
    }


    public static DataResult buildResult(List datalist) {
        return buildResult(-1, datalist);
    }

    public static DataResult buildResult(int pageNum, List datalist) {
        DataResult pr = new DataResult();
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
