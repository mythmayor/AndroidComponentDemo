package com.mythmayor.mainproject.ui.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.adapter.FragmentAdapter;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.base.LifecycleHandler;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.ui.view.NoScrollViewPager;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.PrefUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.MainContract;
import com.mythmayor.mainproject.presenter.MainPresenter;
import com.mythmayor.mainproject.ui.fragment.AFragment;
import com.mythmayor.mainproject.ui.fragment.BFragment;
import com.mythmayor.mainproject.ui.fragment.CFragment;
import com.mythmayor.mainproject.ui.fragment.DFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 * 主页面
 */
@Route(path = "/mainproject/MainActivity")
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View, ViewPager.OnPageChangeListener {

    private NoScrollViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton rbtab01, rbtab02, rbtab03, rbtab04;

    private AFragment mAFragment;
    private BFragment mBFragment;
    private CFragment mCFragment;
    private DFragment mDFragment;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private final long EXIT_APP_BACK_PRESSED_INTERVAL = 1500;
    private long mCurrBackPressTimeMillis;
    private long mPrevBackPressTimeMillis;
    private static final int WHAT_MESSAGE_GETUSERINFO = 1;

    @SuppressLint("HandlerLeak")
    private LifecycleHandler mHandler = new LifecycleHandler(new LifecycleOwner() {
        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return MainActivity.this.getLifecycle();
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case WHAT_MESSAGE_GETUSERINFO:
                    getUserInfo();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbtab01 = (RadioButton) findViewById(R.id.rb_tab01);
        rbtab02 = (RadioButton) findViewById(R.id.rb_tab02);
        rbtab03 = (RadioButton) findViewById(R.id.rb_tab03);
        rbtab04 = (RadioButton) findViewById(R.id.rb_tab04);
    }

    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_tab01) {
                    mViewPager.setCurrentItem(0, false);
                } else if (checkedId == R.id.rb_tab02) {
                    mViewPager.setCurrentItem(1, false);
                } else if (checkedId == R.id.rb_tab03) {
                    mViewPager.setCurrentItem(2, false);
                } else if (checkedId == R.id.rb_tab04) {
                    mViewPager.setCurrentItem(3, false);
                }
            }
        });
    }

    @Override
    protected void initData(Intent intent) {
        PrefUtil.putBoolean(this, PrefUtil.SP_IS_USER_LOGIN, true);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        mHandler.sendEmptyMessageDelayed(WHAT_MESSAGE_GETUSERINFO, 3000);
        initViewPager();
    }

    private void initViewPager() {
        mAFragment = new AFragment();
        mBFragment = new BFragment();
        mCFragment = new CFragment();
        mDFragment = new DFragment();
        mFragmentList.add(mAFragment);
        mFragmentList.add(mBFragment);
        mFragmentList.add(mCFragment);
        mFragmentList.add(mDFragment);
        //初始化Adapter
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        //设置ViewPager的缓存页面的个数
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0, false);
        rbtab01.setChecked(true);
    }

    private void getUserInfo() {
        LoginRequest accountInfo = UserInfoManager.getAccountInfo(this);
        if (accountInfo != null) {
            UserInfoRequest request = new UserInfoRequest(accountInfo.getUsername(), accountInfo.getPassword());
            mPresenter.getUserInfo(request);
        }
    }

    @Override
    public void showLoading() {
        ToastUtil.showToast(this, "正在获取用户信息...");
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(String errMessage) {
        ToastUtil.showToast(this, "获取用户信息失败，原因：" + errMessage);
    }

    @Override
    public void onSuccess(BaseResponse baseResp) {
        UserInfoResponse resp = (UserInfoResponse) baseResp;
        ToastUtil.showToast(this, "获取用户信息成功！" + HttpUtil.mGson.toJson(resp));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exitApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    private void exitApp() {
        mCurrBackPressTimeMillis = System.currentTimeMillis();
        if (mCurrBackPressTimeMillis - mPrevBackPressTimeMillis < EXIT_APP_BACK_PRESSED_INTERVAL) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            finish();
        } else {
            mPrevBackPressTimeMillis = mCurrBackPressTimeMillis;
            ToastUtil.showToast(getApplicationContext(), "再按一次退出软件");
        }
    }

    private void destroy() {
        mViewPager.removeOnPageChangeListener(this);
    }

    @Override
    public void onNetworkListener(int status) {
        if (status == NetworkBroadcastReceiver.NETWORK_NONE) {//无网络连接
            LogUtil.d("无网络连接");
        } else if (status == NetworkBroadcastReceiver.NETWORK_MOBILE) {//移动网络连接
            LogUtil.d("移动网络连接");
        } else if (status == NetworkBroadcastReceiver.NETWORK_WIFI) {//无线网络连接
            LogUtil.d("无线网络连接");
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
