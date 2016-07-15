package com.geminno.youstudy.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geminno.youstudy.comom.ComomFragment;

/**
 * Created by KeryZhang on 6/24/2016.
 */
public class settingFragment extends ComomFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        TextView tv = new TextView(getContext());
        tv.setText("44444444444444");
        return tv;
    }

    @Override
    public void initData() {

    }
}
