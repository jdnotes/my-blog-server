package com.easy.blog.api.filter;

import com.alibaba.fastjson.JSON;
import com.easy.blog.api.constant.CodeMsgConstant;
import com.easy.blog.api.constant.Result;
import com.easy.blog.api.utils.IPUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 限流 切面枚举实现
 */
@Component
@Scope
@Aspect
public class RateLimitAspect {

    private Logger logger = LoggerFactory.getLogger(HttpServletRequestReplacedFilter.class);

    /**
     * 用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
     */
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    private static final String POINT = "execution (* com.easy.blog.api.controller..*.*(..))";

    private RateLimiter rateLimiter;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Pointcut(POINT)
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;

        //1.ip限制
        String ipAddr = IPUtils.getIpAddr(request);
        RateLimiter limiter = ipRequestCaches.get(ipAddr);
        if (!limiter.tryAcquire()) {
            // 未获得令牌（限制访问）
            obj = Result.error(CodeMsgConstant.SERVER_LIMIT_ERROR);
            logger.info("ip拦截拒绝了请求:" + JSON.toJSONString(obj));
            return obj;
        }

        //2.请求url限制
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        //获取注解每秒加入桶中的token
        int limitNum = annotation.limitNum();
        // 注解所在方法名区分不同的限流策略
        String functionName = msig.getName();

        //获取rateLimiter
        if (map.containsKey(functionName)) {
            rateLimiter = map.get(functionName);
        } else {
            map.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = map.get(functionName);
        }
        try {
            if (rateLimiter.tryAcquire()) {
                //执行方法
                obj = joinPoint.proceed();
            } else {
                Result result = Result.error(CodeMsgConstant.SERVER_LIMIT_ERROR);
                //拒绝了请求（服务降级）
                String resultJson = JSON.toJSONString(result);
                logger.info("访问次数拒绝了请求:" + resultJson);
                outErrorResult(resultJson);
            }
        } catch (Throwable throwable) {
            logger.error("", throwable);
        }
        return obj;
    }

    public void outErrorResult(String result) {
        response.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(result.getBytes("utf-8"));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    LoadingCache<String, RateLimiter> ipRequestCaches = CacheBuilder.newBuilder()
            .maximumSize(1000)// 设置缓存个数
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String s) throws Exception {
                    // 新的IP初始化 (限流每秒10个令牌响应)
                    return RateLimiter.create(10);
                }
            });

}