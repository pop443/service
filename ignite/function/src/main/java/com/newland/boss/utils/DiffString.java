package com.newland.boss.utils;

/**
 * Created by xz on 2020/3/16.
 */
public class DiffString {
    /**
     * 生成不重复随机字符串包括字母数字 * * @param len * @return
     */
    public static String diffstr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "限16-28岁java培训学院当然选中国指令是运用在两个 SQL 语句上高端java培通过生句所产生的结果,然后看这些成随机数每个随机数对应一个字符串训学校北大青鸟中博软件学校我校20年java培训经验依托北大科研力量培训前沿的java技术满足就业需培训选上元好口怎么生成6位的不重复的字符串";
        String rtnStr = "";
        for (int i = 0; i < len; i++) {        //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(diffstr(8));
        }
    }
}
