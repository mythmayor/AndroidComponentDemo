package com.mythmayor.mainproject.ui.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.adapter.FragmentAdapter;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.base.LifecycleHandler;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.NoScrollViewPager;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.PrefUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
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
    private DrawerLayout mDrawerLayout;
    private NavigationView drawerleftnavigation;
    private Button drawerrightbtnclose;

    private AFragment mAFragment;
    private BFragment mBFragment;
    private CFragment mCFragment;
    private DFragment mDFragment;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private final long EXIT_APP_BACK_PRESSED_INTERVAL = 1500;
    private long mCurrBackPressTimeMillis;
    private long mPrevBackPressTimeMillis;
    private static final int WHAT_EVENT = 1;

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
                case WHAT_EVENT:
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
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbtab01 = (RadioButton) findViewById(R.id.rb_tab01);
        rbtab02 = (RadioButton) findViewById(R.id.rb_tab02);
        rbtab03 = (RadioButton) findViewById(R.id.rb_tab03);
        rbtab04 = (RadioButton) findViewById(R.id.rb_tab04);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerleftnavigation = (NavigationView) findViewById(R.id.drawer_left_navigation);
        drawerrightbtnclose = (Button) findViewById(R.id.drawer_right_btn_close);
    }

    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_tab01) {
                    mViewPager.setCurrentItem(0, false);
                    setCloseGestureSwipe();
                } else if (checkedId == R.id.rb_tab02) {
                    mViewPager.setCurrentItem(1, false);
                    setOpenGestureSwipe();
                } else if (checkedId == R.id.rb_tab03) {
                    mViewPager.setCurrentItem(2, false);
                    setCloseGestureSwipe();
                } else if (checkedId == R.id.rb_tab04) {
                    mViewPager.setCurrentItem(3, false);
                    setCloseGestureSwipe();
                }
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                LogUtil.d("滑动中");
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                LogUtil.d("打开");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                LogUtil.d("关闭");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                LogUtil.d("状态改变");
            }
        });
        drawerleftnavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String title = (String) item.getTitle();
                if ("Gallery".equals(title) || "Tools".equals(title) || "Share".equals(title)) {
                    ToastUtil.showToast("点击了---" + title);
                } else {
                    ToastUtil.showToastAtPosition("点击了---" + title, ToastUtil.GRAVITY_TOP);
                }
                return false;
            }
        });
        drawerrightbtnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    protected void initData(Intent intent) {
        PrefUtil.putBoolean(this, PrefUtil.SP_IS_USER_LOGIN, true);
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

    //打开左侧抽屉
    public void openLeftDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    //打开右侧抽屉
    public void openRightDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.END);
    }

    //关闭手势滑动
    public void setCloseGestureSwipe() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    //打开手势滑动
    public void setOpenGestureSwipe() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
