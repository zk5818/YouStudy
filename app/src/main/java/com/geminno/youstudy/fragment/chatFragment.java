package com.geminno.youstudy.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geminno.youstudy.comom.ComomFragment;

/**
 * Created by KeryZhang on 6/24/2016.
 */
public class chatFragment extends ComomFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        TextView tv = new TextView(getContext());
        tv.setText("333333333333");
        return tv;
    }

    @Override
    public void initData() {

    }
}
