package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.base.BaseDialog;
import com.mythmayor.basicproject.base.BaseTitleBarMvpActivity;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.activity.WebViewActivity;
import com.mythmayor.basicproject.ui.dialog.LogoutDialog;
import com.mythmayor.basicproject.ui.view.NavigationItemView;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.PrefUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UpdateUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.SettingContract;
import com.mythmayor.mainproject.presenter.SettingPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/7/10.
 * 设置页面
 */
@Route(path = "/mainproject/SettingActivity")
public class SettingActivity extends BaseTitleBarMvpActivity<SettingPresenter> implements SettingContract.View {

    private NavigationItemView nivclearcache;
    private NavigationItemView nivcheckupdate;
    private NavigationItemView nivuserprivacy;
    private NavigationItemView nivaboutus;
    private TextView tvlogout;

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new SettingPresenter();
        mPresenter.attachView(this);
        nivclearcache = (NavigationItemView) findViewById(R.id.niv_clearcache);
        nivcheckupdate = (NavigationItemView) findViewById(R.id.niv_checkupdate);
        nivuserprivacy = (NavigationItemView) findViewById(R.id.niv_userprivacy);
        nivaboutus = (NavigationItemView) findViewById(R.id.niv_aboutus);
        tvlogout = (TextView) findViewById(R.id.tv_logout);
    }

    @Override
    public void initSubEvent() {
        nivclearcache.setOnClickListener(this);
        nivcheckupdate.setOnClickListener(this);
        nivuserprivacy.setOnClickListener(this);
        nivaboutus.setOnClickListener(this);
        tvlogout.setOnClickListener(this);
    }

    @Override
    public void initSubData(Intent intent) {

    }

    @Override
    public void setTitleBar() {
        setLeftImage(true, com.mythmayor.basicproject.R.mipmap.arrow_left);
        setTopTitle(true, "设置");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == nivclearcache) {
            nivclearcache.setContent("0M");
            ToastUtil.showToast(this, "清理缓存成功");
        } else if (v == nivcheckupdate) {
            List<String> updateContent = new ArrayList<>();
            updateContent.add("1.修复已知Bug。");
            updateContent.add("2.优化用户体验。");
            String url = "https://shouji.sogou.com/apkdl/?gr=opact&tp=pcyunying&id=0";
            UpdateUtil updateUtil = new UpdateUtil(this, "1.0.0", false, updateContent, url);
            updateUtil.alertUpdateAppDialog();
        } else if (v == nivuserprivacy) {
            IntentUtil.startWebViewActivity(this, "用户隐私协议", "https://www.baidu.com/", WebViewActivity.class);
        } else if (v == nivaboutus) {
            IntentUtil.startActivity(this, AboutUsActivity.class);
        } else if (v == tvlogout) {
            showLogoutDialog();
        }
    }

    private void showLogoutDialog() {
        String content = "确定要退出登录么？";
        final LogoutDialog dialog = new LogoutDialog(this, content, "取消", "确定");
        dialog.setNoOnclickListener(new BaseDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(Object o) {
                dialog.dismiss();
            }
        });
        dialog.setYesOnclickListener(new BaseDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(Object o) {
                dialog.dismiss();
                String accountInfo = PrefUtil.getString(SettingActivity.this, PrefUtil.SP_ACCOUNT, "");
                PrefUtil.clear(SettingActivity.this);
                PrefUtil.putString(SettingActivity.this, PrefUtil.SP_ACCOUNT, accountInfo);
                IntentUtil.startActivityClearTask(SettingActivity.this, LoginActivity.class);
            }
        });
        dialog.show();
        //ProjectUtil.setDialogWindowAttr(dialog);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(String errMessage) {
    }

    @Override
    public void onSuccess(BaseResponse baseResp) {
    }
}
