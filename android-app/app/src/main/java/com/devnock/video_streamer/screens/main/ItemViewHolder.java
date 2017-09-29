package com.devnock.video_streamer.screens.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devnock.video_streamer.R;

/**
 * Created by Sergey Mochulsky on 18.09.2017.
 */

class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTE;

    public ItemViewHolder(View itemView) {
        super(itemView);
        initVive(itemView);
    }

    private void initVive(View itemView) {
        nameTE = (TextView) itemView.findViewById(R.id.name_tv);
    }

    public void bind(String name, ItemAdapter.OnItemClickListener listener) {
        nameTE.setText(name);
        itemView.setOnClickListener(view -> listener.onItemClicked(name));
    }
}
