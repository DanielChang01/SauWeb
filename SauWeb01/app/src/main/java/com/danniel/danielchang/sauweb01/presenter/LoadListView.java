package com.danniel.danielchang.sauweb01.presenter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.danniel.danielchang.sauweb01.R;

/**
 * Created by danielchang on 2017/5/14.
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener{

    View footer;//底部布局
    int totalItemCount; //item总量
    int lastItemCount; //当前显示最后一个item的位置
    boolean isLoading = false; //正在加载
    LoadListView.ILoadListener iLoadListener;

    public LoadListView(Context context) {
        super(context);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 添加底部加载提示到布局listView
     * @param context
     */
    public void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.item_footer,null);
        //初始默认不显示
        footer.findViewById(R.id.id_list_bottom_progress_linear).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (this.totalItemCount == this.lastItemCount
                && scrollState == SCROLL_STATE_IDLE){ //如果当前显示的总数和最后一个显示的位置相等
            if (!isLoading){
                isLoading = true;
                footer.findViewById(R.id.id_list_bottom_progress_linear).setVisibility(View.VISIBLE);
                iLoadListener.onLoad();

            }


        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastItemCount = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    public void setInterface(LoadListView.ILoadListener iLoadListener){
        this.iLoadListener = iLoadListener;

    }

    //加载完成
    public void loadComplete(){
        isLoading = false;
        footer.findViewById(R.id.id_list_bottom_progress_linear).setVisibility(View.VISIBLE);
    }

    //加载更多数据的回调接口
    public interface ILoadListener{

        public void onLoad();
    }
}
