package com.danniel.danielchang.sauweb01.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.danniel.danielchang.sauweb01.R;
import com.danniel.danielchang.sauweb01.presenter.GetNewsList;
import com.danniel.danielchang.sauweb01.presenter.LoadListView;
import com.danniel.danielchang.sauweb01.presenter.NetAsyncTaskForRefresh;

import java.util.List;
import java.util.Map;


/**
 * 招生就业
 * 创建人：daniel
 * 创建日期：2017/04/03
 * 修改日期：2017/04/09
 */

public class HRFragment extends Fragment implements LoadListView.ILoadListener{
    public static final String net_url = "http://news.sau.edu.cn/hr";
    public static String news_category = "/hr/";
    View view;
    LoadListView listView;
    int baseNum = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_simple,container,false);
        listView = (LoadListView) view.findViewById(R.id.id_simple_listView);

        listView.setInterface(this);
        new GetNewsList(view,net_url,news_category,listView);

        return view;
    }

    @Override
    public void onLoad() {

        //获取更多数据
        new NetAsyncTaskForRefresh().execute(net_url,baseNum++,news_category,getContext());

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //更新listView
//        showListView();
                new GetNewsList(view,net_url,news_category,listView);
                //通知listView加载完毕
                listView.loadComplete();
            }
        },3000);
    }

}

