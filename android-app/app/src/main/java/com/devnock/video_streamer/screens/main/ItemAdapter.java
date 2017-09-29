package com.devnock.video_streamer.screens.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devnock.video_streamer.R;

import java.util.ArrayList;

/**
 * Created by Sergey Mochulsky on 18.09.2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private final ArrayList<String> names;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClicked(String fileName);
    }

    public ItemAdapter(ArrayList<String> names, OnItemClickListener listener){
        this.names = names;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_card, parent, false);
        return new ItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(names.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
