package com.mythmayor.moduled.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseTitleBarMvpActivity;
import com.mythmayor.basicproject.itype.OnDialogClickListener;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.activity.WebViewActivity;
import com.mythmayor.basicproject.ui.dialog.LogoutDialog;
import com.mythmayor.basicproject.ui.view.NavigationItemView;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.GlideCacheUtil;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.PrefUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UpdateUtil;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.contract.SettingContract;
import com.mythmayor.moduled.presenter.SettingPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/7/10.
 * 设置页面
 */
@Route(path = MyConstant.AROUTER_SettingActivity)
public class SettingActivity extends BaseTitleBarMvpActivity<SettingPresenter> implements SettingContract.View {

    private NavigationItemView nivclearcache;
    private NavigationItemView nivcheckupdate;
    private NavigationItemView nivuserprivacy;
    private NavigationItemView nivaboutus;
    private NavigationItemView nivtestroom;
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
        nivtestroom = (NavigationItemView) findViewById(R.id.niv_testroom);
        tvlogout = (TextView) findViewById(R.id.tv_logout);
    }

    @Override
    public void initSubEvent() {
        nivclearcache.setOnClickListener(this);
        nivcheckupdate.setOnClickListener(this);
        nivuserprivacy.setOnClickListener(this);
        nivaboutus.setOnClickListener(this);
        nivtestroom.setOnClickListener(this);
        tvlogout.setOnClickListener(this);
    }

    @Override
    public void initSubData(Intent intent) {
        nivclearcache.setContent("36M");
        nivcheckupdate.setContent("发现新版本");
        nivcheckupdate.isShowTips(true);
    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.img_arrow_left_white);
        topTitleBar.setTopTitle(true, "设置");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == nivclearcache) {
            GlideCacheUtil.getInstance().clearImageAllCache(this);
            nivclearcache.setContent("0M");
            ToastUtil.showToast("清理缓存成功");
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
            ARouterUtil.navigation(MyConstant.AROUTER_AboutUsActivity);
        } else if (v == nivtestroom) {
            ARouterUtil.navigation(MyConstant.AROUTER_TestRoomDatabaseActivity);
        } else if (v == tvlogout) {
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
