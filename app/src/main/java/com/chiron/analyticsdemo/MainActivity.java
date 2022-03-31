package com.chiron.analyticsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chiron.analyticsdemo.Handler.HandlerImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListener();
    }

    private void initListener(){
        findViewById(R.id.btn_click).setOnClickListener(this);
        findViewById(R.id.btn_calculate).setOnClickListener(this);
        findViewById(R.id.btn_get_handler_name).setOnClickListener(this);
        findViewById(R.id.btn_say_hello).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_click:
                doClick(findViewById(R.id.btn_click));
                break;
            case R.id.btn_calculate:
                doCalculate();
                break;
            case R.id.btn_say_hello:
                doSayHello();
                break;
            case R.id.btn_get_handler_name:
                doGetName();
                break;
            default:
                break;
        }
    }

    private void doClick(View view){
        HandlerImpl.getInstance().click(view);
    }

    private void doCalculate(){
        Log.d("getCalculatevalue:",String.valueOf(HandlerImpl.getInstance().calculate(10,9)));
    }

    private void doSayHello(){
        HandlerImpl.getInstance().sayHello();
    }

    private void doGetName(){
        HandlerImpl.getInstance().getHandlerName();
    }
}