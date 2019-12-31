package com.example.mytestone;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        MyHandler.init().sendMessage(SecondActivity.class,Message.obtain(MyHandler.init().getMyHandlers(),2));
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("info", "good good study");
        startService(intent);
    }
}
