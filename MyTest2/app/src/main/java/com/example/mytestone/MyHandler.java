package com.example.mytestone;


import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyHandler {

    public static String tag = "whxMyHandler";
    public static volatile MyHandler handler;
    private MyHandlers myHandlers;

    static synchronized MyHandler init() {
        if (handler == null) {
            handler = new MyHandler();
        }
        return handler;
    }

    interface MyHandlersListener {
        void callBack(Message message);
    }

    private volatile HashMap<Object, List<MyHandlersListener>> listenerHashMap;
    private volatile HashMap<Message, Object> messageHashMap;

    HashMap<Message, Object> getMessageHashMap() {
        if (messageHashMap == null) {
            messageHashMap = new HashMap<>();
        }
        return messageHashMap;
    }

    HashMap<Object, List<MyHandlersListener>> getListenerHashMap() {
        if (listenerHashMap == null) {
            listenerHashMap = new HashMap<>();
        }
        return listenerHashMap;
    }

    MyHandlers getMyHandlers() {
        return myHandlers;
    }

    private MyHandler() {
        myHandlers = new MyHandlers();
        listenerHashMap = new HashMap<>();
        messageHashMap = new HashMap<>();
    }

    void destroyHandler() {
        if (myHandlers!=null) {
            myHandlers.removeCallbacksAndMessages(null);
        }
        listenerHashMap = null;
        messageHashMap = null;
        myHandlers = null;
    }


    /**
     * 发送消息
     *
     * @param obj key
     * @param msg Message
     */
    void sendMessage(Object obj, Message msg) {
        if (getMessageHashMap() != null && myHandlers != null) {
            if (msg == null) {
                msg = Message.obtain();
            }
            getMessageHashMap().put(msg, obj);
            myHandlers.sendMessage(msg);
        }
    }

    /**
     * 接受消息
     *
     * @param obj      key
     * @param listener 监听
     */
    void addMyHandlersListener(Object obj, MyHandlersListener listener) {
        if (obj != null) {
            List<MyHandlersListener> listeners = getListenerHashMap().get(obj);
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            listeners.add(listener);
            getListenerHashMap().put(obj, listeners);
        }
    }

}
