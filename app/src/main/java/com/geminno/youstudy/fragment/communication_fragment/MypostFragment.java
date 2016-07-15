package com.geminno.youstudy.fragment.communication_fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geminno.youstudy.HomepageActivity;
import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;
import com.geminno.youstudy.fragment.communicateFragment;
import com.geminno.youstudy.pojo.CommentBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeryZhang on 6/27/2016.
 */
public class MypostFragment extends ComomFragment implements View.OnClickListener {
    private ListView lv_mypost_fragment;
    private List<CommentBean.Comment> commentList = null;
    private ImageButton ib_pro_communicationfragment;
    private ImageButton ib_add_mypost;
    private List<String> listRight = new ArrayList<String>();
    private PopupWindow popRight;
    private View layoutRight;
    private ListView menulistRight;
    PopupWindow popupwindow;
    private ListView listview;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_mypost, null);
        lv_mypost_fragment = ((ListView) view.findViewById(R.id.lv_mypost_fragment));
        ib_pro_communicationfragment = ((ImageButton) view.findViewById(R.id.ib_pro_communicationfragment));
        ib_add_mypost = ((ImageButton) view.findViewById(R.id.ib_add_mypost));
        ib_add_mypost.setOnClickListener(this);

        ib_pro_communicationfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new communicateFragment());
            }
        });


        return view;
    }

    @Override
    public void initData() {
        listRight.add("类 型:");
        listRight.add("提问贴");
        listRight.add("动态帖");
        listview = new ListView(getActivity());

        listview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
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
                View view = View.inflate(getContext(), R.layout.pop_menulist, null);
                TextView list_item_tv = ((TextView) view.findViewById(R.id.list_item_tv));
                list_item_tv.setText(listRight.get(position) + "");
                return view;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        popupwindow.dismiss();
                        switchFragment(new AddmypostquestionFragment());
                        break;
                    case 2:
                        popupwindow.dismiss();
                        switchFragment(new AddParentscmmFragment());
                        break;
                    default:
                        break;
                }
            }
        });

        HttpUtils http = new HttpUtils();
        commentList = new ArrayList<CommentBean.Comment>();

        http.send(
                HttpRequest.HttpMethod.GET,
                "http://192.168.30.51:8080/youStudySys/getmypostdata",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        CommentBean bean = gson.fromJson(responseInfo.result, CommentBean.class);

                     final  List<CommentBean.Comment> beans_mypost = bean.data;
//                        CommentBean.Comment comment = null;
//                        for (CommentBean.Comment mypost : beans_mypost) {
//                            comment = new CommentBean.Comment();
//                            comment.setCommentId(mypost.commentId);
//                            comment.setUserId(mypost.userId);
//                            comment.setUserName(mypost.userName);
//                            comment.setPid(mypost.pid);
//                            comment.setDate(mypost.date);
//                            comment.setLikes(mypost.likes);
//
//                            comment.setCommentNumber(mypost.commentNumber);
//               comment.setContentTitle(mypost.cont);
//                            comment.setContent(mypost.c);
//                            comment.setCommentType(mypost.commentType);
//                            comment.setTableName(mypost.tableName);
//                            commentList.add(comment);
//                        }

                        lv_mypost_fragment.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return beans_mypost.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return position;
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = View.inflate(getContext(), R.layout.mypost_form, null);

                                TextView tv_mypost_senperson = ((TextView) view.findViewById(R.id.tv_mypost_senperson));
                                TextView tv_mypost_date = ((TextView) view.findViewById(R.id.tv_mypost_date));
                                TextView tv_mypost_context = ((TextView) view.findViewById(R.id.tv_mypost_context));
                                TextView tv_mypost_pinglunshu = ((TextView) view.findViewById(R.id.tv_mypost_pinglunshu));
                                TextView tv_mypost_pinglun = ((TextView) view.findViewById(R.id.tv_mypost_pinglun));
                                inittext(tv_mypost_pinglun);
                                CommentBean.Comment totalp = beans_mypost.get(position);
                                tv_mypost_senperson.setText(totalp.getUserName() + "");
                                tv_mypost_date.setText(totalp.getDate());
                                tv_mypost_context.setText(totalp.getContent());
                                tv_mypost_pinglunshu.setText(totalp.getCommentNumber() + "");

                                return view;
                            }
                        });


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println(s);
                    }
                }
        );
        lv_mypost_fragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
//                bundle.putLong("position", position);
                bundle.putSerializable("tableName", commentList.get(position).getTableName());
                bundle.putSerializable("commentId", commentList.get(position).getCommentId());

                bundle.putSerializable("comment", (Serializable) commentList.get(position));
                MypostDetailFragment myPostDetails_fragment = new MypostDetailFragment();
                myPostDetails_fragment.setArguments(bundle);
                switchFragment(myPostDetails_fragment);
            }
        });
    }

    public void inittext(TextView... textView) {
        for (TextView tv : textView) {
            if (tv != null) {
                if (getActivity() instanceof HomepageActivity)
                    ((HomepageActivity) getActivity()).inittext(tv);

            }
        }
    }


    public void switchFragment(Fragment fragment) {
        if (fragment != null) {
            if (getActivity() instanceof HomepageActivity) {
                ((HomepageActivity) getActivity()).switchFragment(fragment);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_add_mypost:
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                } else {
                    popupwindow = new PopupWindow(listview, 250, 300);

                    //设置popupwindow内部的控件可以获得焦点
                    popupwindow.setFocusable(true);
                    //设置背景
                    popupwindow.setBackgroundDrawable(new BitmapDrawable());
                    //设置控件外部可以点击
                    popupwindow.setOutsideTouchable(true);

                    popupwindow.showAsDropDown(v);
                }
                break;
        }
    }
}