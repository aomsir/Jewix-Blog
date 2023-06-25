package com.aomsir.jewixapi.aspect;

import com.alibaba.fastjson.JSON;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.mapper.LogMapper;
import com.aomsir.jewixapi.pojo.entity.OperatedLog;
import com.aomsir.jewixapi.util.NetUtils;
import com.aomsir.jewixapi.util.UserHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 操作日志记录切面类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Aspect
@Component
public class OperateLogAspect {
    @Resource
    private UserHolder userHolder;

    @Resource
    private NetUtils netUtils;

    @Resource
    private LogMapper logMapper;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.aomsir.jewixapi.annotation.OperateLog)")
    public void optLogPointCut() {}


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "optLogPointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public void saveOptLog(JoinPoint joinPoint, Object keys) throws JsonProcessingException {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        OperateLog optLog = method.getAnnotation(OperateLog.class);

        OperatedLog operatedLog = new OperatedLog();
        // 操作模块
        operatedLog.setOptModule(api.tags()[0]);

        // 操作类型
        operatedLog.setOptType(optLog.optType());

        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;

        // 请求方式
        operatedLog.setReqMethod(Objects.requireNonNull(request).getMethod());
        // 请求方法
        operatedLog.setJavaMethod(methodName);
        // 请求参数
        operatedLog.setOptReqMsg(JSON.toJSONString(joinPoint.getArgs()));
        // 返回结果
        operatedLog.setOptRespMsg(JSON.toJSONString(keys));
        // 请求用户ID
        operatedLog.setUserId(this.userHolder.getUserId());
        // 请求IP

        String realIp = this.netUtils.getRealIp(request);
        String location = this.netUtils.getLocationInfo(realIp);
        operatedLog.setIp(realIp);
        operatedLog.setLocation(location);
        // 请求URL
        operatedLog.setReqUrl(request.getRequestURI());
        // 操作时间
        operatedLog.setOptTime(new Date());
        this.logMapper.insertOperateLog(operatedLog);
    }

}