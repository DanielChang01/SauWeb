package com.danniel.danielchang.sauweb01.entities;


/**
 * Created by Daniel on 2017/4/15.
 * Function:
 * 1.provide the parameters for need
 */

public class NewsListEntity {

    /**
     * 导航，category
     */
    private String basePage = "http://news.sau.edu.cn";  //沈航新闻网
    private String fisrtPage = "/";  //首页
    private String shnewsPage = "/shnews/";//沈航要闻
    private String noticePage = "/notice/";//通知公告
    private String teachingPage = "/teaching/";//教学科研
    private String learningPage = "/learning/";//学术信息
    private String highPage = "/high/";   //高教视点
    private String hrPage = "/hr/";     //招生就业
    private String mediaPage = "/media/";  //媒体沈航
    private String SAUNewspaperPage = "/SAUNewspaper/";//沈航校报
    private String videoPage = "/video/";  //视频新闻
    private String figurePage = "/figure/"; //沈航人物
    private String newsPage = "/news/";   //新闻动态
    private String internationalPage = "/International/";//国际合作
    private String alumnaPage = "/alumna/"; //校友风采
    private String schoolPage = "/school/"; //菁菁校园

    private String pageListBaseNum = "list_";
    private String newsTitleContent = ".title h1";  //新闻标题
    private String newsTitleNote = ".note"; //新闻信息栏
    private String newsRealContent = ".content"; //新闻内容

    private int count_Down = 3000;

    /**
     * 导航栏图标
     */
    private String banner_Pic1 = "http://news.sau.edu.cn/uploads/userup/2015/b.jpg";
    private String banner_Pic2 = "http://news.sau.edu.cn/uploads/allimg/160601/1-1606011I25J28.jpg";
    private String banner_Pic3 = "http://news.sau.edu.cn/uploads/allimg/150618/1-15061QUTS09.jpg";
    private String banner_Pic4 = "http://news.sau.edu.cn/uploads/allimg/160606/1-16060610092TH.jpg";
    private String banner_Pic5 = "http://news.sau.edu.cn/uploads/allimg/170513/1-1F5131944164a.jpg";
    private String banner_Pic6 = "http://news.sau.edu.cn/uploads/allimg/161230/1-161230224G61V.jpg";
    /**
     * 新闻栏目图标
     */
    private String hr_note_pic = "http://news.sau.edu.cn/images/index/ad/AD_iDlistA_1.gif";
    private String international_note_pic = "http://news.sau.edu.cn/images/index/ad/AD_iDlistA_2.gif";
    private String learning_note_pic = "http://news.sau.edu.cn/images/index/ad/AD_iDlistA_4.gif";
    private String school_note_pic = "http://news.sau.edu.cn/images/index/ad/AD_iDlistA_5.gif";
    private String alumna_note_pic = "http://news.sau.edu.cn/images/index/ad/AD_iDlistA_3.gif";


    /**
     * Document分类
     */

    /**
     * 试用范围
     * shnews
     * notice
     * teaching
     * news
     * media
     * SAUNewspaper
     * video
     */
    private String getList_li = ".list li a";
    private String getVideoContent = ".content object embed";
    private String getNewsPaperContent = ".content a";

    /**
     * 试用范围
     * hr
     * international
     * learning
     * school
     * alumna
     */
    private String getIDlist_li_a = ".iDlistA li a";

    /**
     * 试用范围
     * figure
     */
    private String getIFigureTitle = ".iFigure h3 a";

    /**
     * 试用范围
     * high
     */
    private String getIElist_li_a = ".iElist li a";

    /**
     * 试用范围
     * shnews
     * notice
     */
    private String getHeadlines = ".Headlines h2 a";
    /**
     * 试用范围
     * figure
     */
    private String getUpdateFigure = ".iFigure li a";
    private String getUpdateHeadline = ".info a";

    /**
     * 普通新闻页面的列表获取
     */
    private String getCommonNewsList = ".NewList li a";
    /**
     * 普通新闻页的页数加载
     */
    private String getPageList = ".pagelist li";

    private String getNewsPic = ".pic a";
    private String getAdLong = ".ad_long img";
    private String getNewsCommonPic = ".content img";

    public String getGetList_li() {
        return getList_li;
    }

    public String getGetPageList() {
        return getPageList;
    }

    public String getGetIFigureTitle() {
        return getIFigureTitle;
    }

    public String getGetIDlist_li_a() {
        return getIDlist_li_a;
    }

    public String getGetIElist_li_a() {
        return getIElist_li_a;
    }

    public String getGetCommonNewsList() {
        return getCommonNewsList;
    }

    public String getGetHeadlines() {
        return getHeadlines;
    }

    public String getBasePage() {
        return basePage;
    }

    public String getGetUpdateFigure() {
        return getUpdateFigure;
    }

    public String getGetUpdateHeadline() {
        return getUpdateHeadline;
    }

    public String getGetAdLong() {
        return getAdLong;
    }

    public String getGetNewsPic() {
        return getNewsPic;
    }

    public String getFisrtPage() {
        return fisrtPage;
    }

    public String getShnewsPage() {
        return shnewsPage;
    }

    public String getNoticePage() {
        return noticePage;
    }

    public String getTeachingPage() {
        return teachingPage;
    }

    public String getLearningPage() {
        return learningPage;
    }

    public String getHighPage() {
        return highPage;
    }

    public String getHrPage() {
        return hrPage;
    }

    public String getMediaPage() {
        return mediaPage;
    }

    public String getSAUNewspaperPage() {
        return SAUNewspaperPage;
    }

    public String getVideoPage() {
        return videoPage;
    }

    public String getFigurePage() {
        return figurePage;
    }

    public String getNewsPage() {
        return newsPage;
    }

    public String getInternationalPage() {
        return internationalPage;
    }

    public String getAlumnaPage() {
        return alumnaPage;
    }

    public String getSchoolPage() {
        return schoolPage;
    }

    public String getBanner_Pic1() {
        return banner_Pic1;
    }

    public String getBanner_Pic4() {
        return banner_Pic4;
    }

    public String getBanner_Pic3() {
        return banner_Pic3;
    }

    public String getBanner_Pic2() {
        return banner_Pic2;
    }

    public String getBanner_Pic5() {
        return banner_Pic5;
    }

    public String getBanner_Pic6() {
        return banner_Pic6;
    }

    public String getHr_note_pic() {
        return hr_note_pic;
    }

    public String getAlumna_note_pic() {
        return alumna_note_pic;
    }

    public String getSchool_note_pic() {
        return school_note_pic;
    }

    public String getLearning_note_pic() {
        return learning_note_pic;
    }

    public String getInternational_note_pic() {
        return international_note_pic;
    }

    public String getPageListBaseNum() {
        return pageListBaseNum;
    }

    public String getNewsTitleContent() {
        return newsTitleContent;
    }

    public String getNewsTitleNote() {
        return newsTitleNote;
    }

    public String getNewsRealContent() {
        return newsRealContent;
    }

    public String getGetVideoContent() {
        return getVideoContent;
    }

    public String getGetNewsPaperContent() {
        return getNewsPaperContent;
    }

    public String getGetNewsCommonPic() {
        return getNewsCommonPic;
    }

    public int getCount_Down() {
        return count_Down;
    }
}

