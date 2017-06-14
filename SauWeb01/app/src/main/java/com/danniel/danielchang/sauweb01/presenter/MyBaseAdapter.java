package com.danniel.danielchang.sauweb01.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.danniel.danielchang.sauweb01.R;

import java.util.List;

/**
 * Created by danielchang on 2017/5/16.
 * NOT USED
 */

public class MyBaseAdapter extends BaseAdapter {

    private List<SimpleNewsItemBean> newsList;
    private LayoutInflater inflater;

    public MyBaseAdapter(Context context, List<SimpleNewsItemBean> newsList){
        this.newsList = newsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            inflater.inflate(R.layout.list_style_simple_news,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.id_simple_list_style_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleNewsItemBean itemBean = newsList.get(position);
        viewHolder.textView.setText(itemBean.getItemTitle());
        return convertView;
    }

    /**
     * 避免重复操作findViewById()所产生的资源浪费
     * 使用ViewHolder()封装该操作
     */
    class ViewHolder{
        public TextView textView;
    }


}
