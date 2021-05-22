package com.mythmayor.modulea;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.SearchBar;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.modulea.R;
import com.mythmayor.modulea.databinding.FragmentModuleABinding;
import com.mythmayor.modulea.viewmodel.ModuleAFragmentViewModel;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleAFragment)
public class ModuleAFragment extends BaseMvvmFragment<ModuleAFragmentViewModel, FragmentModuleABinding> {

    //标记Fragment是否初始化完成
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        LogUtil.d("");
    }

    @Override
    protected int getMvvmLayoutResId() {
        isPrepared = true;
        return R.layout.fragment_module_a;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.searchBar.setOnSearchBarClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtil.navigation(MyConstant.AROUTER_SearchActivity);
            }
        });
        mViewDataBinding.searchBar.setOnSearchListener(new SearchBar.OnSearchListener() {
            @Override
            public void onInputFinished(String input) {
                ToastUtil.showToast("input=" + input);
            }
        });
    }

    @Override
    protected void initMvvmData() {
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
}
