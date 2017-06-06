package com.danniel.danielchang.sauweb01.presenter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsEntity;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by danielchang on 2017/5/13.
 * use for handle news content such as devided news
 * handle purl text, text and pic, and video etc
 */

public class GetNewsContent extends AsyncTask{

    Document doc;
    String news_Category;
    String news_URL;
    NewsListEntity newsListEntity;
    NewsEntity newsEntity;
    DBOpenHelper helper;
    SQLiteDatabase db;
    Activity activity;


    /**
     * 参数要求：
     * 1，数据库中的新闻分类
     * 2，新闻的完整url
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(Object[] params) {

        news_Category = (String) params[0];
        news_URL = (String) params[1];
        activity = (Activity) params[2];

        newsListEntity = new NewsListEntity();
        newsEntity = new NewsEntity();

        helper = new DBOpenHelper(activity);
        db = helper.getWritableDatabase();

        doc = jsoupconnect(news_URL,360000);
        getNewsHandled(doc,news_Category);

        return newsEntity;
    }

    private void getNewsHandled(Document doc, String news_category) {
        String str_Category = getCategory(news_category);

        Elements els_Title = doc.select(newsListEntity.getNewsTitleContent());
        for (Element el : els_Title){
            newsEntity.setNews_Title(el.text());
        }
        Elements els_Note = doc.select(newsListEntity.getNewsTitleNote());
        for (Element el : els_Note){
            newsEntity.setNews_Note(el.text());
        }
        Elements els_Content = null;
        if (str_Category.trim().equals(newsListEntity.getVideoPage())){
            els_Content = doc.select(newsListEntity.getGetVideoContent());
            for (Element el : els_Content){
//                newsEntity.setNews_Part_One(newsListEntity.getBasePage()+el.attributes().get("src")+" " +
//                        "\n\n"+"提示：我们的视频需要强大的插件，请移至强大的PC端观看！！");
                newsEntity.setNews_Part_One("\n\n"+"提示：我们的视频需要强大的插件，请移至强大的PC端观看！！");
            }
        } else if (str_Category.trim().equals(newsListEntity.getSAUNewspaperPage())){
            newsEntity.setNews_Part_One("提示：无法加载插件！请点击链接下载观看！");
            els_Content = doc.select(newsListEntity.getGetNewsPaperContent());
            for (Element el : els_Content){
                newsEntity.setNews_Part_One(newsEntity.getNews_Part_One()+"\n\n"+newsListEntity.getBasePage()+el.attributes().get("href"));
            }
        } else {
            els_Content = doc.select(newsListEntity.getNewsRealContent());
            for (Element el : els_Content){
                newsEntity.setNews_Part_One(handleContent(el.text()));
            }
        }
        Elements els_Pics = doc.select(newsListEntity.getGetNewsCommonPic());
        if (els_Pics.size()>0){
            for (Element el : els_Pics){
                ContentValues cv = new ContentValues();
                cv.put(DBOpenHelper.TB_PIC_FROM_URL,news_Category);
                //String str = el.attributes().get("src");
                cv.put(DBOpenHelper.TB_PIC_URL,el.attributes().get("src"));
                db.insert(DBOpenHelper.TBNAME_NEWS_PIC,null,cv);
            }

        }
    }

    private String handleContent(String content) {
        StringBuilder sb = new StringBuilder();
        String[] strs = content.split(" ");
        for (int i = 0; i < strs.length; i ++){
            /**
             * 用于去除简单空格所带来的排版问题
             */
            if (strs[i].length()<10){
                sb.append(strs[i]);
            } else {
                sb.append(strs[i] + "\n");
            }
        }
        return sb.toString();
    }

    private String getCategory(String myCategory) {
        String[] strs = myCategory.trim().split("/");
        return "/"+strs[1]+"/";
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
