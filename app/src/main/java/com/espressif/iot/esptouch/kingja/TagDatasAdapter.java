package com.espressif.iot.esptouch.kingja;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.espressif.iot.esptouch.entiy.TagData;
import com.espressif.iot_esptouch_demo.R;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TagDatasAdapter extends BaseLvAdapter<TagData.DataBean> {
    public TagDatasAdapter(Context context, List<TagData.DataBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_tag_data, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_date.setText(String.valueOf(list.get(position).getTagDate()));
        viewHolder.tv_tagContent.setText(String.valueOf(list.get(position).getContent()));
        viewHolder.tv_tagId.setText(String.valueOf(list.get(position).getTagId()));
        viewHolder.tv_tagStation.setText(String.valueOf(list.get(position).getBaseStation()));
        return convertView;
    }



    public class ViewHolder {
        final View root;
        private TextView tv_tagId;
        private TextView tv_tagStation;
        private TextView tv_date;
        private TextView tv_tagContent;

        ViewHolder(View root) {
            this.root = root;
             tv_tagId = root.findViewById(R.id.tv_tagId);
             tv_tagStation = root.findViewById(R.id.tv_tagStation);
             tv_date = root.findViewById(R.id.tv_date);
             tv_tagContent = root.findViewById(R.id.tv_tagContent);
        }
    }
}
