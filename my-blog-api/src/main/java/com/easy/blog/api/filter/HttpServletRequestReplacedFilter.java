package com.easy.blog.api.filter;

import com.alibaba.fastjson.JSON;
import com.easy.blog.api.utils.Md5Utils;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import com.easy.blog.cache.service.CacheService;
import com.easy.blog.mq.config.MQConstant;
import com.easy.blog.mq.service.MqService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author zhouyong
 * @DATE 2019/6/16
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "httpServletRequestReplacedFilter")
public class HttpServletRequestReplacedFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(HttpServletRequestReplacedFilter.class);

    @Autowired
    private MqService mqService;

    @Autowired
    private CacheService cacheService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        ServletRequest requestWrapper = null;
        long startTime = System.currentTimeMillis();
        String bodyJson = "";
        if (!(request instanceof HttpServletRequest)) {
            logger.info("request is not HttpServletRequest type");
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Map<String, String> headers = WrapperUtils.getHeaderParams(httpServletRequest);
        String timestamp = headers.get("timestamp");
        String nonce = headers.get("nonce");
        String sign = headers.get("sign");
        String version = headers.get("version");

        String url = WrapperUtils.getRequestURI(httpServletRequest);
        String ip = WrapperUtils.getRemoteAddr(httpServletRequest);
        String method = WrapperUtils.getMethod(httpServletRequest);
        String token = WrapperUtils.getHeader(httpServletRequest, "token");
        String contentType = WrapperUtils.getHeader(httpServletRequest, "Content-Type");
        logger.info("url:{},ip:{},method:{},token:{},contentType:{},timestamp:{},nonce:{},sign:{},version:{}", url, ip, method, token, contentType, timestamp, nonce, sign, version);

//        if (StringUtils.isNotEmpty(contentType) && contentType.indexOf("multipart/form-data;") >= 0) {
//            //附件上传不进行过滤
//            logger.info("contentType is  application/x-www-form-urlencoded");
//            this.sendMsg(url, method, headers, bodyJson, token, startTime, ip);
//            if (requestWrapper == null) {
//                chain.doFilter(request, response);
//            } else {
//                chain.doFilter(requestWrapper, response);
//            }
//            return;
//        }

        requestWrapper = new MAPIHttpServletRequestWrapper(httpServletRequest);
        try {
            bodyJson = WrapperUtils.getBodyParams(requestWrapper);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }

//        //请求头参数非空验证
//        if (StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(sign)) {
//            response.getWriter().print(JSON.toJSONString(Result.error(CodeMsgConstant.SESSION_ERROR)));
//            return;
//        }
//
//        //请求是否超时
//        if (System.currentTimeMillis() - NumberUtils.toLong(timestamp) > GlobalConstant.REPUEST_EXPIRE_TIME) {
//            response.getWriter().print(JSON.toJSONString(Result.error(CodeMsgConstant.SESSION_ERROR)));
//            return;
//        }
//
//        //签名验证
//        String serverSign = SignUtil.getSign(timestamp, nonce, bodyJson, GlobalConstant.SYMMETRIC_ENCRYPTION_KEY);
//        if (!serverSign.equals(sign)) {
//            response.getWriter().print(JSON.toJSONString(Result.error(CodeMsgConstant.SESSION_ERROR)));
//            return;
//        }
//
//        //nonce重复请求
//        String nonceMd5 = this.getNonceStr(url, token, timestamp, nonce, sign);
//        String nonceKey = RedisConstant.BLOG_INTERCEPT_NONCE + nonceMd5;
//        if (StringUtils.isNotEmpty(nonceKey)) {
//            if (!cacheService.setnx(nonceKey, "1", GlobalConstant.SECOND_EXPIRE_TIME)) {
//                response.getWriter().print(JSON.toJSONString(Result.error(CodeMsgConstant.SESSION_ERROR)));
//                return;
//            }
//        }
//
//        //url表单重复提交
//        String demandUrl = "";
//        if (StringUtils.isNotEmpty(url) && demandUrl.equals(url) && StringUtils.isNotEmpty(bodyJson)) {
//            String repeatKey = WrapperUtils.getRepeatUrlByBody(token, url, requestWrapper);
//            if (!cacheService.setnx(repeatKey, "1", GlobalConstant.SECOND_EXPIRE_TIME)) {
//                //重复提交
//                response.getWriter().print(JSON.toJSONString(Result.error(CodeMsgConstant.SESSION_ERROR)));
//                return;
//            }
//        }

        this.sendMsg(url, method, headers, bodyJson, token, startTime, ip);

        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    private void sendMsg(String url, String method, Map<String, String> headers,
                         String body, String token, long startTime, String ip) {
        Map<String, Object> map = new HashMap<>(10);
        map.put("messageId", SnowflakeIdUtils.getSnowflakeId());
        map.put("url", url);
        map.put("method", method);
        map.put("parameter", body);
        map.put("token", token);
        map.put("messageDate", new Date());
        map.put("status", 200);
        map.put("responseTime", System.currentTimeMillis() - startTime);
        map.put("ip", ip);
        map.put("headers", JSON.toJSONString(headers));
        String temp = JSON.toJSONString(map);
        logger.info("请求地址参数: {}", temp);
        mqService.sendMessage(MQConstant.BlogMqConstant.BLOG_EXCHANGE, MQConstant.BlogMqConstant.LOG_ROUTE_NAME, temp);
    }

    private String getNonceStr(String url, String token, String timestamp, String nonce, String sign) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(url)) {
            sb.append(url);
        }
        if (StringUtils.isNotEmpty(token)) {
            sb.append(token);
        }
        if (StringUtils.isNotEmpty(timestamp)) {
            sb.append(timestamp);
        }
        if (StringUtils.isNotEmpty(nonce)) {
            sb.append(nonce);
        }
        if (StringUtils.isNotEmpty(sign)) {
            sb.append(sign);
        }
        if (sb.length() > 0) {
            return Md5Utils.md5(sb.toString());
        }
        return sb.toString();
    }

    private Map<String, Object> getFormParams(HttpServletRequest httpServletRequest) {
        Map<String, Object> formParams = new TreeMap<>();
        Enumeration parameterNames = httpServletRequest.getParameterNames();
        if (parameterNames != null) {
            //form请求参数
            while (parameterNames.hasMoreElements()) {
                String formName = (String) parameterNames.nextElement();
                formParams.put(formName, httpServletRequest.getParameter(formName));
            }
        }
        return formParams;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
