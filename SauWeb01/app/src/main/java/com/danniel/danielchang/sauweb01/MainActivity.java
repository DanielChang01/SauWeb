package com.danniel.danielchang.sauweb01;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;
import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;
import com.danniel.danielchang.sauweb01.entities.VpEntity;
import com.danniel.danielchang.sauweb01.fragment.AlumnaFragment;
import com.danniel.danielchang.sauweb01.fragment.FigureFragment;
import com.danniel.danielchang.sauweb01.fragment.FirstFragment;
import com.danniel.danielchang.sauweb01.fragment.HRFragment;
import com.danniel.danielchang.sauweb01.fragment.HighFragment;
import com.danniel.danielchang.sauweb01.fragment.InternationalFragment;
import com.danniel.danielchang.sauweb01.fragment.LearningFragment;
import com.danniel.danielchang.sauweb01.fragment.MediaFragment;
import com.danniel.danielchang.sauweb01.fragment.NewsFragment;
import com.danniel.danielchang.sauweb01.fragment.NoticeFragment;
import com.danniel.danielchang.sauweb01.fragment.SAUNewsPaperFragment;
import com.danniel.danielchang.sauweb01.fragment.SHNewsFragment;
import com.danniel.danielchang.sauweb01.fragment.SchoolFragment;
import com.danniel.danielchang.sauweb01.fragment.TabPagerAdapter;
import com.danniel.danielchang.sauweb01.fragment.TeachingFragment;
import com.danniel.danielchang.sauweb01.fragment.VideoFragment;
import com.danniel.danielchang.sauweb01.presenter.NetAsyncTask;

public class MainActivity extends FragmentActivity {

    DBOpenHelper helper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBOpenHelper(this);
        db = helper.getWritableDatabase();


        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);

//        NewsListEntity netList = new NewsListEntity();
//        new NetAsyncTask().execute(netList.getBasePage(),MainActivity.this);


        Fragment[] fragments = {new FirstFragment(),new SHNewsFragment(), new NoticeFragment(), new TeachingFragment(),
                new LearningFragment(), new HighFragment(), new HRFragment(), new MediaFragment(),new SAUNewsPaperFragment(),
                new VideoFragment(), new FigureFragment(), new NewsFragment(), new InternationalFragment(),
                new AlumnaFragment(), new SchoolFragment()};
        String[] titles = {"首页","沈航要闻", "通知公告", "教学科研", "学术信息", "高教视点", "招生就业",
                "媒体沈航", "沈航校报", "视频新闻", "沈航人物", "新闻动态", "国际合作", "校友风采", "菁菁校园"};

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabStrip.setViewPager(viewPager);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        Log.i("dish_wh",String.valueOf(width)+"--"+String.valueOf(height));

        VpEntity e = new VpEntity();
        e.setScreenHeight(height);
        e.setScreenWidth(width);

        SharedPreferences sp = getSharedPreferences("MY_DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor p = sp.edit();
        p.putInt("screenWidth",width);
        p.putInt("screenHeight",height);
        p.commit();



    }
}

