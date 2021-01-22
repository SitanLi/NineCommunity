package com.mall.ninecommunity.aspect;

import com.blankj.utilcode.util.ToastUtils;
import com.mall.baselibrary.acp.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Aspect
public class PermissionTraceAspect {
    /**
     * 找到处理的切点
     * * *(..)  可以处理BehaviorTrace这个类所有的方法
     */
    @Pointcut("execution(@com.mall.ninecommunity.aspect.PermissionTrace  * *(..))")
    public void executionPermissionTrace() {

    }

    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PermissionTrace annotation = signature.getMethod().getAnnotation(PermissionTrace.class);
        if (annotation != null) {
            String[] permissions = annotation.permissions();
            boolean force = annotation.force();
            PermissionUtils.INSTANCE.permission(permissions, force, new Function1<Boolean, Unit>() {
                @Override
                public Unit invoke(Boolean aBoolean) {
                    if (aBoolean) {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    } else {
                        if (force) {

                            ToastUtils.showShort("请允许权限通过...");
                        }
                    }
                    return null;
                }
            });
        }
        return null;
    }
}
