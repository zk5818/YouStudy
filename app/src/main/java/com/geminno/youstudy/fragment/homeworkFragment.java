package com.geminno.youstudy.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;

/**
 * Created by KeryZhang on 6/24/2016.
 */
public class homeworkFragment extends ComomFragment {


    private RadioGroup rg_menu;

    @Override
    public View initView(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.homework,null);
        rg_menu = ((RadioGroup) view.findViewById(R.id.rg_menu));
        return view;
    }

    @Override
    public void initData() {

    }
}
