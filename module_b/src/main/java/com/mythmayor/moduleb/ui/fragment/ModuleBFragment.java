package com.mythmayor.moduleb.ui.fragment;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.moduleb.R;
import com.mythmayor.moduleb.databinding.FragmentModuleBBinding;
import com.mythmayor.moduleb.viewmodel.ModuleBFragmentViewModel;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleBFragment)
public class ModuleBFragment extends BaseMvvmFragment<ModuleBFragmentViewModel, FragmentModuleBBinding> {

    //标记Fragment是否初始化完成
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_module_b;
    }

    @Override
    protected int getMvvmLayoutResId() {
        isPrepared = true;
        return R.layout.fragment_module_b;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.textView.setOnClickListener(this);
        mViewDataBinding.ivDrawerLeft.setOnClickListener(this);
        mViewDataBinding.ivDrawerRight.setOnClickListener(this);
        mViewDataBinding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
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
        mViewDataBinding.drawerLeftNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        mViewDataBinding.drawerRightBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewDataBinding.drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        setOpenGestureSwipe();
    }

    @Override
    protected void initMvvmData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mViewDataBinding.textView) {
            ARouterUtil.navigation(MyConstant.AROUTER_ModuleCActivity);
        }else if (v == mViewDataBinding.ivDrawerLeft) {
            openLeftDrawer();
        } else if (v ==  mViewDataBinding.ivDrawerRight) {
            openRightDrawer();
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(false).titleBarMarginTop(R.id.view_blank).init();
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

    //打开左侧抽屉
    public void openLeftDrawer() {
        mViewDataBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    //打开右侧抽屉
    public void openRightDrawer() {
        mViewDataBinding.drawerLayout.openDrawer(GravityCompat.END);
    }

    //关闭手势滑动
    public void setCloseGestureSwipe() {
        mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    //打开手势滑动
    public void setOpenGestureSwipe() {
        mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
