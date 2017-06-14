package com.danniel.danielchang.sauweb01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;
import com.danniel.danielchang.sauweb01.presenter.NetAsyncTask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by danielchang on 2017/5/18.
 */

public class WelcomePage extends Activity {
    DBOpenHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        helper = new DBOpenHelper(this);
        db = helper.getReadableDatabase();
        Cursor c_news = db.rawQuery("select * from "+DBOpenHelper.TBNAME_NEWS+" where " +
                DBOpenHelper.TB_NEWS_ISTOP+"=1",null);
        if (c_news.getCount()>2){
            db.execSQL("delete from "+DBOpenHelper.TBNAME_NEWS+" where "+DBOpenHelper.TB_NEWS_ISTOP+"=1");
        }

//        NewsListEntity netList = new NewsListEntity();
//        new NetAsyncTask().execute(netList.getBasePage(),WelcomePage.this);

        final Intent intent = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 1000 * 3);
    }
}
