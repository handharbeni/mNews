package com.mdirect.mnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;

/**
 * Created by Beni on 28/03/2018.
 */

public class AdapterItemNews extends RecyclerView.Adapter<AdapterItemNews.ViewHolder> {
    private Context context;
    private ArrayList<DataGetAllPost> listNews;

    public AdapterItemNews(Context context, ArrayList<DataGetAllPost> listNews){
        this.context = context;
        this.listNews = listNews;
    }

    @Override
    public AdapterItemNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_news, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemNews.ViewHolder holder, int position) {
        final DataGetAllPost data = listNews.get(position);
        holder.titleNews.setText(data.getTitle());
        holder.dateNews.setText(data.getCreatedAt());
        Glide.with(context).load(data.getFeaturedImg()).into(holder.imgNews);
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgNews)
        ImageView imgNews;

        @BindView(R.id.titleNews)
        TextView titleNews;

        @BindView(R.id.dateNews)
        TextView dateNews;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
