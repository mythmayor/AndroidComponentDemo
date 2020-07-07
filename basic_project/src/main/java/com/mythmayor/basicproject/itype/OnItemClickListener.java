package com.mythmayor.basicproject.itype;

/**
 * Created by mythmayor on 2020/6/30.
 * 点击每一条目的监听
 */
public interface OnItemClickListener extends BaseListener {

    void onItemClick(int position, Object object);
}
