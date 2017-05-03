package com.danniel.danielchang.sauweb01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.danniel.danielchang.sauweb01.R;
import com.danniel.danielchang.sauweb01.presenter.GetNewsList;


/**
 * 沈航校报
 * 创建人：daniel
 * 创建日期：2017/04/03
 * 修改日期：2017/04/09
 */

public class SAUNewsPaperFragment extends Fragment {
    public static final String net_url = "http://news.sau.edu.cn/SAUNewspaper";
    public static String news_category = "/SAUNewspaper/";
    View view;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_simple,container,false);
        listView = (ListView) view.findViewById(R.id.id_simple_listView);
        new GetNewsList(view,net_url,news_category,listView);
        return view;
    }

}

