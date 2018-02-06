package com.espressif.iot.esptouch.kingja;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.espressif.iot.esptouch.entiy.Tag;
import com.espressif.iot_esptouch_demo.R;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TagsAdapter extends BaseLvAdapter<Tag.DataBean> {
    public TagsAdapter(Context context, List<Tag.DataBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_tag, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_tagId.setText(String.valueOf(list.get(position).getTagId()));
        viewHolder.tv_tagStatus.setText("[ " + String.valueOf(list.get(position).getStatus()) + " ]");
        viewHolder.tv_tagStatus.setTextColor(list.get(position).getStatus() == 0 ? context.getResources().getColor(R
                .color.c_green) :
                context.getResources().getColor(R.color.c_green_light));
        return convertView;
    }

    public void setStatus(int position, int status) {
        list.get(position).setStatus(status);
        notifyDataSetChanged();
    }

    public void addTag(int tagId) {
        Tag.DataBean tag = new Tag.DataBean();
        tag.setTagId(tagId);
        list.add(0, tag);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public final View root;
        private final TextView tv_tagId;
        private final TextView tv_tagStatus;

        public ViewHolder(View root) {
            this.root = root;
            tv_tagId = root.findViewById(R.id.tv_tagId);
            tv_tagStatus = root.findViewById(R.id.tv_tagStation);
        }
    }
}
