package com.danniel.danielchang.sauweb01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.danniel.danielchang.sauweb01.entities.NewsListEntity;
import com.danniel.danielchang.sauweb01.presenter.NetAsyncTask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by danielchang on 2017/5/18.
 */

public class WelcomePage extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

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
