package com.danniel.danielchang.sauweb01.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.danniel.danielchang.sauweb01.ContentActivity;
import com.danniel.danielchang.sauweb01.R;
import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;
import com.danniel.danielchang.sauweb01.entities.RotateBean;
import com.danniel.danielchang.sauweb01.presenter.RotateVpAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.TIME;


/**
 * 创建人：daniel
 * 创建日期：2017/04/03
 * 修改日期：2017/04/09
 * modified by Daniel at 2017/4/15
 * modify_detail:
 * 1.add views and binding with ids
 * 2.get db cursor
 * modified by Daniel at 2017/5/4
 * modify_detail:
 * 1.alter method of initADs, use StringBuilder for receiving the record in database
 */

public class FirstFragment extends Fragment{

    View view;
    DBOpenHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    private String str_news_url = new NewsListEntity().getBasePage();
    private String str_news_category = new NewsListEntity().getFisrtPage();

    private Handler handler;
    private boolean isRotate = false;
    private Runnable rotateRunnable;

    private List<RotateBean> datas;
    private RotateVpAdapter vpAdapter;
    private NewsListEntity newsListEntity;

    //轮播图
    ViewPager first_pager_viewPager;
    LinearLayout first_pager_listLayout;

    //沈航要闻
    TextView tv_shnews_title_left_note;
    TextView tv_shnews_title_more;
    TextView tv_shnews_title;
    TextView tv_shnews_content;
    ListView shnews_listView;

    //通知平台
    TextView tv_notice_title_left_note;
    TextView tv_notice_title_more;
    TextView tv_notice_title;
    TextView tv_notice_content;
    ListView notice_listView;

    //教学科研
    TextView tv_teaching_title_left_note;
    TextView tv_teaching_title_more;
    ListView teaching_content_listView;

    //新闻动态
    TextView tv_news_title_left_note;
    TextView tv_news_title_more;
    ListView news_content_listView;

    //媒体沈航
    TextView tv_media_title_left_note;
    TextView tv_media_title_more;
    ListView media_content_listView;

    //ad_long
    WebView ad_long01_webView;
    WebView ad_long02_webView;
    WebView ad_long03_webView;

    //招生就业
    TextView tv_hr_title_left_note;
    TextView tv_hr_title_more;
    WebView hr_content_webView;
    ListView hr_content_listView;

    //国际合作
    TextView tv_international_title_left_note;
    TextView tv_international_title_more;
    WebView international_content_webView;
    ListView international_content_listView;

    //沈航校报
    TextView tv_SAUNewspaper_title_left_note;
    TextView tv_SAUNewspaper_title_more;
    WebView SAUNewspaper_content_webView;
    ListView SAUNewspaper_content_listView;

    //学术信息
    TextView tv_learning_title_left_note;
    TextView tv_learning_title_more;
    WebView learning_content_webView;
    ListView learning_content_listView;

    //菁菁校园
    TextView tv_school_title_left_note;
    TextView tv_school_title_more;
    WebView school_content_webView;
    ListView school_content_listView;

    //视频新闻
    TextView tv_video_title_left_note;
    TextView tv_video_title_more;
    ListView video_content_listView;

    //校友风采
    TextView tv_alumna_title_left_note;
    TextView tv_alumna_title_more;
    WebView alumna_content_webView;
    ListView alumna_content_listView;

    //沈航人物
    TextView tv_figure_title_left_note;
    TextView tv_figure_title_more;
    ListView figure_content_listView;

    //高教视点
    TextView tv_high_title_left_note;
    TextView tv_high_title_more;
    ListView high_content_listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first,container,false);

        initView(view);
        initNewsCursor(view);
        initBanner(view);
        initADs(view);
        initMoreUrl(view);

        return view;
    }

    private void initMoreUrl(View view) {
        //1.沈航新闻
        tv_shnews_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getShnewsPage();
            }
        });
        //2.通知公告
        tv_notice_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getNoticePage();
            }
        });
        //3.教学科研
        tv_teaching_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getTeachingPage();
            }
        });
        //4.学术信息
        tv_learning_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getLearningPage();
            }
        });
        //5.高教视点
        tv_high_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getHighPage();
            }
        });
        //6.招生就业
        tv_hr_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getHrPage();
            }
        });
        //7.媒体沈航
        tv_media_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getMediaPage();
            }
        });
        //8.沈航校报
        tv_SAUNewspaper_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getSAUNewspaperPage();
            }
        });
        //9.视频新闻
        tv_video_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getVideoPage();
            }
        });
        //10.沈航人物
        tv_figure_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getFigurePage();
            }
        });
        //11.新闻动态
        tv_news_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getNewsPage();
            }
        });
        //12.国际合作
        tv_international_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getInternationalPage();
            }
        });
        //13.校友风采
        tv_alumna_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getAlumnaPage();
            }
        });
        //14.菁菁校园
        tv_school_title_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_url = newsListEntity.getSchoolPage();
            }
        });


    }

    private void initADs(View view) {
        Cursor pic_Cursor = db.rawQuery("select * from " + DBOpenHelper.TBNAME_NEWS_PIC+" where "+
                DBOpenHelper.TB_PIC_ISTOP+"=1 and "+DBOpenHelper.TB_PIC_FROM_URL+"=\"/uploads/\"",null);

        StringBuilder sb = new StringBuilder();
        while (pic_Cursor.moveToNext()){
            sb.append(pic_Cursor.getString(pic_Cursor.getColumnIndex(DBOpenHelper.TB_PIC_URL))+";");
        }
        String[] str = sb.toString().split(";");

        setImageFulfill(ad_long01_webView);
        ad_long01_webView.loadUrl(newsListEntity.getBasePage()+str[0]);
        setImageFulfill(ad_long02_webView);
        ad_long02_webView.loadUrl(newsListEntity.getBasePage()+str[1]);
        setImageFulfill(ad_long03_webView);
        ad_long03_webView.loadUrl(newsListEntity.getBasePage()+str[2]);

    }

    /**
     * 设置图片自适应界面，按照width填充
     */
    private void setImageFulfill(WebView webView) {
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    private void initBanner(View view) {

        buildDatas();// 构造数据
        vpAdapter = new RotateVpAdapter(datas, view.getContext());
        first_pager_viewPager.setAdapter(vpAdapter);
        // ViewPager的页数为int最大值,设置当前页多一些,可以上来就向前滑动
        // 为了保证第一页始终为数据的第0条 取余要为0,因此设置数据集合大小的倍数
        first_pager_viewPager.setCurrentItem(datas.size() * 100);

        // 开始轮播
        handler = new Handler();
        startRotate();
        // 添加轮播小点
        addPoints();
        // 随着轮播改变小点
        changePoints();


    }
    private void changePoints() {
        first_pager_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isRotate) {
                    // 把所有小点设置为白色
                    for (int i = 0; i < datas.size(); i++) {
//                        ImageView pointIv = (ImageView) pointLl.getChildAt(i);
                        ImageView pointIv = (ImageView) first_pager_listLayout.getChildAt(i);
//                        pointIv.setImageResource(R.mipmap.point_white);
                    }
                    // 设置当前位置小点为灰色
                    ImageView iv = (ImageView) first_pager_listLayout.getChildAt(position % datas.size());
//                    iv.setImageResource(R.mipmap.point_grey);
                    iv.setImageResource(R.mipmap.ic_launcher);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 添加轮播切换小点
     */
    private void addPoints() {
        // 有多少张图加载多少个小点
        for (int i = 0; i < datas.size(); i++) {
            ImageView pointIv = new ImageView(view.getContext());
            pointIv.setPadding(5,5,5,5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            pointIv.setLayoutParams(params);

            // 设置第0页小点的为灰色
            if (i == 0) {
//                pointIv.setImageResource(R.mipmap.point_grey);
                pointIv.setImageResource(R.mipmap.ic_launcher);
            } else {
//                pointIv.setImageResource(R.mipmap.point_white);
            }
            first_pager_listLayout.addView(pointIv);
        }
    }

    /**
     * 开始轮播
     */
    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                int nowIndex = first_pager_viewPager.getCurrentItem();
                first_pager_viewPager.setCurrentItem(++nowIndex);
                if (isRotate) {
                    handler.postDelayed(rotateRunnable, TIME);
//                    handler.postDelayed(rotateRunnable, 5000);
                }
            }
        };
        handler.postDelayed(rotateRunnable, TIME);
    }

    @Override
    public void onResume() {
        super.onResume();
        isRotate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRotate = false;
    }

    private void buildDatas() {
        datas = new ArrayList<>();

        datas.add(new RotateBean(newsListEntity.getBanner_Pic1()));
        datas.add(new RotateBean(newsListEntity.getBanner_Pic2()));
        datas.add(new RotateBean(newsListEntity.getBanner_Pic3()));
        datas.add(new RotateBean(newsListEntity.getBanner_Pic4()));
    }

    private void initNewsCursor(View view) {
        cursor = db.rawQuery("select * from " + DBOpenHelper.TBNAME_NEWS + " where "+
                DBOpenHelper.TB_NEWS_ISTOP+"=1",null);

        NewsListEntity newsListEntity = new NewsListEntity();
        initShnewsData(cursor,newsListEntity.getShnewsPage(),shnews_listView);
        initNoticeData(cursor,newsListEntity.getNoticePage(),notice_listView);
        initTeachingData(cursor,newsListEntity.getTeachingPage(),teaching_content_listView);
        initLearningData(cursor,newsListEntity.getLearningPage(),learning_content_listView);
        initHighData(cursor,newsListEntity.getHighPage(),high_content_listView);
        initHrData(cursor,newsListEntity.getHighPage(),hr_content_listView);
        initMediaData(cursor,newsListEntity.getMediaPage(),media_content_listView);
        initSAUNewspaperData(cursor,newsListEntity.getSAUNewspaperPage(),SAUNewspaper_content_listView);
        initVideoData(cursor,newsListEntity.getVideoPage(),video_content_listView);
        initFigureData(cursor,newsListEntity.getFigurePage(),figure_content_listView);
        initNewsData(cursor,newsListEntity.getNewsPage(),news_content_listView);
        initInternationalData(cursor,newsListEntity.getInternationalPage(),international_content_listView);
        initAlumnaData(cursor,newsListEntity.getAlumnaPage(),alumna_content_listView);
        initSchoolData(cursor,newsListEntity.getSchoolPage(),school_content_listView);

        initWebView();



    }

    private void initWebView() {
        NewsListEntity newsListEntity = new NewsListEntity();
        hr_content_webView.loadUrl(newsListEntity.getHr_note_pic());
        international_content_webView.loadUrl(newsListEntity.getInternational_note_pic());
        learning_content_webView.loadUrl(newsListEntity.getLearning_note_pic());
        school_content_webView.loadUrl(newsListEntity.getSchool_note_pic());
        alumna_content_webView.loadUrl(newsListEntity.getAlumna_note_pic());
    }

    private void initSchoolData(Cursor cursor, String schoolPage, ListView school_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,schoolPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        school_content_listView.setAdapter(adapter);
        school_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle bundle = new Bundle();
//                bundle.putString("net_Url",list.get(position).get("net_Url"));
//                bundle.putString("module_type",list.get(position).get("module_type"));
//                Intent intent = new Intent(NewsList.this,NewsContent.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
                getNewsShown(list,view,position);
            }
        });
    }

    private void initAlumnaData(Cursor cursor, String alumnaPage, ListView alumna_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,alumnaPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        alumna_content_listView.setAdapter(adapter);
        alumna_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initInternationalData(Cursor cursor, String internationalPage, ListView international_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,internationalPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        international_content_listView.setAdapter(adapter);
        international_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initNewsData(Cursor cursor, String newsPage, ListView news_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,newsPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        news_content_listView.setAdapter(adapter);
        news_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initFigureData(Cursor cursor, String figurePage, ListView figure_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,figurePage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        figure_content_listView.setAdapter(adapter);
        figure_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initVideoData(Cursor cursor, String videoPage, ListView video_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,videoPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        video_content_listView.setAdapter(adapter);
        video_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });

    }

    private void initSAUNewspaperData(Cursor cursor, String sauNewspaperPage, ListView sauNewspaper_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,sauNewspaperPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        sauNewspaper_content_listView.setAdapter(adapter);
        sauNewspaper_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initMediaData(Cursor cursor, String mediaPage, ListView media_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,mediaPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        media_content_listView.setAdapter(adapter);
        media_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });

    }

    private void initHrData(Cursor cursor, String highPage, ListView hr_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,highPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        hr_content_listView.setAdapter(adapter);
        hr_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initHighData(Cursor cursor, String highPage, ListView high_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,highPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        high_content_listView.setAdapter(adapter);
        high_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initLearningData(Cursor cursor, String learningPage, ListView learning_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,learningPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        learning_content_listView.setAdapter(adapter);
        learning_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initTeachingData(Cursor cursor, String teachingPage, ListView teaching_content_listView) {
        final List<Map<String,String>> list = getListDate(cursor,teachingPage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        teaching_content_listView.setAdapter(adapter);
        teaching_content_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });

    }

    private void initNoticeData(Cursor cursor, String noticePage, ListView notice_listView) {
        final List<Map<String,String>> list = getListDate(cursor,noticePage);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        notice_listView.setAdapter(adapter);
        notice_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getNewsShown(list,view,position);
            }
        });
    }

    private void initShnewsData(Cursor cursor, String category, ListView listView) {

        final List<Map<String,String>> list = getListDate(cursor,category);
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),list,R.layout.list_style_simple_news,
                new String[]{DBOpenHelper.TB_NEWS_TITLE},new int[]{R.id.id_simple_list_style_textView});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView tv_news_title = (TextView) view.findViewById(R.id.id_simple_list_style_textView);

                getNewsShown(list,view,position);

            }


        });
    }

    private void getNewsShown(List<Map<String, String>> list, View view, int position) {
        Map<String,String> myMap = list.get(position);
        String myUrl = myMap.get(DBOpenHelper.TB_NEWS_URL);
        Intent intent = new Intent(view.getContext(), ContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DBOpenHelper.TB_NEWS_URL,myUrl);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<Map<String, String>> getListDate(Cursor cursor, String category) {
        Log.i("dish_s",category);
        List<Map<String,String>> cache_list = new ArrayList<Map<String,String>>();

        while(cursor.moveToNext()){
//            Log.i("dish_cate",cursor.getString(cursor.getColumnIndex(DBOpenHelper.TB_NEWS_CATEGORY)));
//            Log.i("dish_sss",cursor.getString(cursor.getColumnIndex(DBOpenHelper.TB_NEWS_TITLE)));
//            Log.i("dish_url",cursor.getString(cursor.getColumnIndex(DBOpenHelper.TB_NEWS_URL)));
//            Log.i("dish_true2",String.valueOf(cursor.getString(
//                    cursor.getColumnIndex(DBOpenHelper.TB_NEWS_CATEGORY)).trim().equals(category.trim())));
            if (cursor.getString(
                    cursor.getColumnIndex(DBOpenHelper.TB_NEWS_CATEGORY)).trim().equals(category.trim())) {
                Map<String,String> cache_map = new HashMap<String,String>();
                cache_map.put(DBOpenHelper.TB_NEWS_TITLE, cursor.getString(
                        cursor.getColumnIndex(DBOpenHelper.TB_NEWS_TITLE)));
//                Log.i("dish_sif", "getListDate: "+cursor.getString(
//                        cursor.getColumnIndex(DBOpenHelper.TB_NEWS_TITLE)));
                cache_map.put(DBOpenHelper.TB_NEWS_URL,cursor.getString(
                        cursor.getColumnIndex(DBOpenHelper.TB_NEWS_URL)));
                cache_list.add(cache_map);
            }
        }
        cursor.moveToFirst();

        return cache_list;
    }


    private void initView(View view) {
        helper = new DBOpenHelper(view.getContext());
        db = helper.getWritableDatabase();

        newsListEntity = new NewsListEntity();

        first_pager_viewPager = (ViewPager) view.findViewById(R.id.id_first_viewPager);
        first_pager_listLayout = (LinearLayout) view.findViewById(R.id.id_first_rotate_point_container);
        //沈航要闻
        tv_shnews_title_left_note = (TextView) view.findViewById(R.id.id_common_shnews_title_left_note);
        tv_shnews_title_more = (TextView) view.findViewById(R.id.id_common_shnews_title_more);
        tv_shnews_title = (TextView) view.findViewById(R.id.id_common_shnews_news_title);
        tv_shnews_content = (TextView) view.findViewById(R.id.id_common_shnews_content);
        shnews_listView = (ListView) view.findViewById(R.id.id_common_shnews_listView);

        //通知平台
        tv_notice_title_left_note = (TextView) view.findViewById(R.id.id_common_notice_title_left_note);
        tv_notice_title_more = (TextView) view.findViewById(R.id.id_common_notice_title_more);
        tv_notice_title = (TextView) view.findViewById(R.id.id_common_notice_news_title);
        tv_notice_content = (TextView) view.findViewById(R.id.id_common_notice_content);
        notice_listView = (ListView) view.findViewById(R.id.id_common_notice_listView);

        //教学科研
        tv_teaching_title_left_note = (TextView) view.findViewById(R.id.id_common_teaching_title_left_note);
        tv_teaching_title_more = (TextView) view.findViewById(R.id.id_common_teaching_title_more);
        teaching_content_listView = (ListView) view.findViewById(R.id.id_common_teaching_content_listView);

        //新闻动态
        tv_news_title_left_note = (TextView) view.findViewById(R.id.id_common_news_title_left_note);
        tv_news_title_more = (TextView) view.findViewById(R.id.id_common_news_title_more);
        news_content_listView = (ListView) view.findViewById(R.id.id_common_news_content_listView);

        //媒体沈航
        tv_media_title_left_note = (TextView) view.findViewById(R.id.id_common_media_title_left_note);
        tv_media_title_more = (TextView) view.findViewById(R.id.id_common_media_title_more);
        media_content_listView = (ListView) view.findViewById(R.id.id_common_media_content_listView);

        //ad_long
        ad_long01_webView = (WebView) view.findViewById(R.id.id_common_ad_long01_webView);
        ad_long02_webView = (WebView) view.findViewById(R.id.id_common_ad_long02_webView);
        ad_long03_webView = (WebView) view.findViewById(R.id.id_common_ad_long03_webView);

        //招生就业
        tv_hr_title_left_note = (TextView) view.findViewById(R.id.id_common_hr_title_left_note);
        tv_hr_title_more = (TextView) view.findViewById(R.id.id_common_hr_title_more);
        hr_content_webView = (WebView) view.findViewById(R.id.id_common_hr_content_webView);
        hr_content_listView = (ListView) view.findViewById(R.id.id_common_hr_content_listView);

        //国际合作
        tv_international_title_left_note = (TextView) view.findViewById(R.id.id_common_international_title_left_note);
        tv_international_title_more = (TextView) view.findViewById(R.id.id_common_international_title_more);
        international_content_webView = (WebView) view.findViewById(R.id.id_common_international_content_webView);
        international_content_listView = (ListView) view.findViewById(R.id.id_common_international_content_listView);

        //沈航校报
        tv_SAUNewspaper_title_left_note = (TextView) view.findViewById(R.id.id_common_SAUNewspaper_title_left_note);
        tv_SAUNewspaper_title_more = (TextView) view.findViewById(R.id.id_common_SAUNewspaper_title_more);
        SAUNewspaper_content_webView = (WebView) view.findViewById(R.id.id_common_SAUNewspaper_content_webView);
        SAUNewspaper_content_listView = (ListView) view.findViewById(R.id.id_common_SAUNewspaper_content_listView);

        //学术信息
        tv_learning_title_left_note = (TextView) view.findViewById(R.id.id_common_learning_title_left_note);
        tv_learning_title_more = (TextView) view.findViewById(R.id.id_common_learning_title_more);
        learning_content_webView = (WebView) view.findViewById(R.id.id_common_learning_content_webView);
        learning_content_listView = (ListView) view.findViewById(R.id.id_common_learning_content_listView);

        //菁菁校园
        tv_school_title_left_note = (TextView) view.findViewById(R.id.id_common_school_title_left_note);
        tv_school_title_more = (TextView) view.findViewById(R.id.id_common_school_title_more);
        school_content_webView = (WebView) view.findViewById(R.id.id_common_school_content_webView);
        school_content_listView = (ListView) view.findViewById(R.id.id_common_school_content_listView);

        //视频新闻
        tv_video_title_left_note = (TextView) view.findViewById(R.id.id_common_video_title_left_note);
        tv_video_title_more = (TextView) view.findViewById(R.id.id_common_video_title_more);
        video_content_listView  = (ListView) view.findViewById(R.id.id_common_video_content_listView);

        //校友风采
        tv_alumna_title_left_note = (TextView) view.findViewById(R.id.id_common_alumna_title_left_note);
        tv_alumna_title_more = (TextView) view.findViewById(R.id.id_common_alumna_title_more);
        alumna_content_webView = (WebView) view.findViewById(R.id.id_common_alumna_content_webView);
        alumna_content_listView = (ListView) view.findViewById(R.id.id_common_alumna_content_listView);

        //沈航人物
        tv_figure_title_left_note = (TextView) view.findViewById(R.id.id_common_figure_title_left_note);
        tv_figure_title_more = (TextView) view.findViewById(R.id.id_common_figure_title_more);
        figure_content_listView = (ListView) view.findViewById(R.id.id_common_figure_content_listView);

        //高教视点
        tv_high_title_left_note = (TextView) view.findViewById(R.id.id_common_high_title_left_note);
        tv_high_title_more = (TextView) view.findViewById(R.id.id_common_high_title_more);
        high_content_listView = (ListView) view.findViewById(R.id.id_common_high_content_listView);


    }

}

