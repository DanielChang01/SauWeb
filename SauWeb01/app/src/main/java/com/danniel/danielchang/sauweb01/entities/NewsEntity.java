package com.danniel.danielchang.sauweb01.entities;


/**
 * Created by Daniel on 2017/4/14.
 * created for news shown
 * 1，标题
 * 2，标记
 * 3，段落s：显示方式待更新
 * 4，图片s：
 */

public class NewsEntity {

    private String news_Title;
    private String news_Note;
    private String news_Part_One;
    private String news_Part_Two;
    private String news_Pic_One;
    private String news_Pic_Two;

    public NewsEntity(){}

    public String getNews_Title() {
        return news_Title;
    }

    public void setNews_Title(String news_Title) {
        this.news_Title = news_Title;
    }

    public String getNews_Note() {
        return news_Note;
    }

    public void setNews_Note(String news_Note) {
        this.news_Note = news_Note;
    }

    public String getNews_Part_One() {
        return news_Part_One;
    }

    public void setNews_Part_One(String news_Part_One) {
        this.news_Part_One = news_Part_One;
    }

    public String getNews_Part_Two() {
        return news_Part_Two;
    }

    public void setNews_Part_Two(String news_Part_Two) {
        this.news_Part_Two = news_Part_Two;
    }

    public String getNews_Pic_One() {
        return news_Pic_One;
    }

    public void setNews_Pic_One(String news_Pic_One) {
        this.news_Pic_One = news_Pic_One;
    }

    public String getNews_Pic_Two() {
        return news_Pic_Two;
    }

    public void setNews_Pic_Two(String news_Pic_Two) {
        this.news_Pic_Two = news_Pic_Two;
    }
}
