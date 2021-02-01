package com.mythmayor.moduleb.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.moduleb.R;
import com.mythmayor.moduleb.contract.ModuleBFragmentContract;
import com.mythmayor.moduleb.presenter.ModuleBFragmentPresenter;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleBFragment)
public class ModuleBFragment extends BaseMvpFragment<ModuleBFragmentPresenter> implements ModuleBFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    private TextView textView;
    private ImageView ivdrawerleft;
    private ImageView ivdrawerright;
    //private MainActivity mMainActivity;
    private DrawerLayout mDrawerLayout;
    private NavigationView drawerleftnavigation;
    private Button drawerrightbtnclose;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_module_b;
    }

    @Override
    protected void initView(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
        ivdrawerleft = (ImageView) view.findViewById(R.id.iv_drawer_left);
        ivdrawerright = (ImageView) view.findViewById(R.id.iv_drawer_right);
        //mMainActivity = (MainActivity) getActivity();
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawerleftnavigation = (NavigationView) view.findViewById(R.id.drawer_left_navigation);
        drawerrightbtnclose = (Button) view.findViewById(R.id.drawer_right_btn_close);
    }

    @Override
    protected void initEvent() {
        textView.setOnClickListener(this);
        ivdrawerleft.setOnClickListener(this);
        ivdrawerright.setOnClickListener(this);
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
        setOpenGestureSwipe();
    }

    @Override
    protected void initData() {
        mPresenter = new ModuleBFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == textView) {
            ARouterUtil.navigation(MyConstant.AROUTER_ModuleCActivity);
        } else if (v == ivdrawerleft) {
            openLeftDrawer();
        } else if (v == ivdrawerright) {
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
