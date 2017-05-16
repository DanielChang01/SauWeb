package com.danniel.danielchang.sauweb01.presenter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danielchang on 2017/5/15.
 */

public class NetAsyncTaskForRefresh extends AsyncTask {


    private Document doc;
    DBOpenHelper helper;
    SQLiteDatabase db;
    NewsListEntity newsEntity = new NewsListEntity();
    List<Map<String,String>> myList = null;
    String myUrl = null;
    String netUrl = null;
    String netCategory = null;

    @Override
    protected Object doInBackground(Object[] params) {

        netUrl = (String) params[0];
        int baseNum = (int) params[1];
        netCategory = (String) params[2];
        Activity activity = (Activity) params[3];
        myUrl = netUrl + "/" + newsEntity.getPageListBaseNum() +baseNum+".html";

        helper = new DBOpenHelper(activity);
        db = helper.getWritableDatabase();

        doc = jsoupconnect(myUrl,360000);

        getNews_List(doc,newsEntity.getGetCommonNewsList());

        return myList;
    }

    private void getNews_List(Document doc, String getList) {

        myList = new ArrayList<>();
        Elements els = doc.select(getList);
        for (Element el : els) {
            if (el.select("a").size() > 1) {
                continue;
            }
            Map<String,String> map = new HashMap<>();
            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.TB_NEWS_CATEGORY,netCategory);
            cv.put(DBOpenHelper.TB_NEWS_TITLE,el.text());
            map.put(DBOpenHelper.TB_NEWS_TITLE,el.text());
            cv.put(DBOpenHelper.TB_NEWS_URL,el.attributes().get("href"));
            map.put(DBOpenHelper.TB_NEWS_URL,el.attributes().get("href"));
            myList.add(map);
            db.insert(DBOpenHelper.TBNAME_NEWS,null,cv);
        }


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
                System.out.println("connect 获取失败啦,再重试" + retry + "次");
            }
        }
        return doc;
    }
}
