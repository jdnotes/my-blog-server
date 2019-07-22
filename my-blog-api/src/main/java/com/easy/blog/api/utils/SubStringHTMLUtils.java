package com.easy.blog.api.utils;

/**
 * @author zhouyong
 * @date 2019/7/21
 */
public class SubStringHTMLUtils {

    public static String subStringHTML(String param, int length, String end) {
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        //是不是HTML代码
        boolean isCode = false;
        //是不是HTML特殊字符,如&nbsp;
        boolean isHTML = false;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            if (!isCode && !isHTML) {
                n = n + 1;
                //UNICODE码字符占两个字节
                if ((temp + "").getBytes().length > 1) {
                    n = n + 1;
                }
            }

            result.append(temp);
            if (n >= length) {
                break;
            }
        }
        result.append(end);
        return result.toString();
    }

    public static void main(String[] args) {
        String html = "<h2 id=\"h2\"><a name=\"为什么要分库分表？\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>为什么要分库分表？</h2><p>分表<br>比如你单表都几千万数据了，你确定你能扛住么？绝对不行，单表数据量太大，会极大影响你的 sql 执行的性能，到了后面你的 sql 可能就跑的很慢了。一般来说，就以我的经验来看，单表到几百万的时候，性能就会相对差一些了，你就得分表了。分表是啥意思？就是把一个表的数据放到多个表中，然后查询的时候你就查一个表。比如按照用户 id 来分表，将一个用户的数据就放在一个表中。然后操作的时候你对一个用户就操作那个表就好了。这样可以控制每个表的数据量在可控的范围内，比如每个表就固定在 200 万以内。 </p><p>分库<br>分库是啥意思？就是你一个库一般我们经验而言，最多支撑到并发 2000，一定要扩容了，而且一个健康的单库并发值你最好保持在每秒 1000 左右，不要太大。那么你可以将一个库的数据拆分到多个库中，访问的时候就访问一个库好了。 </p><p>总结<br>分表能够解决单表数据量过大带来的查询效率下降的问题；分库可以解决面对高并发的读写访问压力的问题，当数据库master服务器无法承载写操作压力时，不管如何扩展slave服务器，都于事无补。此时，则需要通过数据分库策略，提高数据库并发访问能力。分库分表技术优化了数据存储方式，有效减小数据库服务器的负担、缩短查询响应时间。 </p>";
        System.out.println(subStringHTML(html, 120, "..."));
    }

}