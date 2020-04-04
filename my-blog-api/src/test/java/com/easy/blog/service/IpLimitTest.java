package com.easy.blog.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouyong
 * @DATE 2019/6/13
 */
public class IpLimitTest {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test() throws Exception {
        int n = 20;
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            String ipAddr = "c" + (i % 2);
            threads[i] = new MyThread(ipAddr);
        }
        for (int i = 0; i < n; i++) {
            threads[i].start();
            threads[i].join();
        }
    }

    LoadingCache<String, RateLimiter> ipRequestCaches = CacheBuilder.newBuilder()
            .maximumSize(1000)// 设置缓存个数
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String s) throws Exception {
                    // 新的IP初始化 (限流每秒0.1个令牌响应,即10s一个令牌)
                    return RateLimiter.create(5.0);
                }
            });

    class MyThread extends Thread {

        private String ip;

        public MyThread(String ip) {
            this.ip = ip;
        }

        @Override
        public void run() {
            RateLimiter limiter = null;
            try {
                limiter = ipRequestCaches.get(ip);
                if (limiter.tryAcquire()) {
                    // 获得令牌（不限制访问）
                    System.out.println(ip + " get acq [" + sdf.format(new Date()) + "]");
                } else {
                    // 未获得令牌（限制访问）
                    System.out.println(ip + " limit acq [" + sdf.format(new Date()) + "]");
                }

                //模拟业务执行100毫秒
                Thread.sleep(100);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
