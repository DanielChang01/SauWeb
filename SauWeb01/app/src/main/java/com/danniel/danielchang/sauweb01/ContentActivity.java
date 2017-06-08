package com.danniel.danielchang.sauweb01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsEntity;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;
import com.danniel.danielchang.sauweb01.presenter.GetNewsContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by danielchang on 2017/5/17.
 */

public class ContentActivity extends Activity {

    String myUrl = null;
    String myCategory = null;
    NewsListEntity newsListEntity = null;
    NewsEntity newsEntity;
    Cursor myPhoto;
    DBOpenHelper helper;
    SQLiteDatabase db;
    List<WebView> myWebList;

    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;

    TextView myTitle;
    TextView myNote;
    TextView myContent;
    TextView myShowResource;
    WebView myWebView;
    ViewPager myViewPager;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_for_pure_text);
        helper = new DBOpenHelper(this);
        db = helper.getWritableDatabase();

        newsListEntity = new NewsListEntity();
        myTitle = (TextView) findViewById(R.id.id_pure_text_title);
        myNote = (TextView) findViewById(R.id.id_pure_text_title_note);
        myContent = (TextView) findViewById(R.id.id_pure_text_content);
        myShowResource = (TextView) findViewById(R.id.id_pure_text_showSource);
        myWebView = (WebView) findViewById(R.id.id_pure_text_webView);
        myViewPager = (ViewPager) findViewById(R.id.id_pure_text_viewPager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myCategory = bundle.getString(DBOpenHelper.TB_NEWS_URL);
        myUrl = newsListEntity.getBasePage()+myCategory;

        String str_Category = getCategory(myCategory);


        try {
            //获取异步线程的返回类型
            newsEntity = (NewsEntity) new GetNewsContent().execute(myCategory,myUrl,ContentActivity.this).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (newsEntity != null){
            //查询该新闻是否有图片
            myPhoto = null;
            myPhoto = db.rawQuery("select "+DBOpenHelper.TB_PIC_URL+" from "+DBOpenHelper.TBNAME_NEWS_PIC +
                    " where "+DBOpenHelper.TB_PIC_FROM_URL+"='"+myCategory+"' and "+ DBOpenHelper.TB_PIC_ISTOP+
                    "!=1",null);
            showNewsContent(newsEntity,str_Category,myPhoto);
        }

    }

    private void showNewsContent(NewsEntity newsEntity,String category,Cursor myPhoto) {
        myTitle.setText(newsEntity.getNews_Title());
        myShowResource.setText("查看原文: "+myUrl);
        myShowResource.setTextSize(10);
        myShowResource.setAutoLinkMask(Linkify.ALL);
        myShowResource.setMovementMethod(LinkMovementMethod.getInstance());
        myNote.setText(newsEntity.getNews_Note());
        int i = myPhoto.getColumnCount();


        if (category.equals(newsListEntity.getSAUNewspaperPage())){
            String str = newsEntity.getNews_Part_One();
            myViewPager.setVisibility(View.GONE);
            myContent.setText(newsEntity.getNews_Part_One());
            myContent.setAutoLinkMask(Linkify.ALL);
            myContent.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (myPhoto.getColumnCount()>0){

            if (myPhoto.moveToNext()){
//                myPhoto.moveToFirst();
                myPhoto.moveToPrevious();
                myWebList = new ArrayList<>();
                while (myPhoto.moveToNext()){
                    WebView webView = new WebView(this);
                    setImageFulfill(webView);
                    String str_url = myPhoto.getString(myPhoto.getColumnIndex(DBOpenHelper.TB_PIC_URL)).trim();
                    if (!str_url.substring(0,4).equals("http")){
                        str_url = newsListEntity.getBasePage()+str_url;
                    }
                    webView.loadUrl(str_url);
                    myWebList.add(webView);
                }
                adapter = new ViewPagerAdapter();
                myViewPager.setAdapter(adapter);
                myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        oldPosition = position;
                        currentItem = position;
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            } else {
                myViewPager.setVisibility(View.GONE);
            }
            myContent.setText(newsEntity.getNews_Part_One());
            myContent.setAutoLinkMask(Linkify.WEB_URLS);
            myContent.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            myViewPager.setVisibility(View.GONE);
            myContent.setText(newsEntity.getNews_Part_One());
            myContent.setAutoLinkMask(Linkify.WEB_URLS);
            myContent.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private String getCategory(String myCategory) {
        String[] strs = myCategory.trim().split("/");
        return "/"+strs[1]+"/";
    }

    /**
     * 设置图片自适应界面，按照width填充
     */
    private void setImageFulfill(WebView webView) {
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }



    private class ViewPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return myWebList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(myWebList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(myWebList.get(position));
            return myWebList.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleWithFixedDelay(
//                new ViewPageTask(),
//                2,
//                2,
//                TimeUnit.SECONDS);
    }

    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % myWebList.size();
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            myViewPager.setCurrentItem(currentItem);
        }
    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }


}
