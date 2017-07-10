package com.aframe.appframe.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.aframe.appframe.R;
import com.aframe.appframe.YConstance;
import com.aframe.appframe.mvp.presenter.IWeatherPresenter;
import com.aframe.appframe.mvp.presenter.WeatherPresenterImpl;
import com.aframe.appframe.mvp.view.DataResultView;
import com.aframe.appframe.tools.listener.AutoLoadListener;
import com.aframe.appframe.ui.adapter.NewsListAdapter;
import com.aframe.appframe.ui.bean.ListNewBean;
import com.aframe.appframe.ui.bean.NewsListBean;
import com.aframe.appframe.ui.bean.WeatherBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description
 * Created by pwx on 2016/12/27.
 * Company MingThink
 */

public class ListLoadMoreActivity extends Activity{
    @BindView(R.id.ll_list)
    ListView ll_list;

    int num = 10;
    int page = 1;
    private List<NewsListBean> listBeen = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    private IWeatherPresenter<ListNewBean> iWeatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmore);
        ButterKnife.bind(this);
        initListSlide();
        getNewsList(num+"",page+"");
        newsListAdapter = new NewsListAdapter(this,listBeen);
        ll_list.setAdapter(newsListAdapter);
    }

    private void initListSlide(){
        ll_list.setOnScrollListener(new AutoLoadListener(callBack));
    }

    private void getNewsList(String num,String page){
        iWeatherPresenter = new WeatherPresenterImpl<>(new DataResultView<ListNewBean>() {
            @Override
            public void success(ListNewBean d) {
                listBeen.addAll(d.getNewslist());
                newsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void fail(String s, int erCode) {

            }

            @Override
            public void progressData(int p) {

            }
        });

        YConstance.paramMap.clear();
        YConstance.paramMap.put(YConstance.ACTION,YConstance.NEWS_ACTION);
        YConstance.paramMap.put("num",num);
        YConstance.paramMap.put("page",page);
        iWeatherPresenter.getWeather(YConstance.paramMap,true,ListNewBean.class);
    }

    AutoLoadListener.AutoLoadCallBack callBack = new AutoLoadListener.AutoLoadCallBack() {
        @Override
        public void execute() {
            Log.i("pwx","加载数据...");
            page++;
            getNewsList(num+"",page+"");
        }
    };
}
