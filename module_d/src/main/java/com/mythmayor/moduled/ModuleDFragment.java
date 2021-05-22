package com.mythmayor.moduled;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.engine.CacheResourcesEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.listener.OnCustomCameraInterfaceListener;
import com.luck.picture.lib.listener.OnCustomImagePreviewCallback;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureSelectorUIStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.bean.EventBusBean;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.ui.view.NavigationItemView;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.GlideEngine;
import com.mythmayor.basicproject.utils.GlideUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.PermissionManager;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.moduled.contract.ModuleDFragmentContract;
import com.mythmayor.moduled.presenter.ModuleDFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleDFragment)
public class ModuleDFragment extends BaseMvpFragment<ModuleDFragmentPresenter> implements ModuleDFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;

    private ImageView ivavatar;
    private TextView tvname;
    private LinearLayout llpersonalinfo;
    private NavigationItemView nivmessage;
    private NavigationItemView nivfeedback;
    private NavigationItemView nivmodifypwd;
    private NavigationItemView nivsetting;
    //private MainActivity mMainActivity;
    private String[] mPermissionArray = new String[]{
            PermissionManager.PERMISSION_LOCATION
    };

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册EventBus
        isPrepared = true;
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_module_d;
    }

    @Override
    protected void initView(View view) {
        ivavatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tvname = (TextView) view.findViewById(R.id.tv_name);
        llpersonalinfo = (LinearLayout) view.findViewById(R.id.ll_personalinfo);
        nivmessage = (NavigationItemView) view.findViewById(R.id.niv_message);
        nivfeedback = (NavigationItemView) view.findViewById(R.id.niv_feedback);
        nivmodifypwd = (NavigationItemView) view.findViewById(R.id.niv_modifypwd);
        nivsetting = (NavigationItemView) view.findViewById(R.id.niv_setting);
        //mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initEvent() {
        llpersonalinfo.setOnClickListener(this);
        ivavatar.setOnClickListener(this);
        nivmessage.setOnClickListener(this);
        nivfeedback.setOnClickListener(this);
        nivmodifypwd.setOnClickListener(this);
        nivsetting.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new ModuleDFragmentPresenter();
        mPresenter.attachView(this);
        LoginResponse.DataBean loginInfo = UserInfoManager.getLoginInfo(getActivity());
        if (loginInfo != null) {
            tvname.setText(loginInfo.getNickname());
        } else {
            tvname.setText("--");
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//取消注册EventBus
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean bean) {//EventBus订阅方法，当接收到事件的时候，会调用该方法
        if (MyConstant.EVENT_KEY_NOTIFICATION.equals(bean.getKey())) {
            nivmessage.isShowTips(false);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == llpersonalinfo) {
            ARouterUtil.navigation(MyConstant.AROUTER_PersonalInfoActivity);
        } else if (v == ivavatar) {
            //selectPicture();
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .setPictureUIStyle(PictureSelectorUIStyle.ofDefaultStyle())
                    .setPictureWindowAnimationStyle(PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle())// 自定义相册启动退出动画
                    .isWeChatStyle(true)// 是否开启微信图片选择风格
                    .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                    .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                    .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                    .setCaptureLoadingColor(ContextCompat.getColor(getContext(), R.color.color_blue))
                    .maxSelectNum(9)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .maxVideoSelectNum(1) // 视频最大选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                    .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                    .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                    .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                    .isPreviewImage(true)// 是否可预览图片
                    .isPreviewVideo(true)// 是否可预览视频
                    .isEnablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isEnableCrop(false)// 是否裁剪
                    .isCompress(false)// 是否压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .isOpenClickSound(false)// 是否开启点击声音
                    //.selectionData(mAdapter.getData())// 是否传入已选图片
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            for (LocalMedia media : result) {
                                LogUtil.v("是否压缩:" + media.isCompressed());
                                LogUtil.v("压缩:" + media.getCompressPath());
                                LogUtil.d("原图:" + media.getPath());
                                LogUtil.v("绝对路径:" + media.getRealPath());
                                LogUtil.v("是否裁剪:" + media.isCut());
                                LogUtil.v("裁剪:" + media.getCutPath());
                                LogUtil.v("是否开启原图:" + media.isOriginal());
                                LogUtil.v("原图路径:" + media.getOriginalPath());
                                LogUtil.v("Android Q 特有Path:" + media.getAndroidQToPath());
                                LogUtil.v("宽高: " + media.getWidth() + "x" + media.getHeight());
                                LogUtil.v("Size: " + media.getSize());
                                LogUtil.d("onResult: " + media.toString());
                                String path = media.getPath();
                                GlideUtil.loadCircleImage(PictureMimeType.isContent(path) && !media.isCut() && !media.isCompressed() ? Uri.parse(path) : path, ivavatar);
                                LogUtil.d("======================================================");
                                // 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
                            }
                        }

                        @Override
                        public void onCancel() {
                            LogUtil.d("PictureSelector Canceled");
                        }
                    });
        } else if (v == nivmessage) {
            ARouterUtil.navigation(MyConstant.AROUTER_NotificationActivity);
        } else if (v == nivfeedback) {
            ARouterUtil.navigation(MyConstant.AROUTER_FeedbackActivity);
        } else if (v == nivmodifypwd) {
            ARouterUtil.navigation(MyConstant.AROUTER_ChangePasswordActivity);
        } else if (v == nivsetting) {
            ARouterUtil.navigation(MyConstant.AROUTER_SettingActivity);
        }
    }

    private void selectPicture() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())//相册 媒体类型 PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
                //.openCamera()//单独使用相机 媒体类型 PictureMimeType.ofImage()、ofVideo()
                .theme(R.style.picture_WeChat_style)// xml样式配制 R.style.picture_default_style、picture_WeChat_style or 更多参考Demo
                .imageEngine(GlideEngine.createGlideEngine())// 图片加载引擎 需要 implements ImageEngine接口
                .selectionMode(PictureConfig.MULTIPLE)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
                .isPageStrategy(true)//开启分页模式，默认开启另提供两个参数；pageSize每页总数；isFilterInvalidFile是否过滤损坏图片
                .isSingleDirectReturn(true)//PictureConfig.SINGLE模式下是否直接返回
                .isWeChatStyle(true)//开启R.style.picture_WeChat_style样式
                .setPictureStyle(new PictureParameterStyle())//动态自定义相册主题
                .setPictureCropStyle(new PictureCropParameterStyle())//动态自定义裁剪主题
                .setPictureWindowAnimationStyle(new PictureWindowAnimationStyle())//相册启动退出动画
                .isCamera(true)//列表是否显示拍照按钮
                .isZoomAnim(true)//图片选择缩放效果
                .imageFormat(PictureMimeType.JPEG)//拍照图片格式后缀,默认jpeg, PictureMimeType.PNG，Android Q使用PictureMimeType.PNG_Q
                .maxSelectNum(9)//最大选择数量,默认9张
                .minSelectNum(1)// 最小选择数量
                .maxVideoSelectNum(9)//视频最大选择数量
                .minVideoSelectNum(1)//视频最小选择数量
                .videoMaxSecond(60 * 60 * 5)// 查询多少秒以内的视频
                .videoMinSecond(60)// 查询多少秒以内的视频
                .imageSpanCount(4)//列表每行显示个数
                .openClickSound(false)//是否开启点击声音
                .selectionMedia(null)//是否传入已选图片
                .recordVideoSecond(60)//录制视频秒数 默认60s
                .filterMinFileSize(60L) // 过滤最小的文件
                .filterMaxFileSize(60L * 60 * 5) // 过滤最大的文件
                .queryMimeTypeConditions(PictureMimeType.ofJPEG()) // 只查询什么类型的文件
                .previewEggs(true)//预览图片时是否增强左右滑动图片体验
                //.cropCompressQuality()// 注：已废弃 改用cutOutQuality()
                .isGif(true)//是否显示gif
                .previewImage(true)//是否预览图片
                .previewVideo(true)//是否预览视频
                .enablePreviewAudio(true)//是否预览音频
                .enableCrop(true)//是否开启裁剪
                //.cropWH()// 裁剪宽高比,已废弃，改用. cropImageWideHigh()方法
                .cropImageWideHigh(50, 50)// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .withAspectRatio(1, 1)//裁剪比例
                .cutOutQuality(100)// 裁剪输出质量 默认100
                .freeStyleCropEnabled(true)//裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否开启圆形裁剪
                .setCircleDimmedColor(Color.LTGRAY)//设置圆形裁剪背景色值
                .setCircleDimmedBorderColor(Color.GREEN)//设置圆形裁剪边框色值
                .setCircleStrokeWidth(3)//设置圆形裁剪边框粗细
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .rotateEnabled(false)//裁剪是否可旋转图片
                .scaleEnabled(false)//裁剪是否可放大缩小图片
                .isDragFrame(false)//是否可拖动裁剪框(固定)
                .hideBottomControls(false)//显示底部uCrop工具栏
                .basicUCropConfig(null)//对外提供ucrop所有的配制项
                .compress(true)//是否压缩
                .compressFocusAlpha(true)//压缩后是否保持图片的透明通道
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .videoQuality(0)//视频录制质量 0 or 1
                .compressQuality(0)//图片压缩后输出质量
                .synOrAsy(false)//开启同步or异步压缩
                //.queryMaxFileSize()//查询指定大小内的图片、视频、音频大小，单位M
                .compressSavePath("")//自定义压缩图片保存地址，注意Q版本下的适配
                //.sizeMultiplier()//glide加载大小，已废弃
                //.glideOverride()//glide加载宽高，已废弃
                .isMultipleSkipCrop(false)//多图裁剪是否支持跳过
                .isMultipleRecyclerAnimation(true)// 多图裁剪底部列表显示动画效果
                .querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())//只查询指定后缀的资源，PictureMimeType.ofJPEG() ...
                .isReturnEmpty(true)//未选择数据时按确定是否可以退出
                .isAndroidQTransform(true)//Android Q版本下是否需要拷贝文件至应用沙盒内
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//屏幕旋转方向 ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ...
                .isOriginalImageControl(true)//开启原图选项
                .bindCustomPlayVideoCallback(null)//自定义视频播放拦截
                .bindCustomCameraInterfaceListener(new OnCustomCameraInterfaceListener() {
                    @Override
                    public void onCameraClick(Context context, PictureSelectionConfig config, int type) {

                    }
                })//自定义拍照回调接口
                .bindCustomPreviewCallback(new OnCustomImagePreviewCallback() {
                    @Override
                    public void onCustomPreviewCallback(Context context, List previewData, int currentPosition) {

                    }
                })// 自定义图片预览回调接口
                .cameraFileName("AvatarImage")//自定义拍照文件名，如果是相册内拍照则内部会自动拼上当前时间戳防止重复
                .renameCompressFile("AvatarImage")//自定义压缩文件名，多张压缩情况下内部会自动拼上当前时间戳防止重复
                .renameCropFileName("AvatarImage")//自定义裁剪文件名，多张裁剪情况下内部会自动拼上当前时间戳防止重复
                .setRecyclerAnimationMode(AnimationType.ALPHA_IN_ANIMATION)//列表动画效果,AnimationType.ALPHA_IN_ANIMATION、SLIDE_IN_BOTTOM_ANIMATION
                .isUseCustomCamera(true)// 开启自定义相机
                .setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 自定义相机按钮状态,CustomCameraView.BUTTON_STATE_BOTH
                .setLanguage(LanguageConfig.CHINESE)//国际化语言 LanguageConfig.CHINESE、ENGLISH、JAPAN等
                .isWithVideoImage(false)//图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)//选择条件达到阀时列表是否启用蒙层效果
                .isAutomaticTitleRecyclerTop(true)//图片列表超过一屏连续点击顶部标题栏快速回滚至顶部
                .loadCacheResourcesCallback(new CacheResourcesEngine() {
                    @Override
                    public String onCachePath(Context context, String url) {
                        return null;
                    }
                })//获取ImageEngine缓存图片，参考Demo
                .setOutputCameraPath("")// 自定义相机输出目录只针对Android Q以下版本，具体参考Demo
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.i("取消了图片选择");
                    }
                });//结果回调分两种方式onActivityResult()和OnResultCallbackListener方式
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
