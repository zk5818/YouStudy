package com.geminno.youstudy.fragment.communication_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.geminno.youstudy.HomepageActivity;
import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;
import com.geminno.youstudy.pojo.CommentBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeryZhang on 6/27/2016.
 */
public class MypostDetailFragment extends ComomFragment {
    private TextView tv_mypostdetail_senperson;
    private TextView mypostdetail_content;
    private TextView tv_mypost_pinlunshu;
    private TextView tv_myhistroypost_pinglun;
    private ListView lv_mypostdetails_answer;
    private int commentId;
    private String tableName;
    private CommentBean.Comment comment;

    private ImageButton ib_pro_mypostfragment;

    @Override
    public View initView(LayoutInflater inflater) {
        Bundle bundle = getArguments();
        commentId = (int) bundle.getLong("commentId");
        tableName = bundle.getString("tableName");
        comment = (CommentBean.Comment) bundle.getSerializable("comment");
        View view = inflater.inflate(R.layout.mypostdetails_fragment, null);

        tv_mypostdetail_senperson = ((TextView) view.findViewById(R.id.tv_mypostdetail_senperson));
        mypostdetail_content = ((TextView) view.findViewById(R.id.mypostdetail_content));
        tv_myhistroypost_pinglun = ((TextView) view.findViewById(R.id.tv_myhistroypost_pinglun));
        inittext(tv_myhistroypost_pinglun);
        tv_mypost_pinlunshu = ((TextView) view.findViewById(R.id.tv_mypost_pinlunshu));
        lv_mypostdetails_answer = ((ListView) view.findViewById(R.id.lv_mypostdetails_answer));

        ib_pro_mypostfragment = ((ImageButton) view.findViewById(R.id.ib_pro_mypostfragment));
        ib_pro_mypostfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new MypostFragment());
            }
        });

        return view;
    }

    @Override
    public void initData() {
        tv_mypostdetail_senperson.setText(comment.getUserName() + "");
        mypostdetail_content.setText(comment.getContent());
        tv_mypost_pinlunshu.setText(comment.getCommentNumber() + "");


        HttpUtils http = new HttpUtils();
        final List<CommentBean.Comment> commentList_post= new ArrayList<CommentBean.Comment>();
//        totalPostList_a = new ArrayList<TotalPost>();
        http.send(
                HttpRequest.HttpMethod.GET,
                "http://192.168.30.51:8080/youStudySys/getquestionsdata?commentId=" + commentId + "&tableName=" + tableName + "",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        CommentBean bean_comment = gson.fromJson(responseInfo.result, CommentBean.class);

                       List<CommentBean.Comment> beans_post = bean_comment.data;
//                        ArrayList<CommentBean.Content> beans_answer = bean.data_answer;
                        System.out.println(beans_post.size() + "=========================");
                        CommentBean.Comment comment;
                        for (CommentBean.Comment comment2 : beans_post) {
                            comment = new CommentBean.Comment();
                            comment.setCommentId(comment2.getCommentId());
//                            comment.setCommentId(comment2.userId);
//                            comment.setUserName(comment2.userName);
//                            comment.setPid(comment2.pid);
//                            comment.setDate(comment2.date);
//                            comment.setLikes(comment2.likes);
//                            comment.setDislikes(comment2.dislikes);
//                            comment.setCommentNumber(comment2.commentNumber);
//                            comment.setContent(comment2.content);
//                            comment.setCommentType(comment2.commentType);
//                            comment.setTableName(comment2.tableName);
                            commentList_post.add(comment);
                        }

                        lv_mypostdetails_answer.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return commentList_post.size();
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
                                View view = View.inflate(getContext(), R.layout.answer_form, null);

                                TextView tv_qd_answer_senperson = ((TextView) view.findViewById(R.id.tv_qd_answer_senperson1));
                                TextView tv_qd_answer_date = ((TextView) view.findViewById(R.id.tv_qd_answer_date));
                                TextView tv_qd_answer_context = ((TextView) view.findViewById(R.id.tv_qd_answer_context));

//                                TextView tv_qd_answer_pinglun = ((TextView) view.findViewById(R.id.tv_qd_answer_pinglun));
//                                inittext(tv_qd_answer_pinglun);
//
//                                TextView tv_qd_answer_pinglunshu = ((TextView) view.findViewById(R.id.tv_qd_answer_pinglunshu));
                                Button bt_qd_answer_dianzan = ((Button) view.findViewById(R.id.bt_qd_answer_dianzan));
//                                inittext(tv_qd_answer_dianzan);
//                                TextView tv_qd_answer_dianzanshu = ((TextView) view.findViewById(R.id.tv_qd_answer_dianzanshu));
//                                TextView tv_qd_answer_diszan = ((TextView) view.findViewById(R.id.tv_qd_answer_diszan));
//
//                                inittext(tv_qd_answer_diszan);
//                                TextView tv_qd_answer_diszanshu = ((TextView) view.findViewById(R.id.tv_qd_answer_diszanshu));

                                CommentBean.Comment tp_a = commentList_post.get(position);

                                tv_qd_answer_senperson.setText(tp_a.getUserName() + "");
                                tv_qd_answer_date.setText(tp_a.getDate().toString());
                                tv_qd_answer_context.setText(tp_a.getContent());
//                                tv_qd_answer_pinglunshu.setText(tp_a.getCommentNumber() + "");
//                                tv_qd_answer_dianzanshu.setText(tp_a.getLikes() + "");
//                                tv_qd_answer_diszanshu.setText(tp_a.getDislikes() + "");
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





    }


    public void switchFragment(Fragment fragment) {
        if (fragment != null) {
            if (getActivity() instanceof HomepageActivity) {
                ((HomepageActivity) getActivity()).switchFragment(fragment);
            }
        }
    }


    public void inittext(TextView textView) {
        if (textView != null) {
            if (getActivity() instanceof HomepageActivity)
                ((HomepageActivity) getActivity()).inittext(textView);

        }

    }
}
