package com.geminno.youstudy.fragment.communication_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geminno.youstudy.comom.ComomFragment;

/**
 * Created by KeryZhang on 6/27/2016.
 */
public class TeachercmmFragment extends ComomFragment {
    @Override
    public View initView(LayoutInflater inflater) {
        TextView tv=new TextView(getActivity());
        tv.setText("33333333");
        return tv;
    }

    @Override
    public void initData() {

    }
}
