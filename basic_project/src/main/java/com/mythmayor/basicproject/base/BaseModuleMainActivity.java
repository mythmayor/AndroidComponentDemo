package com.mythmayor.basicproject.base;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.adapter.FragmentAdapter;
import com.mythmayor.basicproject.ui.view.NoScrollViewPager;
import com.mythmayor.basicproject.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2021/2/1.
 * 主页面Activity基类
 */
public abstract class BaseModuleMainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private NoScrollViewPager mViewPager;
    private Fragment mFragment;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private final long EXIT_APP_BACK_PRESSED_INTERVAL = 1500;
    private long mCurrBackPressTimeMillis;
    private long mPrevBackPressTimeMillis;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base_module_main;
    }

    @Override
    protected void initView() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager_modulea);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData(Intent intent) {
        mFragment = getFragment();
        if(mFragment ==null) {
            ToastUtil.showToast("getFragment() return null");
            return;
        }
        mFragmentList.add(mFragment);
        //初始化Adapter
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        //设置ViewPager的缓存页面的个数
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0, false);
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
            ToastUtil.showToast("再按一次退出软件");
        }
    }

    private void destroy() {
        mViewPager.removeOnPageChangeListener(this);
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

    public abstract Fragment getFragment();
}
