package com.danniel.danielchang.sauweb01;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends FragmentActivity {

    DBOpenHelper helper;
    SQLiteDatabase db;
    PagerSlidingTabStrip tabStrip;
    ViewPager viewPager;
    Document doc;
    NewsListEntity newsListEntity;
    ProgressDialog pd;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                initFragments();
                pd.dismiss();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewAndData();

        pd = new ProgressDialog(this);
        pd.setTitle("提示信息");
        pd.setMessage("正在加载...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

//        NewsListEntity netList = new NewsListEntity();
//        new NetAsyncTask().execute(netList.getBasePage(),MainActivity.this,pd);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doc = jsoupconnect(newsListEntity.getBasePage(),360000);
                /**
                 * 读取首页信息，并插入数据库
                 */
                getNews_List_li(doc,newsListEntity.getGetList_li());   //".list li a"
                getNews_List_li(doc,newsListEntity.getGetIDlist_li_a()); //".iDlist li a"
                getNews_List_li(doc,newsListEntity.getGetIFigureTitle());//".iFigure h3 a"
                getNews_List_li(doc,newsListEntity.getGetHeadlines()); //".Headlines h2 a"
                getNews_List_li(doc,newsListEntity.getGetIElist_li_a()); //".iElist li a"

                setUpdateNews(doc,newsListEntity.getGetUpdateFigure());  //".iFigure li a"
                setUpdateNews(doc,newsListEntity.getGetUpdateHeadline());//".info a"

                getNewsPic(doc,newsListEntity.getGetNewsPic()); //.pic a
                getADsPic(doc,newsListEntity.getGetAdLong());

                handler.sendEmptyMessage(1);
            }
        }).start();



    }


    private void initViewAndData(){
        helper = new DBOpenHelper(this);
        db = helper.getWritableDatabase();

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);

        newsListEntity = new NewsListEntity();

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

    private void initFragments(){

        Fragment[] fragments = {new FirstFragment(),new SHNewsFragment(), new NoticeFragment(), new TeachingFragment(),
                new LearningFragment(), new HighFragment(), new HRFragment(), new MediaFragment(),new SAUNewsPaperFragment(),
                new VideoFragment(), new FigureFragment(), new NewsFragment(), new InternationalFragment(),
                new AlumnaFragment(), new SchoolFragment()};
        String[] titles = {"首页","沈航要闻", "通知公告", "教学科研", "学术信息", "高教视点", "招生就业",
                "媒体沈航", "沈航校报", "视频新闻", "沈航人物", "新闻动态", "国际合作", "校友风采", "菁菁校园"};

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabStrip.setViewPager(viewPager);
    }

    private void getNewsPic(Document doc, String getNewsPic) {
        Elements els = doc.select(getNewsPic);
        for (Element el : els) {
            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.TB_PIC_ISTOP,1);
            cv.put(DBOpenHelper.TB_PIC_URL,el.getElementsByTag("img").get(0).attr("src"));
            cv.put(DBOpenHelper.TB_PIC_FROM_URL,el.attributes().get("href"));
            db.insert(DBOpenHelper.TBNAME_NEWS_PIC,null,cv);
        }
    }

    private void getADsPic(Document doc, String div_str) {
        Elements els = doc.select(div_str);
        for (Element el : els) {
            if (el.select("img").size() > 1) {
                continue;
            }
            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.TB_PIC_ISTOP,1);
            /**
             * bug处理，添加表字段
             * cv.put(DBOpenHelper.TB_PIC_FROM_URL,getCategory(el.attributes().get("src")));
             */
            cv.put(DBOpenHelper.TB_PIC_FROM_URL,getCategory(el.attributes().get("src")));
            cv.put(DBOpenHelper.TB_PIC_URL,el.attributes().get("src"));
            db.insert(DBOpenHelper.TBNAME_NEWS_PIC,null,cv);
        }
    }

    /**
     * 补充已存在新闻的描述
     * @param doc
     * @param div_str
     */
    private void setUpdateNews(Document doc, String div_str) {
        Elements els = doc.select(div_str);
        for (Element el : els) {
            if (el.select("a").size() > 1) {
                continue;
            }
            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.TB_NEWS_DESCRIBE,el.text());
            db.update(DBOpenHelper.TBNAME_NEWS,cv,DBOpenHelper.TB_NEWS_URL+"=?"
                    ,new String[]{el.attributes().get("href")});
        }
    }

    /**
     * 获取首页新闻信息，并添加数据库
     * @param doc
     * @param div_str
     */
    private void getNews_List_li(Document doc, String div_str) {
        Elements els = doc.select(div_str);
        for (Element el : els) {
            if (el.select("a").size() > 1) {
                continue;
            }
            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.TB_NEWS_TITLE,el.text());
            cv.put(DBOpenHelper.TB_NEWS_URL,el.attributes().get("href"));
            cv.put(DBOpenHelper.TB_NEWS_CATEGORY,getCategory(el.attributes().get("href")));
            cv.put(DBOpenHelper.TB_NEWS_ISTOP,1);
            db.insert(DBOpenHelper.TBNAME_NEWS,null,cv);
        }
    }


    /**
     * 获取新闻的分类信息
     * @param href
     * @return
     */
    private String getCategory(String href) {
        String[] str = href.split("/");
        String ret_str = "/"+str[1]+"/";
        return ret_str.trim();
    }

    /**
     * 获取Document，同时完成浏览器的适配
     * @param url
     * @param timeout
     * @return
     */
    private Document jsoupconnect(String url, int timeout) {

        Document doc = null;
        int retry = 5;
        while (null == doc && retry > 0) {
            retry--;
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:5.0)").timeout(timeout).get();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("connect 获取失败,再重试" + retry + "次");
            }
        }
        return doc;
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        long exitTime = 0;
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//            if((System.currentTimeMillis()-exitTime) > 2000){
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
////                db.delete(DBOpenHelper.TBNAME_NEWS,null,null);
////                db.execSQL("delete from "+DBOpenHelper.TBNAME_NEWS);
////                finish();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}

