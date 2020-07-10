package com.mythmayor.basicproject.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/6/30.
 */
public class UpdateViewHolder extends RecyclerView.ViewHolder {

    public TextView tvupdateinfo;

    public UpdateViewHolder(View itemView) {
        super(itemView);
        tvupdateinfo = (TextView) itemView.findViewById(R.id.tv_updateinfo);
    }
}
