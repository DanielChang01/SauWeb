package com.danniel.danielchang.sauweb01.presenter;

import android.os.AsyncTask;

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

        newsListEntity = new NewsListEntity();
        newsEntity = new NewsEntity();

        doc = jsoupconnect(news_URL,360000);
        getNewsHandled(doc,news_Category);

        return newsEntity;
    }

    private void getNewsHandled(Document doc, String news_category) {

        Elements els_Title = doc.select(newsListEntity.getNewsTitleContent());
        for (Element el : els_Title){
            newsEntity.setNews_Title(el.text());
        }
        Elements els_Note = doc.select(newsListEntity.getNewsTitleNote());
        for (Element el : els_Note){
            newsEntity.setNews_Note(el.text());
        }
        Elements els_Content = doc.select(newsListEntity.getNewsRealContent());
        for (Element el : els_Content){
            newsEntity.setNews_Part_One(el.text());
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
