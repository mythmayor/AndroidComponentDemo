package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/7/10.
 * 导航条
 */
public class NavigationItemView extends FrameLayout {

    private LinearLayout llitem;
    private ImageView ivicon;
    private TextView tvtitle;
    private TextView tvcontent;
    private ImageView ivarrow;

    private static final int DEFAULT_DRAWABLE_PADDING = 10;

    public NavigationItemView(@NonNull Context context) {
        this(context, null);
    }

    public NavigationItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.navigation_item_view, this);
        initView();//初始化View
        initAttrs(context, attrs);
        initEvent();//初始化事件
    }

    private void initView() {
        llitem = (LinearLayout) findViewById(R.id.ll_item);
        ivicon = (ImageView) findViewById(R.id.iv_icon);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvcontent = (TextView) findViewById(R.id.tv_content);
        ivarrow = (ImageView) findViewById(R.id.iv_arrow);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationItemView);
        Drawable iconDrawable = typedArray.getDrawable(R.styleable.NavigationItemView_niv_icon);
        if (iconDrawable != null) {
            ivicon.setImageDrawable(iconDrawable);
        }
        String title = typedArray.getString(R.styleable.NavigationItemView_niv_title);
        if (!TextUtils.isEmpty(title)) {
            tvtitle.setText(title);
        }
        String content = typedArray.getString(R.styleable.NavigationItemView_niv_content);
        if (!TextUtils.isEmpty(content)) {
            tvcontent.setText(content);
        }
        Drawable leftDrawable = typedArray.getDrawable(R.styleable.NavigationItemView_niv_drawableLeft);
        Drawable rightDrawable = typedArray.getDrawable(R.styleable.NavigationItemView_niv_drawableRight);
        tvcontent.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, rightDrawable, null);
        int padding = typedArray.getDimensionPixelSize(R.styleable.NavigationItemView_niv_drawablePadding, DEFAULT_DRAWABLE_PADDING);
        tvcontent.setCompoundDrawablePadding(padding);
        Drawable arrowDrawable = typedArray.getDrawable(R.styleable.NavigationItemView_niv_arrowImage);
        if (arrowDrawable != null) {
            ivarrow.setImageDrawable(arrowDrawable);
        }
        typedArray.recycle();
    }

    private void initEvent() {
    }

    public void setIconImage(int iconResId) {
        ivicon.setImageResource(iconResId);
    }

    public void setTitle(String title) {
        tvtitle.setText(title);
    }

    public void setContent(String content) {
        tvcontent.setText(content);
    }

    public void isShowTips(boolean show) {
        if (show) {
            tvcontent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_notification_tips, 0);
        } else {
            tvcontent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public void isShowTips(boolean show, int drawableLeftResId, int drawableRightResId) {
        if (show) {
            tvcontent.setCompoundDrawablesWithIntrinsicBounds(drawableLeftResId, 0, drawableRightResId, 0);
        } else {
            tvcontent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public void setContentDrawablePadding(int padding) {
        tvcontent.setCompoundDrawablePadding(padding);
    }

    public void setArrowImage(int arrowResId) {
        ivarrow.setImageResource(arrowResId);
    }

    //为Item添加自定义点击事件
    public void setOnItemClickListener(OnClickListener listener) {
        llitem.setOnClickListener(listener);
    }
}
