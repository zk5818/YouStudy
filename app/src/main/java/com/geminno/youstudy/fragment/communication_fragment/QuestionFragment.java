package com.geminno.youstudy.fragment.communication_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geminno.youstudy.HomepageActivity;
import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;
import com.geminno.youstudy.fragment.communicateFragment;
import com.geminno.youstudy.pojo.CommentBean;

import com.geminno.youstudy.pojo.QuestionBean;
import com.geminno.youstudy.pojo.QuotesBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
public class QuestionFragment extends ComomFragment {
    private ImageButton ib_pro_communicationfragment;
    private ImageButton ib_add_myquestion;
    private PullToRefreshListView lv_question;
    private ImageView ivDeleteText;
    private EditText etSearch;
    List<QuestionBean.Question> questionList = new ArrayList<QuestionBean.Question>();
    List<QuestionBean.Question> dataList = new ArrayList<QuestionBean.Question>();

    @Override
    public View initView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.fragment_question, null);
        lv_question = (PullToRefreshListView) view.findViewById(R.id.lv_question);

        ib_pro_communicationfragment = (ImageButton) view.findViewById(R.id.ib_pro_communicationfragment);
        ib_add_myquestion = (ImageButton) view.findViewById(R.id.ib_add_myquestion);
        ib_add_myquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new AddmyquestionFragment());
            }
        });


        ivDeleteText = (ImageView) view.findViewById(R.id.ivDeleteText);
        etSearch = (EditText) view.findViewById(R.id.etSearch);

        ib_pro_communicationfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new communicateFragment());
            }
        });


        ivDeleteText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });
        HttpUtils http = new HttpUtils();
        http.send(
                HttpRequest.HttpMethod.GET,
                "http://192.168.30.27:8080/youStudySys/getquestionsdata",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        QuestionBean bean = gson.fromJson(responseInfo.result, QuestionBean.class);
                        questionList = bean.data;
                        System.out.println(questionList.size() + "----------------------//////");
                        MyAdapter ma = new MyAdapter();
                        ma.notifyDataSetChanged();
                        lv_question.setAdapter(ma);


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println(s);
                    }
                }
        );

        lv_question.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "End of List!",
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void initData() {

        lv_question.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), QuestionDetails.class);
                intent.putExtra("commentId", questionList.get(position - 1).getCommentId());

                Bundle bundle = new Bundle();
                bundle.putSerializable("comment", questionList.get(position - 1));
                intent.putExtras(bundle);
                startActivity(intent);

//                Bundle bundle = new Bundle();
//                bundle.putLong("commentId", questionList.get(position-1).getCommentId());
//                bundle.putSerializable("comment", questionList.get(position-1));
//                QuestionDetails_Fragment questionDetails_fragment = new QuestionDetails_Fragment();
//                questionDetails_fragment.setArguments(bundle);
//
//                switchFragment(questionDetails_fragment);
            }
        });
        //设置一个监听器，被调用时刷新列表（下拉刷新）

        lv_question.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 更新最后更新标签
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                getNews();
            }
        }
        );
    }

    public void getNews() {
        HttpUtils http = new HttpUtils();
        http.send(
                HttpRequest.HttpMethod.GET,
                "http://192.168.30.27:8080/youStudySys/getquestionsdata",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        QuestionBean bean = gson.fromJson(responseInfo.result, QuestionBean.class);
                        questionList = bean.data;
                        System.out.println(questionList.size() + "----------------------//////");
                        MyAdapter ma = new MyAdapter();
                        ma.notifyDataSetChanged();
                        lv_question.setAdapter(ma);


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println(s);
                    }
                }
        );


    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return questionList.size();
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
            //打气筒inflate
            View view = View.inflate(getContext(), R.layout.question_form, null);
            TextView tv_question_author = ((TextView) view.findViewById(R.id.tv_question_author));
            TextView tv_question_titlecontent = ((TextView) view.findViewById(R.id.tv_question_titlecontent));
            TextView tv_question_date = ((TextView) view.findViewById(R.id.tv_question_date));
            TextView tv_question_context = ((TextView) view.findViewById(R.id.tv_question_context));
//                                TextView tv_question_pinglun = ((TextView) view.findViewById(R.id.tv_question_pinglun));
//                                TextView tv_question_form_pinglun = ((TextView) view.findViewById(R.id.tv_question_form_pinglun));
//                                inittext(tv_question_form_pinglun);

            //获取当前位置信息
            QuestionBean.Question tp = questionList.get(position);
            tv_question_author.setText(tp.getUserName() + "");
            tv_question_date.setText(tp.getDate());
            tv_question_context.setText(tp.getContent());
            tv_question_titlecontent.setText(tp.getContentTitle() + "");
            return view;
        }
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