package com.mythmayor.basicproject.itype;

/**
 * Created by mythmayor on 2020/6/30.
 * 点击Dialog按钮的监听
 */
public interface OnDialogClickListener extends BaseListener {

    /**
     * 点击了左侧的按钮
     */
    void onClickLeft(Object object);

    /**
     * 点击了右侧的按钮
     */
    void onClickRight(Object object);
}
