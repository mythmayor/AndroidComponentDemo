package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.itype.OnMyClickListener;

/**
 * Created by mythmayor on 2020/7/10.
 */
public class NavigationItemView extends FrameLayout  {

    private LinearLayout llitem;
    private ImageView ivicon;
    private TextView tvtitle;
    private ImageView ivarrow;

    public NavigationItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.navigation_item_view, this);
        initView();//初始化View
        initAttrs(context, attrs);
        initEvent();//初始化事件
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationItemView);
        Drawable iconDrawable = typedArray.getDrawable(R.styleable.NavigationItemView_niv_icon);
        if (iconDrawable != null) {
            ivicon.setImageDrawable(iconDrawable);
        }
        String text = typedArray.getString(R.styleable.NavigationItemView_niv_text);
        if (!TextUtils.isEmpty(text)) {
            tvtitle.setText(text);
        }
        Drawable arrowDrawable = typedArray.getDrawable(R.styleable.NavigationItemView_niv_arrowImage);
        if (arrowDrawable != null) {
            ivarrow.setImageDrawable(arrowDrawable);
        }
        typedArray.recycle();
    }

    private void initView() {
        llitem = (LinearLayout) findViewById(R.id.ll_item);
        ivicon = (ImageView) findViewById(R.id.iv_icon);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        ivarrow = (ImageView) findViewById(R.id.iv_arrow);
    }

    private void initEvent() {
    }

    public void setIconImage(int iconResId) {
        ivicon.setImageResource(iconResId);
    }

    public void setText(String title) {
        tvtitle.setText(title);
    }

    public void setArrowImage(int arrowResId) {
        ivarrow.setImageResource(arrowResId);
    }

    //为Item添加自定义点击事件
    public void setOnItemClickListener(OnClickListener listener) {
        llitem.setOnClickListener(listener);
    }
}
