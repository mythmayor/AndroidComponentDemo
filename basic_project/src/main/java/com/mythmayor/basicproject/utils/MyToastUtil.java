package com.mythmayor.basicproject.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mythmayor.basicproject.R;


/**
 * Created by mythmayor on 2020/6/30.
 * 自定义Toast工具类
 */
public class MyToastUtil {

    public static void showToast(Context context, String content) {
        showToast(context, -1, content);
    }

    public static void showToast(Context context, int stringResId) {
        showToast(context, -1, context.getString(stringResId));
    }

    public static void showToast(Context context, int imgResId, String content) {
        Toast toast;
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null); //加載layout下的布局
        ImageView imageView = view.findViewById(R.id.iv_content);
        TextView textView = view.findViewById(R.id.tv_content);
        if (-1 != imgResId) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(imgResId);
        } else {
            imageView.setVisibility(View.GONE);
            imageView.setImageResource(0);
        }
        textView.setText(content);
        toast = new Toast(context);
        //toast.setGravity(Gravity.BOTTOM, 0, 200);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view);//添加视图文件
        toast.show();

    }

    public static void showToast(Context context, int imgResId, int resId) {
        showToast(context, imgResId, context.getString(resId));
    }
}
