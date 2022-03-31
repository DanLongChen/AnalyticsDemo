package com.chiron.analyticsdemo.Handler;

import android.util.Log;
import android.view.View;

import com.chiron.analytics.annotation.TrackElements;
import com.chiron.analytics.annotation.TrackEvent;
import com.chiron.analytics.annotation.TrackReturn;

public class HandlerImpl implements IHandler{
    private static final String TAG = HandlerImpl.class.getSimpleName();
    private HandlerImpl(){}

    private static class LazyHolder{
        private static final HandlerImpl INSTANCE = new HandlerImpl();
    }

    public static IHandler getInstance(){
        return LazyHolder.INSTANCE;
    }

    @Override
    public void click(View view) {

    }

    @TrackEvent(eventName = "sayHello",category = "track")
    @Override
    public void sayHello() {

    }

    @TrackEvent(eventName = "play",category = "track")
    @Override
    public void play() {

    }

    @TrackEvent(eventName = "calculate",category = "track",result = "calculateResult")
    @Override
    public int calculate(@TrackElements(value = "times") int times,@TrackElements(value = "startValue") int startValue) {
        int sum = startValue;
        for (int i = 0; i < times; i++) {
            sum += i;
        }
        return sum;
    }

    @Override
    public String getHandlerName() {
        return "getHandlerName";
    }
}
