package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.bean.SearchHistoryBean;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.FlowLayout;
import com.mythmayor.basicproject.ui.view.SearchBar;
import com.mythmayor.basicproject.utils.CommonUtil;
import com.mythmayor.basicproject.utils.SearchHistoryUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.SearchContract;
import com.mythmayor.mainproject.presenter.SearchPresenter;

import java.util.List;
import java.util.Random;

/**
 * Created by mythmayor on 2020/7/15.
 */
public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.View {

    private SearchBar mSearchBar;
    private ImageView ivdeletehistory;
    private FlowLayout mFlowLayout;
    private LinearLayout llhistory;
    private TextView tvnodata;
    private TextView tvresult;
    private LinearLayout llresult;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mPresenter = new SearchPresenter();
        mPresenter.attachView(this);
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
        ivdeletehistory = (ImageView) findViewById(R.id.iv_delete_history);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        llhistory = (LinearLayout) findViewById(R.id.ll_history);
        tvnodata = (TextView) findViewById(R.id.tv_no_data);
        tvresult = (TextView) findViewById(R.id.tv_result);
        llresult = (LinearLayout) findViewById(R.id.ll_result);
    }

    @Override
    protected void initEvent() {
        ivdeletehistory.setOnClickListener(this);
        mSearchBar.setOnCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchBar.setOnSearchListener(new SearchBar.OnSearchListener() {
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

    private void search(String input) {
        Random random = new Random();
        boolean haveResult = random.nextBoolean();//模拟搜索结果
        llhistory.setVisibility(View.GONE);
        if (haveResult) {
            tvnodata.setVisibility(View.GONE);
            llresult.setVisibility(View.VISIBLE);
            tvresult.setText(input);
        } else {
            tvnodata.setVisibility(View.VISIBLE);
            llresult.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData(Intent intent) {
        initFlowLayout();
    }

    private void initFlowLayout() {
        if (mFlowLayout != null) {
            mFlowLayout.removeAllViews();
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
                    mSearchBar.setEtSearchText(content);
                    search(content);
                }
            });
            mFlowLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == ivdeletehistory) {
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
