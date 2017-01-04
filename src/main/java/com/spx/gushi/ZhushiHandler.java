package com.spx.gushi;

import com.spx.gushi.data.Result;
import com.spx.gushi.util.Utils;
import org.apache.commons.collections.list.TreeList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SHAOPENGXIANG on 2017/1/4.
 */
public class ZhushiHandler {

    public static void makeZhushiResult(String yuanwen, String zhushiStr, List<Result> paramResultList) {

        List<Result> resultList = new ArrayList<>();

        String tag = "⑴⑵⑶⑻";
//        for(int i=0;i<tag.length();i++){
//            char ch = tag.charAt(i);
//            System.out.println(""+Integer.toHexString(ch));
//        }

        tag = "\r\n";
        for (char ch = '\u2474'; ch < '\u24ff'; ch++) {
            tag += ch;
        }
        System.out.println("tag:" + tag);
//        System.out.println("247f:"+"\u247f");
//        System.out.println("2480:"+"\u2480");
//        System.out.println("2481:"+"\u2481");
//        System.out.println("2482:"+"\u2482");
//        System.out.println("2483:"+"\u2483");
//        System.out.println("2484:"+"\u2484");
//        System.out.println("2485:"+"\u2485");
//        System.out.println("2486:"+"\u2486");
//        System.out.println("2487:"+"\u2487");
//        System.out.println("2488:"+"\u2488");
//        System.out.println("2489:"+"\u2489");
//        System.out.println("24ff:"+"\u24ff");


//        String delim = "\r\n";
        List<String> lines = Utils.split(zhushiStr, tag);
        System.out.println(lines.size());

        String zhushi = "";
        for (String line : lines) {
            System.out.println(line);
            String ciyu = "";
            while (line.length() > 0) {
                String part = getNextPart(line);
                line = line.substring(part.length());

                ciyu = getCiyu(yuanwen, part);
                System.out.println(part + "[ciyu:" + ciyu + "]");
                if (Utils.isNotNull(ciyu)) {
                    Result r = new Result();
                    String jieshi = getJieshi(part, ciyu);
                    System.out.println(part + "[jieshi:" + jieshi + "]");
                    if (ciyu.contains("(") && ciyu.contains(")")) {
                        int start = ciyu.indexOf("(");
                        int end = ciyu.indexOf(")");
                        if (end > start && start > 0) {
                            String beizhu = ciyu.substring(start + 1, end);
                            r.put("b", beizhu);
                            r.put("rawc", ciyu);
                            ciyu = ciyu.substring(0, start) + ciyu.substring(end + 1);
                        }
                    } else if (ciyu.contains("（") && ciyu.contains("）")) {
                        int start = ciyu.indexOf("（");
                        int end = ciyu.indexOf("）");
                        if (end > start && start > 0) {
                            String beizhu = ciyu.substring(start + 1, end);
                            r.put("b", beizhu);
                            r.put("rawc", ciyu);
                            ciyu = ciyu.substring(0, start) + ciyu.substring(end + 1);
                        }
                    }
                    r.put("ciyu", ciyu);

                    r.put("jieshi", jieshi);
                    //r.put(ciyu, jieshi);
                    //resultList.add(r);
                    addToList(yuanwen, r, resultList);
                } else {
                    if (resultList.size() > 0) {
                        Result result = resultList.get(resultList.size() - 1);
                        String lastciyu = (String) result.get("ciyu");
                        String lastjieshi = (String) result.get("jieshi");
                        result.put("jieshi", lastjieshi + part);
                    } else {
                        Result r = new Result();
                        r.put("ciyu", "");
                        r.put("jieshi", part);
//                        resultList.add(r);
                        addToList(yuanwen, r, resultList);
                    }


                }
            }

//            int index = 0;
//            while(index<line.length()){
//
//            }
        }


        mergeResultList(yuanwen, paramResultList, resultList);
    }

    private static void mergeResultList(String yuanwen,List<Result> paramResultList, List<Result> resultList) {
        for (Result result : resultList) {
            String ciyu = (String) result.get("ciyu");
            if (Utils.isNull(ciyu)) {
                continue;
            }
            boolean found = false;
            for (Result pr : paramResultList) {
                if (ciyu.equals(pr.get("ciyu"))) {
                    found = true;
                    String jieshi_new = (String) result.get("jieshi");
                    String jieshi_old = (String) pr.get("jieshi");
                    if (Utils.isNotNull(jieshi_new) && Utils.isNotNull(jieshi_old)) {
                        double sim = Utils.sim(jieshi_new, jieshi_old);
//                        System.out.println("jieshi_new:"+jieshi_new);
//                        System.out.println("jieshi_old:"+jieshi_old);
//                        System.out.println("sim:"+sim);
                        if (sim < 0.5) {
                            String jieshi = jieshi_old + jieshi_new;
                            pr.put("jieshi", jieshi);
                        }
                    } else if (Utils.isNull(jieshi_old)) {
                        pr.put("jieshi", jieshi_new);
                    }

                    break;
                }
            }

            if (!found) {
                addToList(yuanwen, result, paramResultList);
            }
        }

        paramResultList.sort(new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                String ln= (String) o1.get("l");
                String rn= (String) o2.get("l");
                return  Integer.parseInt(ln)-Integer.parseInt(rn);
            }
        });
    }


    private static void addToList(String yuanwen, Result r, List<Result> resultList) {

        String num = (String) r.get("l");
        if (Utils.isNull(num)) {
            String cy = (String) r.get("ciyu");
            int inde = yuanwen.indexOf(cy);
            r.put("l", "" + inde);
        }

        resultList.add(r);
    }

    private static String getJieshi(String part, String ciyu) {
        return part.substring(ciyu.length() + 1);
    }

    private static String getCiyu(String yuanwen, String part) {
        if (Utils.isNull(part)) return null;
        int index = part.indexOf("：");
        if (index == -1) return null;
        if (index > 0) {
            String ciyu = part.substring(0, index);
            if (yuanwen.contains(ciyu)) {
                return ciyu;
            } else {
                String cleanciyu = getCleanCiyu(ciyu);
                //System.out.println("cleanciyu:"+cleanciyu);
                if (yuanwen.contains(cleanciyu)) {
                    return ciyu;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private static String getCleanCiyu(String ciyu){
        if (ciyu.contains("(") && ciyu.contains(")")) {
            int start = ciyu.indexOf("(");
            int end = ciyu.indexOf(")");
            if (end > start && start > 0) {
                ciyu = ciyu.substring(0, start) + ciyu.substring(end + 1);
            }
        }


        if (ciyu.contains("（") && ciyu.contains("）")) {
            int start = ciyu.indexOf("（");
            int end = ciyu.indexOf("）");
            if (end > start && start > 0) {
                ciyu = ciyu.substring(0, start) + ciyu.substring(end + 1);
            }
        }

        return ciyu;
    }

    private static String getNextPart(String line) {
        if (Utils.isNull(line)) return "";
        int index = line.indexOf("。");
        if (index == -1) return line;
        if (index > 0) {
            return line.substring(0, index + 1);
        }
        return line;
    }


//    public static void main(String[] ars) {
//        String str1 = "⑴题一作《九日登高》。古代农历九月九日有登高习俗。选自《杜诗详注》。作于唐代宗大历二年（767）秋天的重阳节。\n⑵啸哀：指猿的叫声凄厉。\n⑶渚(zhǔ)：水中的小洲；水中的小块陆地。鸟飞回：鸟在急风中飞舞盘旋。回：回旋。\n⑷落木：指秋天飘落的树叶。萧萧：模拟草木飘落的声音。\n⑸万里：指远离故乡。常作客：长期漂泊他乡。\n⑹百年：犹言一生，这里借指晚年。\n⑺艰难：兼指国运和自身命运。苦恨：极恨，极其遗憾。苦，极。繁霜鬓：增多了白发，如鬓边着霜雪。 繁，这里作动词，增多。\n⑻潦倒：衰颓，失意。这里指衰老多病，志不得伸。新停：刚刚停止。杜甫晚年因病戒酒，所以说“新停”。";
//        String str = "⑴登高：农历九月九日为重阳节，历来有登高的习俗。\n⑵猿啸哀：指长江三峡中猿猴凄厉的叫声。《水经注·江水》引民谣云：“巴东三峡巫峡长，猿鸣三声泪沾裳。”\n⑶渚（zhǔ）：水中的小洲；水中的小块陆地。鸟飞回：鸟在急风中飞舞盘旋。回：回旋。\n⑷落木：指秋天飘落的树叶。萧萧：风吹落叶的声音。\n⑸万里：指远离故乡。常作客：长期漂泊他乡。\n⑹百年：犹言一生，这里借指晚年。\n⑺艰难：兼指国运和自身命运。苦恨：极恨，极其遗憾。苦，极。繁霜鬓：增多了白发，如鬓边着霜雪。 繁，这里作动词，增多。\n⑻潦倒：衰颓，失意。这里指衰老多病，志不得伸。新停：新近停止。重阳登高，例应喝酒。杜甫晚年因肺病戒酒，所以说“新停”。[2] [3-4] [5] \n";
//        List<Result> result = new ArrayList<>();
//
//        ZhushiHandler.makeZhushiResult("风急天高猿啸哀，\\n渚清沙白鸟飞回。\\n无边落木萧萧下，\\n不尽长江滚滚来。\\n万里悲秋常作客，\\n百年多病独登台。\\n艰难苦恨繁霜鬓，\\n潦倒新停浊酒杯。\\n",
//                str1, result);
//
//        ZhushiHandler.makeZhushiResult("风急天高猿啸哀，\\n渚清沙白鸟飞回。\\n无边落木萧萧下，\\n不尽长江滚滚来。\\n万里悲秋常作客，\\n百年多病独登台。\\n艰难苦恨繁霜鬓，\\n潦倒新停浊酒杯。\\n",
//                str, result);
//
//
//
//        for (Result result1 : result) {
//            System.out.println(result1.toString());
//        }
//
//    }
}
