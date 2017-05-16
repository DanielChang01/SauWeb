package com.danniel.danielchang.sauweb01.presenter;

/**
 * Created by danielchang on 2017/5/16.
 * used for news List shown
 */

public class SimpleNewsItemBean {
    private String itemTitle;
    private String itemURL;

    public SimpleNewsItemBean(String itemTitle, String itemURL){
        this.itemTitle = itemTitle;
        this.itemURL = itemURL;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemURL() {
        return itemURL;
    }
}
