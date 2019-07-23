package com.easy.blog.api.constant;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class GlobalConstant {

    /**
     * 文章类型
     */
    public static final String ARTICLE_TYPE_TIDY = "整理";

    /**
     * 文章类型
     */
    public static final String ARTICLE_TYPE_ORIGINAL = "原创";

    /**
     * 文章类型
     */
    public static final String ARTICLE_TYPE_REPRINTED = "转载";

    /**
     * 文章作者
     */
    public static final String ARTICLE_AUTHOR = "一花一世界";

    /**
     * 用户(默认)
     */
    public static final String ARTICLE_USERNAME = "zhouyong";

    /**
     * 口令(默认)
     */
    public static final String ARTICLE_PASSWORD = "ts842771506";

    /**
     * 请求超时时间(90s)
     */
    public static final long REPUEST_EXPIRE_TIME = 90000L;

    /**
     * 重复请求时间
     */
    public static final int SECOND_EXPIRE_TIME = 60;

    /**
     * 对称加密key( MD5(Gk4Wmql9yk) )
     */
    public final static String SYMMETRIC_ENCRYPTION_KEY = "38ec4167d7fa907447c8f2217ed3b0e2";
}