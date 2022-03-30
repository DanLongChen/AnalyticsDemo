package com.chiron.analyticsdemo.Handler;

import android.util.Log;
import android.view.View;

import com.chiron.analytics.annotation.Trace;

public class HandlerImpl implements IHandler{
    private static final String TAG = HandlerImpl.class.getSimpleName();
    private HandlerImpl(){}

    private static class LazyHolder{
        private static final HandlerImpl INSTANCE = new HandlerImpl();
    }

    public static IHandler getInstance(){
        return LazyHolder.INSTANCE;
    }

    @Trace(method = "onclick")
    @Override
    public void click(View view) {
        Log.d(TAG,"doClick");
    }

    @Override
    public void sayHello() {
        Log.d(TAG,"doSayHello");
    }

    @Override
    public int calculate() {
        int sum=0;
        for(int i=0;i<100;i++){
            sum+=i;
        }
        return sum;
    }

    @Override
    public String getHandlerName() {
        return "getHandlerName";
    }
}
