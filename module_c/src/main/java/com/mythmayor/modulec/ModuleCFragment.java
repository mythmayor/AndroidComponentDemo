package com.mythmayor.modulec;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.modulec.R;
import com.mythmayor.modulec.databinding.FragmentModuleCBinding;
import com.mythmayor.modulec.viewmodel.ModuleCFragmentViewModel;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleCFragment)
public class ModuleCFragment extends BaseMvvmFragment<ModuleCFragmentViewModel, FragmentModuleCBinding> {

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
        return R.layout.fragment_module_c;
    }

    @Override
    protected void initMvvmEvent() {

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
