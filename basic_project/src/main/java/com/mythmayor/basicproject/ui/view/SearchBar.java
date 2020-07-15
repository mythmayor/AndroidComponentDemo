package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.utils.CommonUtil;
import com.mythmayor.basicproject.utils.ProjectUtil;

/**
 * Created by mythmayor on 2020/7/15.
 * 搜索框
 */
public class SearchBar extends FrameLayout {

    private LinearLayout llsearch;
    private ImageView ivsearch;
    private CustomEditText etsearch;
    private ImageView ivclear;
    private TextView tvcancel;
    private OnSearchListener mListener;
    private boolean isAllowInput;

    private static final int DEFAULT_TVCANCEL_TEXTCOLOR = CommonUtil.getColor(R.color.color_76C0F7);
    private static final boolean DEFAULT_SHOW_TVCANCEL = true;
    private static final boolean DEFAULT_IS_ALLOW_INPUT = true;

    public SearchBar(@NonNull Context context) {
        this(context, null);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_search_bar, this);
        initView();//初始化View
        initAttrs(context, attrs);
        initEvent();//初始化事件
    }

    private void initView() {
        llsearch = (LinearLayout) findViewById(R.id.ll_search);
        ivsearch = (ImageView) findViewById(R.id.iv_search);
        etsearch = (CustomEditText) findViewById(R.id.et_search);
        ivclear = (ImageView) findViewById(R.id.iv_clear);
        tvcancel = (TextView) findViewById(R.id.tv_cancel);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBar);
        isAllowInput = typedArray.getBoolean(R.styleable.SearchBar_search_isAllowInput, DEFAULT_IS_ALLOW_INPUT);
        boolean showTvcancel = typedArray.getBoolean(R.styleable.SearchBar_search_showTvcancel, DEFAULT_SHOW_TVCANCEL);
        tvcancel.setVisibility(showTvcancel ? VISIBLE : GONE);
        Drawable llsearchDrawable = typedArray.getDrawable(R.styleable.SearchBar_search_bg);
        if (llsearchDrawable != null) {
            llsearch.setBackground(llsearchDrawable);
        }
        int color = typedArray.getColor(R.styleable.SearchBar_search_tvcancelTextColor, DEFAULT_TVCANCEL_TEXTCOLOR);
        tvcancel.setTextColor(color);
        Drawable ivsearchDrawable = typedArray.getDrawable(R.styleable.SearchBar_search_ivsearchSrc);
        if (ivsearchDrawable != null) {
            ivsearch.setImageDrawable(ivsearchDrawable);
        }
        Drawable ivclearDrawable = typedArray.getDrawable(R.styleable.SearchBar_search_ivclearSrc);
        if (ivclearDrawable != null) {
            ivclear.setImageDrawable(ivclearDrawable);
        }
        String hint = typedArray.getString(R.styleable.SearchBar_search_hint);
        if (!TextUtils.isEmpty(hint)) {
            etsearch.setHint(hint);
        }
        typedArray.recycle();
    }

    private void initEvent() {
        etsearch.setEnabled(isAllowInput);
        if (isAllowInput) {
            ivclear.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    etsearch.setText("");
                }
            });
            ivsearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    search();
                }
            });
            etsearch.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                        //处理事件
                        search();
                        return true;
                    }
                    return false;
                }
            });
            etsearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        ivclear.setVisibility(View.VISIBLE);
                    } else {
                        ivclear.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void search() {
        String content = etsearch.getText().toString().trim();
        ProjectUtil.hideKeyboard(etsearch);
        if (mListener != null) {
            mListener.onInputFinished(content);
        }
    }

    public void setAllowInput(boolean allow) {
        isAllowInput = allow;
    }

    public void isShowTvcancel(boolean showTvcancel) {
        tvcancel.setVisibility(showTvcancel ? VISIBLE : GONE);
    }

    public void setSearchBackground(int resId) {
        llsearch.setBackgroundResource(resId);
    }

    public void setTvcancelTextColor(int color) {
        tvcancel.setTextColor(color);
    }

    public void setIvsearchSrc(int resId) {
        ivsearch.setImageResource(resId);
    }

    public void setIvclearSrc(int resId) {
        ivclear.setImageResource(resId);
    }

    public void setEtSearchHint(String hint) {
        etsearch.setHint(hint);
    }

    public void setEtSearchText(String text) {
        etsearch.setText(text);
        etsearch.setSelection(etsearch.getText().toString().length());//把光标移到末尾
    }

    //为搜索框外层布局添加点击事件
    public void setOnSearchBarClickListener(OnClickListener listener) {
        llsearch.setOnClickListener(listener);
    }

    //为取消操作添加点击事件
    public void setOnCancelClickListener(OnClickListener listener) {
        tvcancel.setOnClickListener(listener);
    }

    public void setOnSearchListener(OnSearchListener listener) {
        mListener = listener;
    }

    public interface OnSearchListener {
        void onInputFinished(String input);
    }
}
