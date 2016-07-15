package com.geminno.youstudy.fragment.communication_fragment;

import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.geminno.youstudy.HomepageActivity;
import com.geminno.youstudy.R;
import com.geminno.youstudy.comom.ComomFragment;
import com.geminno.youstudy.utils.SelectPicPopupWindow;

import java.io.File;

/**
 * Created by KeryZhang on 6/27/2016.
 */
public class AddParentscmmFragment extends ComomFragment {
    private EditText et_myparentscmm_neirong;
    private EditText et_myparentscmm_title;
    private ImageButton ib_add_parentscmm_img;
    private Button bt_parentscmm_send;
    private View view;
    private SelectPicPopupWindow menuWindow;
    private ImageButton ib_pro_parentscmmfragment_frommyq;
    @Override
    public View initView(LayoutInflater inflater) {


        String file_str = Environment.getExternalStorageDirectory().getPath();
        File mars_file = new File(file_str + "/my_camera");
        File file_go = new File(file_str + "/my_camera/file.jpg");


        view = inflater.inflate(R.layout.add_parentscmmdetails, null);
        et_myparentscmm_title = ((EditText) view.findViewById(R.id.et_myparentscmm_title));
        et_myparentscmm_neirong = ((EditText) view.findViewById(R.id.et_myparentscmm_neirong));
        ib_add_parentscmm_img = ((ImageButton) view.findViewById(R.id.ib_add_parentscmm_img));
        bt_parentscmm_send = ((Button) view.findViewById(R.id.bt_parentscmm_send));
        ib_pro_parentscmmfragment_frommyq = ((ImageButton) view.findViewById(R.id.ib_pro_parentscmmfragment_frommyq));


        return view;
    }

    @Override
    public void initData() {

        ib_pro_parentscmmfragment_frommyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new MypostFragment());
            }
        });

        ib_add_parentscmm_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(getActivity(), itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置


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
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                    break;
                case R.id.btn_pick_photo:
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");//相片类型
                    startActivityForResult(intent1, 1);

                    break;
                default:
                    break;
            }


        }

    };
    public void switchFragment(Fragment fragment) {
        if (fragment != null) {
            if (getActivity() instanceof HomepageActivity) {
                ((HomepageActivity) getActivity()).switchFragment(fragment);
            }
        }
    }


}

