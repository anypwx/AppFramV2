package com.aframe.appframe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aframe.appframe.R;
import com.aframe.appframe.ui.bean.NewsListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description
 * Created by pwx on 2016/12/27.
 * Company MingThink
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsListBean> listBeen;
    private LayoutInflater mInflater;

    public NewsListAdapter(Context context, List<NewsListBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if(null != listBeen){
            return listBeen.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView){
            convertView = mInflater.inflate(R.layout.item_news,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(listBeen.get(position).getTitle());
        viewHolder.tv_time.setText(listBeen.get(position).getCtime());
        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
