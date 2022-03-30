package com.chiron.analyticsdemo.Handler;

import android.view.View;

import com.chiron.analytics.annotation.Trace;

public interface IHandler {
    @Trace(method = "onclick")
    void click(View view);

    void sayHello();

    int calculate();

    String getHandlerName();
}
