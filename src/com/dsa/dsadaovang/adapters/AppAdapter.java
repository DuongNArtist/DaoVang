package com.dsa.dsadaovang.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.activities.MainActivity;

public class AppAdapter extends ArrayAdapter<AppModel> {

    private LayoutInflater mLayoutInflater;

    public AppAdapter(Context context, ArrayList<AppModel> appModels) {
        super(context, 0, appModels);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_item, parent,
                    false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AppModel appModel = getItem(position);
        viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
        viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_help);
        viewHolder.tvName.setText(appModel.getName());
        MainActivity.mImageLoader.displayImage(appModel.getImage(),
                viewHolder.ivIcon, MainActivity.mDisplayImageOptions);
        return convertView;
    }

    private class ViewHolder {
        public ImageView ivIcon;
        public TextView tvName;
    }
}
