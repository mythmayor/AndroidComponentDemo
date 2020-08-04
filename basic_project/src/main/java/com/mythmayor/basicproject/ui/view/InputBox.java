package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythmayor.basicproject.BasicApplication;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.utils.CommonUtil;

/**
 * Created by mythmayor on 2020/7/15.
 * 自定义输入框
 */
public class InputBox extends FrameLayout {

    private TextView tvtitle;
    private TextView tvcontent;
    private EditText etcontent;

    public static final int TYPE_DISPLAY = 1;//展示
    public static final int TYPE_EDIT = 2;//编辑

    private static final int DEFAULT_TYPE = TYPE_DISPLAY;
    private static final int DEFAULT_TVTITLE_TEXTCOLOR = CommonUtil.getColor(R.color.color_666666);
    private static final int DEFAULT_TVTITLE_TEXTSIZE = CommonUtil.sp2px(BasicApplication.getInstance().getContext(), 16);
    private static final int DEFAULT_TVCONTENT_TEXTCOLOR = CommonUtil.getColor(R.color.color_333333);
    private static final int DEFAULT_TVCONTENT_TEXTSIZE = CommonUtil.sp2px(BasicApplication.getInstance().getContext(), 16);
    private static final int DEFAULT_ETCONTENT_TEXTCOLOR = CommonUtil.getColor(R.color.color_333333);
    private static final int DEFAULT_ETCONTENT_TEXTSIZE = CommonUtil.sp2px(BasicApplication.getInstance().getContext(), 16);

    public InputBox(@NonNull Context context) {
        this(context, null);
    }

    public InputBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_input_box, this);
        initView();//初始化View
        initAttrs(context, attrs);
        initEvent();//初始化事件
    }

    private void initView() {
        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvcontent = (TextView) findViewById(R.id.tv_content);
        etcontent = (EditText) findViewById(R.id.et_content);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputBox);
        int type = typedArray.getInteger(R.styleable.InputBox_inputbox_type, DEFAULT_TYPE);
        setType(type);
        //标题
        String tvtitleText = typedArray.getString(R.styleable.InputBox_inputbox_tvtitleText);
        setTvtitleText(tvtitleText);
        int tvtitleTextColor = typedArray.getColor(R.styleable.InputBox_inputbox_tvtitleTextColor, DEFAULT_TVTITLE_TEXTCOLOR);
        setTvtitleColor(tvtitleTextColor);
        int tvtitleTextSize = typedArray.getDimensionPixelSize(R.styleable.InputBox_inputbox_tvtitleTextSize, DEFAULT_TVTITLE_TEXTSIZE);
        setTvtitleSize(tvtitleTextSize);
        //内容展示框
        String tvcontentText = typedArray.getString(R.styleable.InputBox_inputbox_tvcontentText);
        setTvcontentText(tvcontentText);
        String tvcontentHint = typedArray.getString(R.styleable.InputBox_inputbox_tvcontentHint);
        setTvcontentHint(tvcontentHint);
        int tvcontentTextColor = typedArray.getColor(R.styleable.InputBox_inputbox_tvcontentTextColor, DEFAULT_TVCONTENT_TEXTCOLOR);
        setTvcontentColor(tvcontentTextColor);
        int tvcontentTextSize = typedArray.getDimensionPixelSize(R.styleable.InputBox_inputbox_tvcontentTextSize, DEFAULT_TVCONTENT_TEXTSIZE);
        setTvcontentSize(tvcontentTextSize);
        Drawable tvcontentDrawableRight = typedArray.getDrawable(R.styleable.InputBox_inputbox_tvcontentDrawableRight);
        setTvcontentDrawableRight(tvcontentDrawableRight);
        //内容输入框
        String etcontentText = typedArray.getString(R.styleable.InputBox_inputbox_etcontentText);
        setEtcontentText(etcontentText);
        String etcontentHint = typedArray.getString(R.styleable.InputBox_inputbox_etcontentHint);
        setEtcontentHint(etcontentHint);
        int etcontentTextColor = typedArray.getColor(R.styleable.InputBox_inputbox_etcontentTextColor, DEFAULT_ETCONTENT_TEXTCOLOR);
        setEtcontentColor(etcontentTextColor);
        int etcontentTextSize = typedArray.getDimensionPixelSize(R.styleable.InputBox_inputbox_etcontentTextSize, DEFAULT_ETCONTENT_TEXTSIZE);
        setEtcontentSize(etcontentTextSize);
    }

    private void initEvent() {

    }

    public void setType(int type) {
        if (type == TYPE_DISPLAY) {
            tvcontent.setVisibility(VISIBLE);
            etcontent.setVisibility(GONE);
            String text = etcontent.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                tvcontent.setText(text);
            }
        } else if (type == TYPE_EDIT) {
            tvcontent.setVisibility(GONE);
            etcontent.setVisibility(VISIBLE);
            String text = tvcontent.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                etcontent.setText(text);
                etcontent.setSelection(text.length());
            }
        }
    }

    public void setTvtitleText(String tvtitleText) {
        tvtitle.setText(tvtitleText);
    }

    public void setTvtitleColor(int tvtitleTextColor) {
        tvtitle.setTextColor(tvtitleTextColor);
    }

    public void setTvtitleSize(int tvtitleTextSize) {
        tvtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvtitleTextSize);
    }

    public void setTvcontentText(String tvcontentText) {
        tvcontent.setText(tvcontentText);
    }

    public void setTvcontentHint(String tvcontentHint) {
        tvcontent.setHint(tvcontentHint);
    }

    public void setTvcontentColor(int tvcontentTextColor) {
        tvcontent.setTextColor(tvcontentTextColor);
    }

    public void setTvcontentSize(int tvcontentTextSize) {
        tvcontent.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvcontentTextSize);
    }

    public void setTvcontentDrawableRight(Drawable tvcontentDrawableRight) {
        tvcontent.setCompoundDrawablesWithIntrinsicBounds(null, null, tvcontentDrawableRight, null);
    }

    public void setTvcontentDrawableRight(int drawableRightResId) {
        tvcontent.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRightResId, 0);
    }

    public void setEtcontentText(String etcontentText) {
        etcontent.setText(etcontentText);
    }

    public void setEtcontentHint(String etcontentHint) {
        etcontent.setHint(etcontentHint);
    }

    public void setEtcontentColor(int etcontentTextColor) {
        etcontent.setTextColor(etcontentTextColor);
    }

    public void setEtcontentSize(int etcontentTextSize) {
        etcontent.setTextSize(TypedValue.COMPLEX_UNIT_PX, etcontentTextSize);
    }

    public String getInputContent() {//输入框内容
        return etcontent.getText().toString();
    }

    public String getShowContent() {//展示框内容
        return tvcontent.getText().toString();
    }
}
