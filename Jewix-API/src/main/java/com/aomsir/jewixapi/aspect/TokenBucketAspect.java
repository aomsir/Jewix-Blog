//package com.example.Annaotion;
//
//import cn.hutool.core.util.StrUtil;
//import com.example.log.LogBackRemoteUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.script.RedisScript;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.time.Instant;
//import java.util.Collections;
//import java.util.concurrent.TimeUnit;
//
//
//@Slf4j
///**
// * PROJECT_NAME TokenBucketAspect
// *
// * @Author <a href="Alliance github_https://github.com/AllianceTing"/>
// * @description 11
// */
//@Component
//@Aspect
//public class TokenBucketAspect {
//    private static final String SEPARATOR = ":";
//    private static final String REDIS_LIMIT_KEY_PREFIX = "Redis::limit:";
//
//    @Resource
//    RedisTemplate<String, Long> redisTemplate;
//
//    @Resource
//    RedisScript<Long> limitScript;
//
//    private boolean shouldLimited(String key, long max, long timeout, TimeUnit timeUnit) {
//        // 最终的 key 格式为：
//        // limit:自定义key:IP
//        // limit:类名.方法名:IP
//        key = REDIS_LIMIT_KEY_PREFIX + key;
//        // 统一使用单位毫秒
//        long ttl = timeUnit.toMillis(timeout);
//        // 当前时间毫秒数
//        long now = Instant.now().toEpochMilli();
//        long expired = now - ttl;
//        // 注意这里必须转为 String,否则会报错 java.lang.Long cannot be cast to java.lang.String
//        Long executeTimes = redisTemplate.execute(limitScript, Collections.singletonList(key), String.valueOf(now), String.valueOf(ttl), String.valueOf(expired), String.valueOf(max));
//        if (executeTimes != null) {
//            if (executeTimes == 0) {
//                log.error("【{}】在单位时间 {} 毫秒内已达到访问上限，当前接口上限 {}", key, ttl, max);
//                return true;
//            } else {
//                log.info("【{}】在单位时间 {} 毫秒内访问 {} 次", key, ttl, executeTimes);
//                return false;
//            }
//        }
//        return false;
//    }
//
//    @Around(value = "@annotation(com.example.Annaotion.BucketRateLimit)")
//    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//        // 获取 RateLimiter 注解
//        BucketRateLimit rateLimiter = method.getAnnotation(BucketRateLimit.class);
//        if (rateLimiter != null) {
//            String key = String.valueOf(rateLimiter.key());
//            // 默认用类名+方法名做限流的 key 前缀
//            if (StrUtil.isBlank(key)) {
//                key = method.getDeclaringClass().getName() + StrUtil.DOT + method.getName();
//            }
//            // 最终限流的 key 为 前缀 + IP地址
//            // TODO: 此时需要考虑局域网多用户访问的情况，因此 key 后续需要加上方法参数更加合理
//            key = key + SEPARATOR + LogBackRemoteUtil.getIpAddr();
//            long max = rateLimiter.max();
//            long timeout = rateLimiter.timeout();
//            TimeUnit timeUnit = rateLimiter.timeUnit();
//            boolean limited = shouldLimited(key, max, timeout, timeUnit);
//            if (limited) {
//                throw new RuntimeException("手速太快了，慢点儿吧~");
//            }
//        }
//
//        return point.proceed();
//    }
//}
