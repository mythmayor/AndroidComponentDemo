package com.mythmayor.moduled.ui.activity;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseTitleBarMvvmActivity;
import com.mythmayor.basicproject.itype.OnDialogClickListener;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.activity.WebViewActivity;
import com.mythmayor.basicproject.ui.dialog.LogoutDialog;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.GlideCacheUtil;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.PrefUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UpdateUtil;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.databinding.ActivitySettingBinding;
import com.mythmayor.moduled.viewmodel.SettingViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/7/10.
 * 设置页面
 */
@Route(path = MyConstant.AROUTER_SettingActivity)
public class SettingActivity extends BaseTitleBarMvvmActivity<SettingViewModel, ActivitySettingBinding> {

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.nivClearcache.setOnClickListener(this);
        mViewDataBinding.nivCheckupdate.setOnClickListener(this);
        mViewDataBinding.nivUserprivacy.setOnClickListener(this);
        mViewDataBinding.nivAboutus.setOnClickListener(this);
        mViewDataBinding.nivTestroom.setOnClickListener(this);
        mViewDataBinding.tvLogout.setOnClickListener(this);
    }

    @Override
    protected void initMvvmData(Intent intent) {
        mViewDataBinding.nivClearcache.setContent("36M");
        mViewDataBinding.nivCheckupdate.setContent("发现新版本");
        mViewDataBinding.nivCheckupdate.isShowTips(true);
    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, com.mythmayor.basicproject.R.mipmap.arrow_left);
        topTitleBar.setTopTitle(true, "设置");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == mViewDataBinding.nivClearcache) {
            GlideCacheUtil.getInstance().clearImageAllCache(this);
            mViewDataBinding.nivClearcache.setContent("0M");
            ToastUtil.showToast("清理缓存成功");
        } else if (v == mViewDataBinding.nivCheckupdate) {
            List<String> updateContent = new ArrayList<>();
            updateContent.add("1.修复已知Bug。");
            updateContent.add("2.优化用户体验。");
            String url = "https://shouji.sogou.com/apkdl/?gr=opact&tp=pcyunying&id=0";
            UpdateUtil updateUtil = new UpdateUtil(this, "1.0.0", false, updateContent, url);
            updateUtil.alertUpdateAppDialog();
        } else if (v == mViewDataBinding.nivUserprivacy) {
            IntentUtil.startWebViewActivity(this, "用户隐私协议", "https://www.baidu.com/", WebViewActivity.class);
        } else if (v == mViewDataBinding.nivAboutus) {
            ARouterUtil.navigation(MyConstant.AROUTER_AboutUsActivity);
        } else if (v == mViewDataBinding.nivTestroom) {
            ARouterUtil.navigation(MyConstant.AROUTER_TestRoomDatabaseActivity);
        } else if (v == mViewDataBinding.tvLogout) {
            showLogoutDialog();
        }
    }

    private void showLogoutDialog() {
        String content = "确定要退出登录么？";
        final LogoutDialog dialog = new LogoutDialog(this, content, "取消", "确定");
        dialog.setOnClickListener(new OnDialogClickListener() {
            @Override
            public void onClickLeft(Object object) {
                dialog.dismiss();
            }

            @Override
            public void onClickRight(Object object) {
                dialog.dismiss();
                String accountInfo = PrefUtil.getString(SettingActivity.this, PrefUtil.SP_ACCOUNT, "");
                PrefUtil.clear(SettingActivity.this);
                PrefUtil.putString(SettingActivity.this, PrefUtil.SP_ACCOUNT, accountInfo);
                ARouterUtil.navigationwithFlags(MyConstant.AROUTER_LoginActivity, IntentUtil.FLAGS_CLEAK_TASK);
            }
        });
        dialog.show();
        //ProjectUtil.setDialogWindowAttr(dialog);
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
