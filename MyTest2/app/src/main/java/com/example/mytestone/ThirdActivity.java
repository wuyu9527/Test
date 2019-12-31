package com.example.mytestone;

import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ThirdActivity extends AppCompatActivity {


    String database_sql = "CREATE TABLE USER_INOF(_ID INTEGER PRIMARY KEY,_NAME,_AGE,_ADDRESS)";
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        MyHandler.init().sendMessage(ThirdActivity.class, Message.obtain(MyHandler.init().getMyHandlers(), 3));
        String database_path = this.getFilesDir() + "/myDataBase.db";
        File file = new File(database_path);
        if (file.exists()){
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(database_path,null);
        }

    }
}
