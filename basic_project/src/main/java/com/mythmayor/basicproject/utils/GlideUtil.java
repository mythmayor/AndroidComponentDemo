package com.mythmayor.basicproject.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mythmayor.basicproject.BasicProjectApplication;
import com.mythmayor.basicproject.itype.OnGlideLoadListener;

import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 * Glide图片框架使用工具类
 */
public class GlideUtil {
    private static int mIndex;
    private static int mErrorCount;
    private static int mSuccessCount;

    /**
     * 预加载图片
     */
    public static void preloadImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(BasicProjectApplication.getInstance().getContext())
                .load(url)
                .apply(options)
                .preload();
    }

    /**
     * 加载图片
     * @param url 图片路径
     * @param imageView ImageView对象
     */
    public static void loadImage(String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url) || null == imageView) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(BasicProjectApplication.getInstance().getContext())
                .load(url)
                .apply(options)
                .into(new SimpleTarget<Drawable>() {//加上这段代码可以解决加载不显示的问题
                    @Override
                    public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(drawable); //显示图片
                    }
                });
    }

    /**
     * 加载图片
     * @param url 图片路径
     * @param errImageId 错误图片ID
     * @param imageView ImageView对象
     */
    public static void loadImage(String url, int errImageId, final ImageView imageView) {
        if (TextUtils.isEmpty(url) || null == imageView) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(errImageId)
                .error(errImageId)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(BasicProjectApplication.getInstance().getContext())
                .load(url)
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(drawable); //显示图片
                    }
                });
    }

    /**
     * 加载一组图片
     */
    public static void preloadGroupImage(List<String> imageList, OnGlideLoadListener listener) {
        mIndex = 0;
        mErrorCount = 0;
        mSuccessCount = 0;
        preloadImage(imageList, listener);
    }

    private static void preloadImage(final List<String> imageList, final OnGlideLoadListener listener) {
        if (mIndex < imageList.size()) {
            String url = imageList.get(mIndex);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA);
            Glide.with(BasicProjectApplication.getInstance().getContext())
                    .load(url)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            mErrorCount++;
                            mIndex++;
                            preloadImage(imageList, listener);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            mSuccessCount++;
                            mIndex++;
                            preloadImage(imageList, listener);
                            return false;
                        }
                    })
                    .preload();

        } else if (mIndex == imageList.size()) {
            if (mSuccessCount == imageList.size()) {//图片预加载成功
                listener.onSuccess(mSuccessCount, mErrorCount);
            } else {//图片预加载异常
                listener.onError(mSuccessCount, mErrorCount);
            }
        }
    }

    /**
     * 获取网络图片的宽和高
     *
     * @param pathUrl
     * @param data
     */
    public static void getImageWidthHeight(String pathUrl, final GetImageData data) {
        //获取图片真正的宽高
        Glide.with(BasicProjectApplication.getInstance().getContext())
                .load(pathUrl)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                        Bitmap bitmap = drawableToBitmap(drawable);
                        data.sendData(bitmap.getWidth(), bitmap.getHeight());
                    }
                });
    }

    public interface GetImageData {
        void sendData(int width, int height);
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 销毁Glide
     */
    public static void destroy() {
        //Glide.with(MyApplication.getContext()).pauseRequests();
        GlideCacheUtil.getInstance().clearImageAllCache(BasicProjectApplication.getInstance().getContext());
    }
}
