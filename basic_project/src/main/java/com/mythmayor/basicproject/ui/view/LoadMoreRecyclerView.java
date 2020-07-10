package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/6/30.
 * 自定义RecyclerView，支持底部加载更多
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private OnRecyerViewLastItemListener loadMoreListener;
    private BaseAdapter mAdapter;

    private static final int TYPE_NOMAL = 0; //正常的
    private static final int TYPE_FOOTER = 1; //底部加载更多
    public static final int NO_MORE_DATAS = 2;  //没有更多数据了
    public static final int IS_LOADING_DATAS = 3; //正在加载数据...
    private static final int FOOTER_IS_EMPTY = 4;  //底部footer为空
    private static final int TYPE_HEADER = 5; //头部
    public static final int NO_DATAS = 6; //暂无数据

    private boolean isLoadMoreEnable = false; //是否开启底部自动加载更多

    private int current_footer_state = 4; //底部显示状态  默认显示为空..

    private boolean isHeaderEnable = false;

    private int mHeaderViewResId = 0;
    private boolean isScroll;//记录是否进行了滑动操作

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        LayoutManager layoutManager = getLayoutManager();
        //得到当前屏幕显示的最后一个item的View
        View lastChildView = layoutManager.getChildAt(getLayoutManager().getChildCount() - 1);
        //获取当前显示的最后一个item的View的底部bootm值
        int lastChildBottom = 0;
        int lastPosition = 0;
        //这里要做下判断，貌似是Recyclerview被完全遮挡（也可能是挤没了），比如弹出键盘的时候，会为null
        if (null != lastChildView) {
            lastChildBottom = lastChildView.getBottom();
            //获取当前屏幕显示的最后一个item的position
            lastPosition = layoutManager.getPosition(lastChildView);
        }
        //获取RecylerView底部Bootom的值
        int recylerViewBottom = getBottom();
        //如果相等，则判断为滑到底部
        if (lastChildBottom == recylerViewBottom && lastPosition == getLayoutManager().getItemCount() - 1) {

            //业务代码
            //底部自动加载更多数据
            //如果current_footer_state == NO_DATAS  没有数据也就不用加载更多数据了
            //或者current_footer_state == NO_MORE_DATAS  没有更多数据了也就不用继续加载了
            //所以我添加了这两个判断条件
            if (loadMoreListener != null
                    && isLoadMoreEnable
                    && current_footer_state != NO_DATAS
                    && current_footer_state != NO_MORE_DATAS) {
                isScroll = true;
                this.loadMoreListener.loadMore();
            }
        }
    }

    /**
     * 获取当前屏幕可显示的最后一个item的position
     *
     * @return
     */
    private int getLastVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return layoutManager.getPosition(layoutManager.getChildAt(getLayoutManager().getChildCount() - 1));
    }

    public void setOnRecylerViewLastItemListener(OnRecyerViewLastItemListener listener) {
        this.loadMoreListener = listener;
    }

    public interface OnRecyerViewLastItemListener {
        void loadMore(); //加载更多
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (null != adapter) {
            mAdapter = new BaseAdapter(adapter);
        }
        super.swapAdapter(mAdapter, true);
    }

    private class BaseAdapter extends Adapter<ViewHolder> {
        private final Adapter baseAdapter;

        BaseAdapter(Adapter adapter) {
            this.baseAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            int headerPosition = 0;
            int footerPosition = getItemCount() - 1;
            if (headerPosition == position && isHeaderEnable && mHeaderViewResId > 0) {
                return TYPE_HEADER;
            }
            if (footerPosition == position && isLoadMoreEnable) {
                return TYPE_FOOTER;
            }
            return TYPE_NOMAL;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                View headerView = LayoutInflater.from(parent.getContext()).inflate(mHeaderViewResId, parent, false);
                return new HeaderViewHolder(headerView);
            } else if (viewType == TYPE_FOOTER) {
                View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_root, parent, false);
                return new FooterViewHolder(footerView);
            } else {
                return baseAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == TYPE_NOMAL) {//0
                if (isHeaderEnable) {
                    position--;
                }
                baseAdapter.onBindViewHolder(holder, position);
            } else if (type == TYPE_FOOTER) {//1
                setFooterViewTextState(holder);
//            } else if (type == TYPE_HEADER) {
//                //头部数据处理
//                holder.itemView.setOnClickListener(new OnMyClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getContext(), "你点击了头部", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }

        @Override
        public int getItemCount() {
            int count = baseAdapter.getItemCount();
            if (isHeaderEnable) count++;
            if (isLoadMoreEnable) count++;
            return count;
        }
    }

    //根据加载完后的不同结果，底部添加不同状态的View
    private void setFooterViewTextState(ViewHolder holder) {
        ViewGroup parent = (ViewGroup) holder.itemView.findViewById(R.id.footer_root);
        parent.setVisibility(View.VISIBLE);
        View view = null;
        //移除之前添加过的View
        parent.removeAllViews();
        parent.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //解析当前需要显示的View
        if (current_footer_state == NO_MORE_DATAS) {  //没有更多数据
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view_no_more_datas, parent, false);
        } else if (current_footer_state == IS_LOADING_DATAS) {  //正在加载数据
            //判断footview是滑出的,还是删除item自动显示的
            if (isScroll) {
                isScroll = false;
            } else {
                loadMoreListener.loadMore();
            }
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view_isloading, parent, false);
        } else if (current_footer_state == FOOTER_IS_EMPTY) {  //隐藏底部View
            parent.setVisibility(View.GONE);
        } else if (current_footer_state == NO_DATAS) { //暂无数据
            if (footerViewId != 0) {
                parent.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view = LayoutInflater.from(parent.getContext()).inflate(footerViewId, parent, false);
            } else {
                parent.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvlist_no_datas, parent, false);
            }
        }
        //add当前要显示的View
        if (null != view)
            parent.addView(view);
    }

    /**
     * 设置是否显示头部view
     *
     * @param enable
     */
    public void setHeaderEnable(boolean enable) {
        this.isHeaderEnable = enable;
    }

    /**
     * 设置底部是否显示自动加载更多
     */
    public void setFooterLoadMoreEnable() {
        this.isLoadMoreEnable = true;
    }

    /**
     * 底部显示加载状态
     *
     * @param resId
     */
    public void setFooterViewState(int resId) {
        this.current_footer_state = resId;
    }

    int footerViewId;

    public void setFooterViewId(int id) {
        footerViewId = id;
    }

    /**
     * 添加头部View
     *
     * @param resId
     */
    public void addHeaderView(int resId) {

        this.mHeaderViewResId = resId;
    }

    private class HeaderViewHolder extends ViewHolder {

        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FooterViewHolder extends ViewHolder {

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void notifyDataSetChanged() {
        getAdapter().notifyDataSetChanged();
    }
}
