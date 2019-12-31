package com.example.mytestone;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.flutter.embedding.android.FlutterActivity;
////flutter module引入
//import io.flutter.embedding.android.FlutterActivity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private ThreadPoolExecutor threadPoolExecutor;

    TextView tvH;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        setContentView(R.layout.activity_main);
        tvH = findViewById(R.id.tvH);
        tvH.setText("热修复成功");
        booleans = new LongSparseArray<>();
        threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                10, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        MyHandler.init();
        MyHandler.init().addMyHandlersListener(FirstActivity.class, new MyHandler.MyHandlersListener() {
            @Override
            public void callBack(Message message) {
                startWakenThread();
                Log.i(MyHandler.tag, "MainActivity:" + message.toString());
            }
        });
        MyHandler.init().addMyHandlersListener(SecondActivity.class, new MyHandler.MyHandlersListener() {
            @Override
            public void callBack(Message message) {
                startWakenThread();
                Log.i(MyHandler.tag, "MainActivity:" + message.toString());
            }
        });
        MyHandler.init().addMyHandlersListener(ThirdActivity.class, new MyHandler.MyHandlersListener() {
            @Override
            public void callBack(Message message) {
                startWakenThread();
                Log.i(MyHandler.tag, "MainActivity:" + message.toString());
            }
        });

        MyHandler.init().addMyHandlersListener("startWakenThread", new MyHandler.MyHandlersListener() {
            @Override
            public void callBack(Message message) {
                startWakenThread();
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvH:
                for (int i = 0; i < 25; i++) {
                    MyTask myTask = new MyTask(i);
                    threadPoolExecutor.execute(myTask);

                    System.out.println("线程池中线程数目：" + threadPoolExecutor.getPoolSize() + "，队列中等待执行的任务数目：" +
                            threadPoolExecutor.getQueue().size() + "，已执行玩别的任务数目：" + threadPoolExecutor.getCompletedTaskCount());
                }
                break;
            case R.id.tvJ:
                startWakenThread();
                break;
            case R.id.button:
//                Crashlytics.setString("username", "这是账号");
//                Crashlytics.setString("password", "这是密码");
//                Crashlytics.setUserName("直接的用户名");
//                Crashlytics.setUserEmail("直接的Email");
//                Crashlytics.setUserIdentifier("直接的用户标识");
//                Crashlytics.setString("手机厂商", SystemUtil.getDeviceBrand());
//                Crashlytics.setString("手机型号", SystemUtil.getSystemModel());
//                Crashlytics.setString("手机当前系统语言", SystemUtil.getSystemLanguage());
//                Crashlytics.setString("Android系统版本号", SystemUtil.getSystemVersion());
//                Crashlytics.setString("手机IMEI", SystemUtil.getIMEI(getApplicationContext()));
//                Crashlytics.getInstance().crash();
//                try {
//                    Crashlytics.getInstance().crash();
//                } catch (Exception e) {
//                    Crashlytics.logException(e);
//                }

                //flutter module引入
//                startActivity(
//                        FlutterActivity.createDefaultIntent(this)
//                );
                //flutter aar引入
                startActivity(
                        FlutterActivity.createDefaultIntent(this)
                );
                break;
        }

    }

    /**
     * 该线程作为一个唤醒线程
     */
    public void startWakenThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (booleans.size() != 0) {
                    Log.i(MyHandler.tag, "size:" + booleans.size());
//                    int count = booleans.size();
//                    for (int i = 0; i < count; i++) {
//                        if (booleans.valueAt(i)) {
                    synchronized (object) {
                        System.out.println("唤醒线程开始执行...");
                        object.notify();
                    }
//                        }
//                    }
                }
            }
        });
        t.start();
    }

    public void notifySingeThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    object.notify();
                }
            }
        });
        thread.start();
    }

    /**
     * 对象锁
     */
    public static final Object object = new Object();
    private LongSparseArray<Boolean> booleans;

    /**
     * 实行任务
     */
    private volatile int num = 0;

    class MyTask implements Runnable {
        private int taskNum;
        //显示定义Lock对象
        private final Lock lock = new ReentrantLock();
        //获得指定Lock对象对应的条件变量
        private final Condition con = lock.newCondition();

        MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            //lock.lock();
            System.out.println("正在执行task " + taskNum);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (taskNum) {
                case 10:
                    startActivity(new Intent(MainActivity.this, FirstActivity.class));
                    break;
                case 15:
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    break;
                case 24:
                    startActivity(new Intent(MainActivity.this, ThirdActivity.class));
                    break;
            }
            try {
                synchronized (object) {
                    Log.i(MyHandler.tag, "id:" + Thread.currentThread().getId());
                    booleans.put(Thread.currentThread().getId(), true);
                    //con.await();
                    //object.wait();
                    num++;
                }
                int index = booleans.indexOfKey(Thread.currentThread().getId());
                if (index < booleans.size() - 1) {
                    if (booleans.get(booleans.keyAt(index + 1))) {
                        notifySingeThread();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //lock.unlock();
            }
            System.out.println("task " + taskNum + "执行完毕:" + num);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyHandler.init().destroyHandler();
    }
}
