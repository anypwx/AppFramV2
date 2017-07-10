package com.aframe.appframe.ui.bean;

import java.util.List;

/**
 * Description
 * Created by pwx on 2016/12/27.
 * Company MingThink
 */

public class ListNewBean {
    //这里的 newslist 一定要和 BaseNewsBean newslist 一样，这样才能拿到数据进行list 化
    private List<NewsListBean> newslist;

    public List<NewsListBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewsListBean> newslist) {
        this.newslist = newslist;
    }
}
