package com.example.mytestone;

import android.os.Handler;
import android.os.Message;

import java.util.List;

class MyHandlers extends Handler {

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Object o =MyHandler.init().getMessageHashMap().get(msg);
        if (o!=null){
            List<MyHandler.MyHandlersListener> listener = MyHandler.init().getListenerHashMap().get(o);
            if (listener!=null){
                for (MyHandler.MyHandlersListener myHandlersListener : listener) {
                    myHandlersListener.callBack(msg);
                }
            }
        }
    }
}
