package com.chiron.analyticsdemo.Handler;

import android.view.View;

import com.chiron.analytics.annotation.TrackElements;

public interface IHandler {
    void click(View view);

    void sayHello();

    void play();

    int calculate(int times,int startValue);

    String getHandlerName();
}
