package com.ws.common.aspect;

import com.alibaba.fastjson.JSON;
import com.ws.common.annotation.SystemLog;
import com.ws.sys.entity.SysLog;
import com.ws.sys.service.ISysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 日志AOP增强
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class SysLogAspect {
    @Autowired
    ISysLogService sysLogService;

    @Pointcut("@annotation(com.ws.common.annotation.SystemLog)")
    public void logPointcut(){

    }
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行的开始时间
        long begin=System.currentTimeMillis();
        Object obj = point.proceed();
        //获取到目标方法执行的时长
        long time =System.currentTimeMillis()-begin;
        //保存日志操作的信息
        saveSyslog(point,time);
        return obj;
    }

    /**
     * 保存日志相关的方法
     *      操作的用户
     *      具体的操作
     *      操作的方法名称
     *      参数列表
     *      执行的时长
     *      客户端的ip
     *      记录创建的时间
     * @param point
     */
    private void saveSyslog(ProceedingJoinPoint point,long time) {
        SysLog sysLog = new SysLog();
        //TODO 当前登录的用户 和 ip信息
        //获取当前调用的方法
        MethodSignature signature= (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //需要获取 方法@SysLog注解 的描述信息
        SystemLog systemLogAnno=method.getAnnotation(SystemLog.class);
        if (systemLogAnno!=null){
            //绑定 operation 的值
            sysLog.setOperation(systemLogAnno.value());
        }
        //绑定方法的名称
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className+"."+methodName+"()");

        //获取方法的参数
        Object[] paramArgs = point.getArgs();
        String params = JSON.toJSON(paramArgs).toString();
        sysLog.setParams(params);

        //方法调用的执行时长
        sysLog.setTime(time);

        //记录的创建时间
        sysLog.setCreateDate(LocalDateTime.now());

        sysLogService.save(sysLog);
    }

}
