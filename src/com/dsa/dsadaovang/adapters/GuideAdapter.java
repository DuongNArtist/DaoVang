package com.dsa.dsadaovang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsa.dsadaovang.R;

public class GuideAdapter extends ArrayAdapter<String> {

    public static final int NUMBER_OF_ITEMS = 13;

    private LayoutInflater mLayoutInflater;

    public GuideAdapter(Context context, String[] strings) {
        super(context, 0, strings);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_item, parent,
                    false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
        viewHolder.tvHelp = (TextView) convertView.findViewById(R.id.tv_help);
        viewHolder.ivIcon.setImageResource(R.drawable.and_item00 + position);
        viewHolder.tvHelp.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder {
        public ImageView ivIcon;
        public TextView tvHelp;
    }
}
