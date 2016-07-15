package com.geminno.youstudy.fragment.communication_fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.geminno.youstudy.HomepageActivity;
import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;
import com.geminno.youstudy.utils.Base64Coder;
import com.geminno.youstudy.utils.SelectPicPopupWindow;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeryZhang on 6/27/2016.
 */
public class AddmyquestionFragment extends ComomFragment {
    private EditText et_myquestion_title;
    private EditText et_myquestion_neirong;
    private ImageButton ib_add_question_img;
    private Button bt_myquestion_send;
    private View view;
    private SelectPicPopupWindow menuWindow;
    private ImageButton ib_pro_questionfragment_frommyq;
    private final String TAG = "MainActivity";
    private Bitmap bitmap;
    private MyHalder myHalder = new MyHalder();
    //用户信息目录
    private static final String FILE_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/my_camera/user";

    //用户拍照后存放的临时目录
    private static final String IMG_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/my_camera/temp";
    private ImageView iv_photo_show;

    @Override
    public View initView(LayoutInflater inflater) {

        view = inflater.inflate(R.layout.add_questiondetails, null);
        et_myquestion_title = ((EditText) view.findViewById(R.id.et_myquestion_title));
        et_myquestion_neirong = ((EditText) view.findViewById(R.id.et_myquestion_neirong));
        ib_add_question_img = ((ImageButton) view.findViewById(R.id.ib_add_question_img));
        bt_myquestion_send = ((Button) view.findViewById(R.id.bt_myquestion_send));
        ib_pro_questionfragment_frommyq = ((ImageButton) view.findViewById(R.id.ib_pro_questionfragment_frommyq));
        iv_photo_show = ((ImageView) view.findViewById(R.id.iv_photo_show));

        ib_pro_questionfragment_frommyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new QuestionFragment());
            }
        });
        return view;
    }

    @Override
    public void initData() {


        ib_add_question_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(getActivity(), itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置


            }
        });


        bt_myquestion_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_myquestion_title.getText().toString();
                String neirong = et_myquestion_neirong.getText().toString();
                String picturePosition = (IMG_PATH + "/temp.jpg").toString();
                String strImg = bitToString(bitmap);
                String path = "http://192.168.30.44:8080/youStudySys/upserver";
                HttpUtils httpUtils = new HttpUtils();
                RequestParams params = new RequestParams();

                params.addBodyParameter("title", title);
                params.addBodyParameter("neirong", neirong);
                params.addBodyParameter("userId", "1");
                params.addBodyParameter("userName", "jack");
                params.addBodyParameter("tableName", "comment");
                params.addBodyParameter("commentType", "1");
                params.addBodyParameter("strImg", strImg);
                httpUtils.send(HttpRequest.HttpMethod.POST, path, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //上传成功，刷新界面
                        System.out.println("问题发送成功");
                        switchFragment(new QuestionFragment());
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println("问题发送失败-------------------------" + s);
                    }
                });

            }
        });


    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String out_file_path = IMG_PATH + "/temp.jpg";
                    File dir = new File(IMG_PATH);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(out_file_path)));
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, 1);

                    break;
                case R.id.btn_pick_photo:
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");//相片类型
                    startActivityForResult(intent1, 2);
                    break;
                default:
                    break;
            }


        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(IMG_PATH + "/temp.jpg");
                    Log.i(TAG, "Compress-------------succeed" + fis);
                    //文件流转换为BitMap
                    bitmap = BitmapFactory.decodeStream(fis);
                    //处理图片
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                            myHalder.sendEmptyMessage(1);
                        }
                    }).start();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                Uri mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    try {
                        //这个方法是根据Uri获取Bitmap图片的静态方法
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                                mImageCaptureUri);
                        if (bitmap != null) {
                            iv_photo_show.setImageBitmap(bitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
//        if (bitmap != null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    saveBitmapToFile(FILE_PATH, bitmap, "aa.jpg");
//                    //FILE_PATH:  /storage/emulated/0/my_camera/user
//                    Log.i(TAG, "Compress-------------succeed");
//                }
//            }).start();
//        }
    }

    class MyHalder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            iv_photo_show.setImageBitmap(bitmap);

        }
    }

    public void switchFragment(Fragment fragment) {
        if (fragment != null) {
            if (getActivity() instanceof HomepageActivity) {
                ((HomepageActivity) getActivity()).switchFragment(fragment);
            }
        }
    }

//    public static void saveBitmapToFile(String filrPath, Bitmap mBitmap, String bitName) {
//        //File file = Environment.getExternalStorageDirectory();
//        //获取手机的自带存储路径下的指定文件夹
//        File parentPath = new File(filrPath);
//        //如果不存在，就创建
//        if (!parentPath.exists()) {
//            parentPath.mkdir();
//        } else {
//            //存在，就将图片写入该目录
//            StringBuilder sb = new StringBuilder();
//            sb.append(parentPath.getPath()).append("/").append(bitName);
//            File imgFile = new File(sb.toString());
//            //获取压缩后的图片
//            try {
//                FileOutputStream fOut = new FileOutputStream(imgFile);
////                fOut.toString()
//
//
//
//
//                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
//
//
//
//
//
//
//
//                fOut.flush();
//                fOut.close();
//                Log.i("SaveBitmapToFile", "succeed");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    public String bitToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] b = stream.toByteArray();
        // 将图片流以字符串形式存储下来
        String file = new String(Base64Coder.encodeLines(b));
//        System.out.println(file);
        return file;
    }


}

