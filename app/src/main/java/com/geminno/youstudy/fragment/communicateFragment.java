package com.geminno.youstudy.fragment;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geminno.youstudy.HomepageActivity;

import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;
import com.geminno.youstudy.fragment.communication_fragment.MypostFragment;
import com.geminno.youstudy.fragment.communication_fragment.ParentscmmFragment;
import com.geminno.youstudy.fragment.communication_fragment.QuestionFragment;
import com.geminno.youstudy.fragment.communication_fragment.TeachercmmFragment;
import com.geminno.youstudy.pojo.CommentBean;
import com.geminno.youstudy.pojo.QuotesBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeryZhang on 6/24/2016.
 */
public class communicateFragment extends ComomFragment {
    private ListView contentList;
    private Message msg;
    private Button cmm_bt1;
    private Button cmm_bt2;
    private Button cmm_bt3;
    private Button cmm_bt4;
    private ImageButton ib_pro_homepage;
    private Fragment fragment = null;
    private ImageView communication_images;
    private ViewPager vp_change_items;
    List<QuotesBean.Quotes> quotesList = new ArrayList<QuotesBean.Quotes>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    vp_change_items.setCurrentItem(vp_change_items.getCurrentItem() + 1);

                    switch ((vp_change_items.getCurrentItem()) % 4) {
                        case 0:
                            cmm_bt1.setBackgroundColor(Color.parseColor("#A020F0"));
                            cmm_bt4.setBackgroundColor(Color.parseColor("#d6d7d7"));
                            break;
                        case 1:
                            cmm_bt2.setBackgroundColor(Color.parseColor("#A020F0"));
                            cmm_bt1.setBackgroundColor(Color.parseColor("#d6d7d7"));
                            break;
                        case 2:

                            cmm_bt3.setBackgroundColor(Color.parseColor("#A020F0"));
                            cmm_bt2.setBackgroundColor(Color.parseColor("#d6d7d7"));
                            break;
                        case 3:
                            cmm_bt3.setBackgroundColor(Color.parseColor("#d6d7d7"));
                            cmm_bt4.setBackgroundColor(Color.parseColor("#A020F0"));
                            break;
                    }


                    msg = handler.obtainMessage();
                    handler.sendMessageDelayed(msg, 5000);

                    break;
            }
        }
    };

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.communication, null);
        vp_change_items = ((ViewPager) view.findViewById(R.id.vp_change_items));
        cmm_bt1 = ((Button) view.findViewById(R.id.cmm_bt1));
        cmm_bt2 = ((Button) view.findViewById(R.id.cmm_bt2));
        cmm_bt3 = ((Button) view.findViewById(R.id.cmm_bt3));
        cmm_bt4 = ((Button) view.findViewById(R.id.cmm_bt4));
        contentList = (ListView) view.findViewById(R.id.contentList);
        ib_pro_homepage = (ImageButton) view.findViewById(R.id.ib_pro_homepage);
        cmm_bt1.setBackgroundColor(Color.parseColor("#A020F0"));


        return view;
    }

    public void initData() {


        HttpUtils http = new HttpUtils();
        http.send(
                HttpRequest.HttpMethod.GET,
                "http://192.168.30.51:8080/youStudySys/getfamousquotes",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        QuotesBean bean = gson.fromJson(responseInfo.result, QuotesBean.class);
                        quotesList = bean.data_quotes;

                        contentList.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return quotesList.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return null;
                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {


                                //打气筒inflate
                                View view = View.inflate(getContext(), R.layout.listview_communication, null);
                                TextView tv_title = ((TextView) view.findViewById(R.id.tv_title));
                                TextView tv_context = ((TextView) view.findViewById(R.id.tv_context));
                                TextView tv_author = ((TextView) view.findViewById(R.id.tv_author));


                                //获取当前位置信息
                                QuotesBean.Quotes news = quotesList.get(position);
//                                System.out.println(news.getContentTitle()+"++++++++++++++++++++++++++");

                                tv_title.setText(news.getUserName());
                                tv_context.setText(news.getContent());
                                tv_author.setText(news.getContentTitle());
                                return view;


                            }


                        });
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                }
        );
        final int[] imgsrc = new int[]{R.mipmap.communication_img1,
                R.mipmap.communication_img2,
                R.mipmap.communication_img3,
                R.mipmap.communication_img4};

        vp_change_items.setAdapter(new MyPageAdapter(imgsrc));
//        vp_change_items.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int currentPage = vp_change_items.getCurrentItem();
//
//                switch (currentPage%4) {
//                    case 0:
//                        fragment = new QuestionFragment();
//                        break;
//                    case 1:
//                        fragment = new MypostFragment();
//                        break;
//                    case 2:
//                        fragment = new ParentscmmFragment();
//                        break;
//                    case 3:
//                        fragment = new TeachercmmFragment();
//                        break;
//                }
//                switchFragment(fragment);
//                return true;
//            }
//        });
        cmm_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new QuestionFragment());
            }
        });
        cmm_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new MypostFragment());
            }
        });
        cmm_bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new ParentscmmFragment());
            }
        });
        cmm_bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new TeachercmmFragment());
            }
        });
        //从数据库获取数据


        ib_pro_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new homeworkFragment());
            }
        });

    }

    public class MyPageAdapter extends PagerAdapter {
        private int[] imgsrc;

        public MyPageAdapter(int[] imgsrc) {
            this.imgsrc = imgsrc;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getContext(), R.layout.communication_image, null);
            communication_images = ((ImageView) view.findViewById(R.id.communication_images));

            //   communication_images.setScaleType(ImageView.ScaleType.CENTER_CROP);
            communication_images.setBackgroundResource(imgsrc[position % 4]);

            msg = handler.obtainMessage();
            msg.what = 1;
            handler.sendMessageDelayed(msg, 5000);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    public void switchFragment(Fragment fragment) {
        if (fragment != null) {

            if (getActivity() instanceof HomepageActivity) {
                ((HomepageActivity) getActivity()).switchFragment(fragment);
            }
        }
    }
}
