package com.chiron.analytics;

import android.util.Log;
import android.view.View;

import com.chiron.analytics.annotation.TrackElements;
import com.chiron.analytics.annotation.TrackEvent;
import com.chiron.analytics.annotation.TrackReturn;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Aspect
public class AspectJController {
    /**
     * 定义切入点，使用注解的方式定位
     */
    private static final String ANNOTATION_METHOD = "execution(@com.chiron.analytics.annotation.TrackEvent * *(..)";
    private static final String ANNOTATION_METHOD1 = "execution(* *(..)) && @annotation(com.chiron.analytics.annotation.TrackEvent)";
    /**
     * 使用匹配方式定位
     */
    private static final String CLICK_METHOD = "execution(* com.chiron.analyticsdemo.Handler.HandlerImpl.click(..))";

    private static final String SAY_HELLO_METHOD = "execution(* *.sayHello(..))";

    private static final String CALCULATE_METHOD = "execution(* *..*.calculate(..))";

    private static final String RETURN_VALUE = "call(* *..*.getHandlerName())";

    /**
     * 定义切点
     */
    @Pointcut(ANNOTATION_METHOD1)
    public void annotationTrack() {

    }

    @Pointcut(CLICK_METHOD)
    public void clickMethod(){

    }

    @Before(value = CLICK_METHOD)
    public void weaveBeforeJoinPoint(final ProceedingJoinPoint joinPoint){
        Log.d("Daniel","weaveBeforeJoinPoint");
    }

    @After(value = SAY_HELLO_METHOD)
    public void weaveAfterJoinPoint(final ProceedingJoinPoint joinPoint){
        Log.d("Daniel","weaveAfterJoinPoint");
    }

    @AfterReturning(pointcut = RETURN_VALUE, returning = "handlerName")
    public void trackReturnValue(String handlerName) {
        Log.d("Daniel", "trackReturnValue: " + handlerName);
    }

    @AfterReturning(pointcut = "annotationTrack()",returning = "returnValue")
    public void weaveJoinPoint(final JoinPoint joinPoint,final Object returnValue){
        try {
            performJoinPoint(joinPoint,returnValue);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void performJoinPoint(final JoinPoint joinPoint, final Object returnValue) {
        if (joinPoint == null) {
            return;
        }
        Map<String,String> extra = new HashMap<>(3);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        TrackEvent trackEvent = methodSignature.getMethod().getAnnotation(TrackEvent.class);
        String eventName = trackEvent.eventName();
        String category = trackEvent.category();
        String result = trackEvent.result();

        if (!"".equals(result) && returnValue != null) {
            extra.put(result, returnValue.toString());
        }

        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

        if(parameterAnnotations!=null && parameterAnnotations.length>0){
            int i=0;
            for(Annotation[] parameterAnnotation:parameterAnnotations){
                for (Annotation annotation : parameterAnnotation) {
                    if(annotation instanceof TrackElements){
                        String key = ((TrackElements) annotation).value();
                        String value = String.valueOf(joinPoint.getArgs()[i++]);
                        extra.put(key, value);
                    }
                }
            }
        }
        doTrack(eventName,category,extra);
    }

    /**
     * 这里模拟埋点本地缓存和网络上报接入
     */
    public void doTrack(String eventName, String category, Map<String, String> extra) {
        Log.e("doTrack", eventName + "  " + category + "  " + extra);
    }
}
