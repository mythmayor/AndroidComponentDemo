package com.mythmayor.basicproject.itype;

/**
 * Created by mythmayor on 2020/6/30.
 * Glide图片框架加载图片监听
 */
public interface OnGlideLoadListener extends BaseListener {

    void onSuccess(int successCount, int errorCount);

    void onError(int successCount, int errorCount);
}
