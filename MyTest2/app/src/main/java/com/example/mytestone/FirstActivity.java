package com.example.mytestone;

import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        MyHandler.init().sendMessage(FirstActivity.class,Message.obtain(MyHandler.init().getMyHandlers(),1));
        MyHandler.init().addMyHandlersListener(ThirdActivity.class, new MyHandler.MyHandlersListener() {
            @Override
            public void callBack(Message message) {
                MyHandler.init().sendMessage("startWakenThread",Message.obtain());
                Log.i(MyHandler.tag, "FirstActivity:" + message.toString());
            }
        });
        MyHandler.init().getMyHandlers().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(MyHandler.tag,"5秒后运行runnable");

            }
        },5000);
    }


}
