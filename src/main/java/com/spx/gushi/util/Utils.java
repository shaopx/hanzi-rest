package com.spx.gushi.util;

/**
 * Created by Administrator on 2016/11/19.
 */
public class Utils {

    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0 || str.trim().equalsIgnoreCase("NULL");
    }

    public static boolean isNotNull(String str){
        return !isNull(str);
    }
}
