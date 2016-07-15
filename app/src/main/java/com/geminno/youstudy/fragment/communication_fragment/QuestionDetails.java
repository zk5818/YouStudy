package com.geminno.youstudy.fragment.communication_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geminno.youstudy.HomepageActivity;
import com.geminno.youstudy.R;
import com.geminno.youstudy.pojo.QuestionBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class QuestionDetails extends AppCompatActivity {
    private TextView tv_questiondetail_senperson;
    private TextView tv_question_detail_title;
    private TextView questiondetail_content;
    private TextView tv_qdetails_pinlunshu;
    private TextView tv_qdetails_pinglun;
    private ListView lv_questiondetails_answer;
    private QuestionBean.Question post;
    private int commentId;
    private ImageButton ib_pro_questionfragment;
    List<QuestionBean.Question> commentList_answer = new ArrayList<QuestionBean.Question>();
    private LinearLayout ll_sendmsg;
    private Button bt_send_comment;
    private EditText et_send_msg_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
        final Intent intent = getIntent();
        final int id = intent.getIntExtra("commentId", -1);
        post = (QuestionBean.Question) intent.getSerializableExtra("comment");
        commentId = post.getCommentId();


        View view1 = View.inflate(this, R.layout.headquestion, null);
        tv_questiondetail_senperson = ((TextView) view1.findViewById(R.id.tv_questiondetail_senperson));
        tv_question_detail_title = ((TextView) view1.findViewById(R.id.tv_question_detail_title));
        questiondetail_content = ((TextView) view1.findViewById(R.id.questiondetail_content));
        tv_qdetails_pinlunshu = ((TextView) view1.findViewById(R.id.tv_qdetails_pinlunshu));

        tv_qdetails_pinglun = ((TextView) view1.findViewById(R.id.tv_qdetails_pinglun));
        inittext(tv_qdetails_pinglun);

        tv_qdetails_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ll_sendmsg.getVisibility()) == View.VISIBLE) {
                    ll_sendmsg.setVisibility(View.GONE);
                } else {
                    ll_sendmsg.setVisibility(View.VISIBLE);
                }

            }
        });

        ll_sendmsg = ((LinearLayout) findViewById(R.id.ll_sendmsg));

        et_send_msg_text = ((EditText) findViewById(R.id.et_send_msg_text));
        bt_send_comment = ((Button) findViewById(R.id.bt_send_comment));


        bt_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_content = et_send_msg_text.getText().toString();
                String path = "http://192.168.30.27:8080/youStudySys/sendmessage";
                HttpUtils httpUtils = new HttpUtils();
                RequestParams params = new RequestParams();

                params.addBodyParameter("messageText", msg_content);
                params.addBodyParameter("userId", "1");
                params.addBodyParameter("userName", post.getUserName());
                params.addBodyParameter("pid", (id + ""));
                params.addBodyParameter("tableName", "comment");
                params.addBodyParameter("commentType", "2");
                httpUtils.send(HttpRequest.HttpMethod.POST, path, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //上传成功，刷新界面
                        System.out.println("上传成功");
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println("上传失败");
                    }
                });

                ll_sendmsg.setVisibility(View.GONE);
                initData();
            }
        });

        tv_questiondetail_senperson.setText(post.getUserName() + "");
        tv_question_detail_title.setText(post.getContentTitle());
        questiondetail_content.setText(post.getContent());
        tv_qdetails_pinlunshu.setText(post.getCommentNumber() + "");

        lv_questiondetails_answer = ((ListView) findViewById(R.id.lv_questiondetails_answer));
        ib_pro_questionfragment = ((ImageButton) findViewById(R.id.ib_pro_questionfragment));
        ib_pro_questionfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv_questiondetails_answer.addHeaderView(view1);
        initData();
    }

    private void initData() {
        HttpUtils http = new HttpUtils();
        http.send(
                HttpRequest.HttpMethod.GET,
                "http://192.168.30.27:8080/youStudySys/getanswerdata?commentId=" + commentId,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        QuestionBean bean_answer = gson.fromJson(responseInfo.result, QuestionBean.class);
                        List<QuestionBean.Question> beansanswer = new ArrayList<QuestionBean.Question>();
                        beansanswer = bean_answer.data;
                        QuestionBean.Question question;
                        for (QuestionBean.Question comment2 : beansanswer) {
                            question = new QuestionBean.Question();
                            question.setCommentId(comment2.getCommentId());
                            question.setUserId(comment2.getUserId());
                            question.setUserName(comment2.getUserName());
                            question.setPid(comment2.getPid());
                            question.setDate(comment2.getDate());
                            question.setLikes(comment2.getLikes());
                            question.setCommentNumber(comment2.getCommentNumber());
                            question.setContentTitle(comment2.getContentTitle());
                            question.setContent(comment2.getContent());
                            question.setCommentType(comment2.getCommentType());
                            question.setTableName(comment2.getTableName());
                            commentList_answer.add(question);
                        }


                        lv_questiondetails_answer.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return commentList_answer.size();
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
                                View view = View.inflate(getApplicationContext(), R.layout.answer_form, null);

                                TextView tv_qd_answer_senperson = ((TextView) view.findViewById(R.id.tv_qd_answer_senperson1));
                                TextView tv_qd_answer_date = ((TextView) view.findViewById(R.id.tv_qd_answer_date));
                                TextView tv_qd_answer_context = ((TextView) view.findViewById(R.id.tv_qd_answer_context));
                                TextView tv_qd_answer_dianzanshu = ((TextView) view.findViewById(R.id.tv_qd_answer_dianzanshu));

                                Button bt_qd_answer_dianzan = ((Button) view.findViewById(R.id.bt_qd_answer_dianzan));

                                final QuestionBean.Question tp_a = commentList_answer.get(position);
                                tv_qd_answer_senperson.setText(tp_a.getUserName() + "");
                                tv_qd_answer_date.setText(tp_a.getDate().toString());
                                tv_qd_answer_context.setText(tp_a.getContent());
                                tv_qd_answer_dianzanshu.setText(tp_a.getCommentNumber() + "");
                                bt_qd_answer_dianzan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //设置点赞数+1

                                        HttpUtils http = new HttpUtils();
                                        http.send(
                                                HttpRequest.HttpMethod.GET,
                                                "http://192.168.30.27:8080/youStudySys/sendLikes?commentId=" + tp_a.getCommentId(),
                                                new RequestCallBack<String>() {

                                                    @Override
                                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                                        System.out.println("你点赞了");
                                                    }

                                                    @Override
                                                    public void onFailure(HttpException e, String s) {

                                                    }
                                                }

                                        );

                                    }
                                });
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

    public void inittext(TextView textView) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        textView.setTypeface(typeface);

    }


}

