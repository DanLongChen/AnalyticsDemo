package com.chiron.analytics;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectJController {
    private static final String POINTCUT_METHOD =
            "execution(@com.chiron.analytics.annotation.Trace * *(..))";
    private static final String TEXT_METHOD = "* com.chiron.analyticsdemo.Handler.HandlerImpl.click(..)";

    @Pointcut(POINTCUT_METHOD)
    public void annoHaviorTrace() {
        Log.e("Daniel", "annoHaviorTrace");
    }

    @Around("annoHaviorTrace()")
    public Object weaveJoinPoint(final ProceedingJoinPoint joinPoint){
        Log.e("Daniel","weaveJoinPoint");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Pointcut("execution(void com.chiron.analyticsdemo.Handler.HandlerImpl.doClick(..))")
    public void doClick(final ProceedingJoinPoint joinPoint){

    }

    @Around("execution(void com.chiron.analyticsdemo.Handler.IHandler.doClick(..))")
    public void onAroundClick(final ProceedingJoinPoint joinPoint){
        Log.d("Daniel","onAroundClick");
        Object target = joinPoint.getTarget();
        String className = "";
        if(target!=null){
            className = target.getClass().getName();
            if (className.contains("$")) {
                className = className.split("\\$")[0];
            }
            if (className.contains("_ViewBinding")) {
                className = className.split("_ViewBinding")[0];
            }
        }
        Object[] args = joinPoint.getArgs();
        if(args.length>=1 && args[0] instanceof View){
            View view = (View)args[0];
            int id = view.getId();
            if(id<0){
                doTrace(className,"");
            }else{
                String entryName = view.getResources().getResourceEntryName(id);
                doTrace(className,entryName);
            }
        }
        try {
            joinPoint.proceed();//执行原来的方法
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void doTrace(String key,String value){
        Log.d(key,value);
    }
}
