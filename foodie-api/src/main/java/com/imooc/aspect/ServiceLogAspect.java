package com.imooc.aspect;

import com.immoc.utils.JSONResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP通知
     *  1、前置通知：在方法调用之前执行
     *  2、后置通知：在方法正常调用之后执行
     *  3、环绕通知：在方法调用之前和之后，都分别可以执行的通知
     *  4、异常通知：如果在方法调用过程中发生异常，则通知
     *  5、最终通知：在方法调用之后通知
     */

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主题
     * 第一处*： 代表返回的返回类型 *代表所有类型
     * 第二处包名：包名代表aop监控的类所在的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名 *代表所有类
     * d第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        LOGGER.info("=== 开始执行 {}.{} ===",
                        joinPoint.getTarget().getClass(),
                        joinPoint.getSignature().getName());
        //记录开始时间
        long begin = System.currentTimeMillis();

        //执行目标service
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long takeTime = end - begin;
        if(takeTime > 3000){
            LOGGER.error("=== 执行结束，耗时{}毫秒 ====",takeTime);
        } else if(takeTime > 2000){
            LOGGER.warn("=== 执行结束，耗时{}毫秒 ====",takeTime);
        }else{
            LOGGER.info("=== 执行结束，耗时{}毫秒 ====",takeTime);
        }

        return result;
    }





}
