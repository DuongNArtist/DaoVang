package com.dsa.dsadaovang.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.adapters.GuideAdapter;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

public class InstructionsDialog extends BaseDialog {

    private Button mbtClose;
    private ListView mlvList;
    private TextView mtvTitle;
    private LinearLayout mllTitle;

    public InstructionsDialog(Context context) {
        super(context);
        mtvTitle.setText(mContext.getResources().getString(
                R.string.btn_instruction));
        String[] helps = mContext.getResources().getStringArray(
                R.array.instructions);
        mlvList.setAdapter(new GuideAdapter(mContext, helps));
        mlvList.setOnItemClickListener(null);
        show();
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_list;
    }

    @Override
    public void bindView() {
        mbtClose = (Button) findViewById(R.id.bt_close);
        mbtClose.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GameUtility.mGameSound.play(GameSound.CLICK, 0);
                cancel();
            }
        });
        mtvTitle = (TextView) findViewById(R.id.tv_title);
        mlvList = (ListView) findViewById(R.id.lv_list);
        mllTitle = (LinearLayout) findViewById(R.id.ll_title);
        mllTitle.setVisibility(LinearLayout.GONE);
    }
}
