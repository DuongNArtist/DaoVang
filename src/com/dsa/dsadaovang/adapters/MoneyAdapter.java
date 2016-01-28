package com.dsa.dsadaovang.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.engines.GameUtility;

public class MoneyAdapter extends ArrayAdapter<MoneyModel> {

    private LayoutInflater mLayoutInflater;
    private int mColorText;
    private int mColorMe;

    public MoneyAdapter(Context context, ArrayList<MoneyModel> moneyModels) {
        super(context, 0, moneyModels);
        mLayoutInflater = LayoutInflater.from(context);
        mColorText = context.getResources().getColor(R.color.text);
        mColorMe = context.getResources().getColor(R.color.me);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_score,
                    parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
        viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tvLevel = (TextView) convertView.findViewById(R.id.tv_level);
        viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
        MoneyModel money = getItem(position);
        if (money.getMac().equals(GameUtility.getMacAddress())) {
            viewHolder.tvId.setTextColor(mColorMe);
            viewHolder.tvName.setTextColor(mColorMe);
            viewHolder.tvMoney.setTextColor(mColorMe);
            viewHolder.tvLevel.setTextColor(mColorMe);
        } else {
            viewHolder.tvId.setTextColor(mColorText);
            viewHolder.tvName.setTextColor(mColorText);
            viewHolder.tvMoney.setTextColor(mColorText);
            viewHolder.tvLevel.setTextColor(mColorText);
        }
        viewHolder.tvId.setText(money.getId());
        viewHolder.tvName.setText(money.getName());
        viewHolder.tvLevel.setText(money.getLevel());
        viewHolder.tvMoney.setText(money.getMoney() + " $");
        return convertView;
    }

    private class ViewHolder {
        public TextView tvId;
        public TextView tvName;
        public TextView tvLevel;
        public TextView tvMoney;
    }
}
