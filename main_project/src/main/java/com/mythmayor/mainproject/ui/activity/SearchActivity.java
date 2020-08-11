package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvvmActivity;
import com.mythmayor.basicproject.bean.SearchHistoryBean;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.SearchBar;
import com.mythmayor.basicproject.utils.CommonUtil;
import com.mythmayor.basicproject.utils.SearchHistoryUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.databinding.ActivitySearchBinding;
import com.mythmayor.mainproject.viewmodel.SearchViewModel;

import java.util.List;
import java.util.Random;

/**
 * Created by mythmayor on 2020/7/15.
 * 搜索页面
 */
public class SearchActivity extends BaseMvvmActivity<SearchViewModel, ActivitySearchBinding> {

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.ivDeleteHistory.setOnClickListener(this);
        mViewDataBinding.searchBar.setOnCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewDataBinding.searchBar.setOnSearchListener(new SearchBar.OnSearchListener() {
            @Override
            public void onInputFinished(String input) {
                if (!TextUtils.isEmpty(input)) {//搜索内容不为空，存储到本地
                    SearchHistoryBean bean = new SearchHistoryBean(input);
                    SearchHistoryUtil.getInstance().add(bean, null);
                }
                search(input);
            }
        });
    }

    @Override
    protected void initMvvmData(Intent intent) {
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        initFlowLayout();
    }

    private void search(String input) {
        Random random = new Random();
        boolean haveResult = random.nextBoolean();//模拟搜索结果
        mViewDataBinding.llHistory.setVisibility(View.GONE);
        if (haveResult) {
            mViewDataBinding.tvNoData.setVisibility(View.GONE);
            mViewDataBinding.llResult.setVisibility(View.VISIBLE);
            mViewDataBinding.tvResult.setText(input);
        } else {
            mViewDataBinding.tvNoData.setVisibility(View.VISIBLE);
            mViewDataBinding.llResult.setVisibility(View.GONE);
        }
    }

    private void initFlowLayout() {
        if (mViewDataBinding.flowLayout != null) {
            mViewDataBinding.flowLayout.removeAllViews();
        }
        List<SearchHistoryBean> list = SearchHistoryUtil.getInstance().get(null);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            final TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_history, null, false);
            textView.setText(list.get(i).getName());
            //往容器内添加TextView数据
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, CommonUtil.dip2px(this, 12), CommonUtil.dip2px(this, 16));
            textView.setLayoutParams(layoutParams);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = textView.getText().toString().trim();
                    mViewDataBinding.searchBar.setEtSearchText(content);
                    search(content);
                }
            });
            mViewDataBinding.flowLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == mViewDataBinding.ivDeleteHistory) {
            SearchHistoryUtil.getInstance().clear(null);
            initFlowLayout();
        }
    }

    @Override
    public void showLoading(String address) {
    }

    @Override
    public void hideLoading(String address) {
    }

    @Override
    public void onError(String address, String errMessage) {
    }

    @Override
    public void onSuccess(String address, BaseResponse baseResp) {
    }
}
