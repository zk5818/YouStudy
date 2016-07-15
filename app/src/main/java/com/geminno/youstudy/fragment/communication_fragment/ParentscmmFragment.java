package com.geminno.youstudy.fragment.communication_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;

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
public class ParentscmmFragment extends ComomFragment {
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
//    private ListView lv_praentscmm;
//    private EditText et_praentscmm;
//    private TextView tv_search_praentscmm;
//    private List<TotalPost> totalPostList;
//    @Override
//    public View initView(LayoutInflater inflater) {
//        View view = inflater.inflate(R.layout.fragment_parentscmm, null);
//        lv_praentscmm = (ListView) view.findViewById(R.id.lv_praentscmm);
//        et_praentscmm = ((EditText) view.findViewById(R.id.et_praentscmm));
//        tv_search_praentscmm = ((TextView) view.findViewById(R.id.tv_search_praentscmm));
//        return view;
//    }
//
//    @Override
//    public void initData() {
//        HttpUtils http = new HttpUtils();
//        totalPostList = new ArrayList<TotalPost>();
//        http.send(
//                HttpRequest.HttpMethod.GET,
//                "http://10.40.8.4:8080/youStudy/gettotalposts",
//                new RequestCallBack<String>() {
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        Gson gson = new Gson();
//                        TotalPostBean bean = gson.fromJson(responseInfo.result, TotalPostBean.class);
//                        ArrayList<TotalPostBean.TotalPost> beans_question = bean.data_question;
//                        TotalPost totalPost;
//                        for (TotalPostBean.TotalPost totalPost1 : beans_question) {
//                            totalPost = new TotalPost();
//                            totalPost.setId(totalPost1.id);
//                            totalPost.setSenPerson(totalPost1.senPerson);
//                            totalPost.setDate(totalPost1.date);
//                            totalPost.setLikes(totalPost1.likes);
//                            totalPost.setDislikes(totalPost1.dislikes);
//                            totalPost.setPostType(totalPost1.postType);
//                            totalPost.setContent(totalPost1.content);
//                            totalPost.setCommentNumber(totalPost1.commentNumber);
//                            totalPost.setCheckPostComment(totalPost1.checkPostComment);
//                            totalPostList.add(totalPost);
//                        }
//
//                        lv_praentscmm.setAdapter(new BaseAdapter() {
//                            @Override
//                            public int getCount() {
//                                return totalPostList.size();
//                            }
//
//                            @Override
//                            public Object getItem(int position) {
//                                return position;
//                            }
//
//                            @Override
//                            public long getItemId(int position) {
//                                return position;
//                            }
//
//                            @Override
//                            public View getView(int position, View convertView, ViewGroup parent) {
//                                //打气筒inflate
//                                View view = View.inflate(getContext(), R.layout.question_form, null);
//
//                                TextView tv_question_id = ((TextView) view.findViewById(R.id.tv_question_id));
//                                TextView tv_question_date = ((TextView) view.findViewById(R.id.tv_question_date));
//                                TextView tv_question_context = ((TextView) view.findViewById(R.id.tv_question_context));
//                                TextView tv_question_pinglun = ((TextView) view.findViewById(R.id.tv_question_pinglun));
//
//
//                                //获取当前位置信息
//                                TotalPost tp = totalPostList.get(position);
//                                tv_question_id.setText(tp.getSenPerson()+"");
//                                tv_question_date.setText(tp.getDate().toString());
//                                tv_question_context.setText(tp.getContent());
//                                tv_question_pinglun.setText(tp.getCommentNumber() + "");
//                                return view;
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        System.out.println(s);
//                    }
//                }
//        );
//
//
//
//
//    }
//
//
//
//
//
//}
