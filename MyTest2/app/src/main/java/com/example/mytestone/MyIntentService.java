package com.example.mytestone;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@androidx.annotation.Nullable Intent intent) {
        assert intent != null;
        Log.e("MyIntentService--", Thread.currentThread().getName() + "--" + intent.getStringExtra("info") );
        for(int i = 0; i < 100; i++){ //耗时操作
            Log.i("onHandleIntent--",  i + "--" + Thread.currentThread().getName());
        }
    }

    public MyIntentService(){
        super("MyIntentService");
    }



    @Override
    public void onCreate() {
        Log.e("MyIntentService--", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyIntentService--", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("MyIntentService--", "onDestroy");
        super.onDestroy();
    }


}
