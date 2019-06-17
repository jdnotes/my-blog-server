package com.easy.blog.api.filter;

import com.alibaba.fastjson.JSON;
import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author zhouyong
 * @DATE 2019/6/16
 */
public class WrapperUtils {

    private static Logger logger = LoggerFactory.getLogger(WrapperUtils.class);

    public static String getRequestURI(HttpServletRequest request) {
        String url = request.getRequestURI();
        return url;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    public static String getMethod(HttpServletRequest request) {
        String method = request.getMethod();
        return method;
    }

    /**
     * 获取Header值
     *
     * @param request
     * @param headerName
     * @return
     */
    public static String getHeader(HttpServletRequest request, String headerName) {
        String value = request.getHeader(headerName);
        return value;
    }

    public static String getBodyParams(ServletRequest requestWrapper) {
        if (requestWrapper == null) {
            return "";
        }
        StringBuilder body = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(requestWrapper.getInputStream()))) {
            //body请求参数
            char[] charBuffer = new char[1024];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                body.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            logger.error("Fail to read body input stream", e);
        }
        return body.toString();
    }

    public static Map<String, String> getHeaderParams(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String headerName = enumeration.nextElement();
                String headerValue = request.getHeader(headerName);
                if (StringUtils.isNotEmpty(headerName) && StringUtils.isNotEmpty(headerValue)) {
                    headers.put(headerName, headerValue);
                }
            }
        }
        return headers;
    }

    public static String getRepeatUrlByBody(String token, String url, ServletRequest requestWrapper) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        if (requestWrapper == null) {
            return "";
        }
        String repeatKey = "";
        String bodyJson = getBodyParams(requestWrapper);
        TreeMap<String, Object> treeMap = JSON.parseObject(bodyJson, TreeMap.class);
        if (treeMap != null) {
            if (StringUtils.isNotEmpty(token)) {
                treeMap.put("token", token);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(url).append("?");
            for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                sb.append(entry.getKey()).append("=")
                        .append(entry.getValue() == null ? "" : entry.getValue().toString()).append("&");
            }
            if (sb.length() > 0) {
                String md5 = Md5Utils.md5(sb.toString());
                //repeatKey = RedisConstant.INTERCEPT_REPEAT + md5;
            }
        }
        return repeatKey;
    }

    /**
     * 去除特殊字符
     *
     * @param str
     * @return
     */
    public static String removeSpecialChar(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        Pattern p = compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        str = m.replaceAll("");
        return str;
    }
}
