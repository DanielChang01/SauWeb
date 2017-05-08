package com.danniel.danielchang.sauweb01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.danniel.danielchang.sauweb01.database.DBOpenHelper;
import com.danniel.danielchang.sauweb01.entities.NewsListEntity;

/**
 * Created by Daniel on 2017/4/29.
 */

public class ContentActivity extends Activity {

    WebView webView;
    String myUrl = null;
    NewsListEntity newsListEntity = null;
    String js_Function = "function getClass(parent,sClass)\n" +
            "{\n" +
            "    var aEle=parent.getElementsByTagName('div');\n" +
            "    var aResult=[];\n" +
            "    var i=0;\n" +
            "    for(i<0; i<aEle.length; i++) {\n" +
            "        if(aEle[i].className==sClass)\n" +
            "        {\n" +
            "            aResult.push(aEle[i]);\n" +
            "        }\n" +
            "    };\n" +
            "    return aResult;\n" +
            "}";

//    String js_FunctionHide = "function hideOther() \n" +
//            "{\n" +
//            "    getClass(document,'sid-left')[0].style.display='none';\n" +
//            "    document.getElementById('mainNav').style.display='none';\n" +
//            "    document.getElementById('header').style.display='none';\n" +
//            "    document.getElementById('footer').style.display='none';\n" +
//            "}";

    String js_FunctionHide = "function hideOther() \n" +
            "{\n" +
            "    getClass(document,'sid-left')[0].style.display='none';\n" +
            "}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        webView = (WebView) findViewById(R.id.content_WebView);
        newsListEntity = new NewsListEntity();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myUrl = newsListEntity.getBasePage()+bundle.getString(DBOpenHelper.TB_NEWS_URL);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setSupportZoom(true); //支持缩放
        webSettings.setPluginState(WebSettings.PluginState.ON);//对任何对象都会加载一个（可能不存在的）插件来处理内容
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                if(url!=null&&url.contains("http")) {
//
//                    view.loadUrl(js_Function);
//
//                    view.loadUrl(js_FunctionHide);
//
//                    view.loadUrl("javascript:hideOther();");
//                }

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });

        webView.loadUrl(myUrl);



    }
}

