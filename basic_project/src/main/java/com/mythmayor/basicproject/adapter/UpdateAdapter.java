package com.mythmayor.basicproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.viewholder.UpdateViewHolder;

import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 */
public class UpdateAdapter extends RecyclerView.Adapter<UpdateViewHolder> {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public UpdateAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_update, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpdateViewHolder holder, final int position) {
        holder.tvupdateinfo.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void refreshDatas(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
