package com.danniel.danielchang.sauweb01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsEntity;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;
import com.danniel.danielchang.sauweb01.presenter.GetNewsContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

/**
 * Created by danielchang on 2017/5/17.
 */

public class ContentActivity extends Activity {

    String myUrl = null;
    String myCategory = null;
    NewsListEntity newsListEntity = null;
    NewsEntity newsEntity;

    TextView myTitle;
    TextView myNote;
    TextView myContent;
    WebView myWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_for_pure_text);

        newsListEntity = new NewsListEntity();
        myTitle = (TextView) findViewById(R.id.id_pure_text_title);
        myNote = (TextView) findViewById(R.id.id_pure_text_title_note);
        myContent = (TextView) findViewById(R.id.id_pure_text_content);
        myWebView = (WebView) findViewById(R.id.id_pure_text_webView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myCategory = bundle.getString(DBOpenHelper.TB_NEWS_URL);
        myUrl = newsListEntity.getBasePage()+myCategory;

        String str_Category = getCategory(myCategory);

        try {
            //获取异步线程的返回类型
            newsEntity = (NewsEntity) new GetNewsContent().execute(myCategory,myUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (newsEntity != null){
            showNewsContent(newsEntity,str_Category);
        }

    }


    private void showNewsContent(NewsEntity newsEntity,String category) {
        myTitle.setText(newsEntity.getNews_Title());
        myNote.setText(newsEntity.getNews_Note());

        if (category.equals(newsListEntity.getSAUNewspaperPage())){
            String str = newsEntity.getNews_Part_One();
            myContent.setText(newsEntity.getNews_Part_One());
            myWebView.loadUrl(newsEntity.getNews_Part_One());
        } else {
            myContent.setText(newsEntity.getNews_Part_One());
        }
    }

    private String getCategory(String myCategory) {
        String[] strs = myCategory.trim().split("/");
        return "/"+strs[1]+"/";
    }


}
