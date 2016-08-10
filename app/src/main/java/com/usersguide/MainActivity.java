package com.usersguide;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Auth：yujunyao
 * Since: 2016/8/9 16:39
 * Email：yujunyao@yonglibao.com
 */
public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener{
    private TextView textView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.textview1);
        ViewTreeObserver vto = textView1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);


    }

    private void showGuideDialog(final int[] location, TextView textView) {
        final Dialog dialog = new Dialog(this, R.style.MyDialogStyleCenter);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_guide);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        GuideView guideView = (GuideView) window.findViewById(R.id.guideview);
        int width = textView.getWidth();
        int height = textView.getHeight();
        guideView.setData(location[0], location[1] - (width - height) / 2 - getStatusBarHeight(this), textView.getWidth());

        /**此图片就做下简单的坐标跟大小适配就好了这里就不写了*/
        ImageView guideImg = (ImageView) window.findViewById(R.id.guide_img);
        RelativeLayout.LayoutParams promptMargin = new RelativeLayout.LayoutParams(guideImg.getLayoutParams());
        promptMargin.setMargins(400, 600, 0, 0);
        guideImg.setLayoutParams(promptMargin);


        dialog.show();
    }

    @Override
    public void onGlobalLayout() {
        textView1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        int[] location = new int[2];
        textView1.getLocationInWindow(location);
        textView1.getLocationOnScreen(location);
//        int x = location[0];//x坐标
//        int y = location[1];//y坐标
//        int withdrawWidth = textView1.getWidth();
//        int withdrawHeight = textView1.getHeight();
        showGuideDialog(location, textView1);
    }

    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

}
