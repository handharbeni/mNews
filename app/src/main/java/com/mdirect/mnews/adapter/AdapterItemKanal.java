package com.mdirect.mnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdirect.mnews.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;

public class AdapterItemKanal extends RecyclerView.Adapter<AdapterItemKanal.ViewHolder>{
    private ArrayList<DataMenus> listKanal;
    private Context context;

    public AdapterItemKanal(ArrayList<DataMenus> listKanal, Context context) {
        this.listKanal = listKanal;
        this.context = context;
    }

    @Override
    public AdapterItemKanal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_kanal, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemKanal.ViewHolder holder, int position) {
        final DataMenus dataMenus = listKanal.get(position);
        holder.titleKanal.setText(dataMenus.getName());
    }

    @Override
    public int getItemCount() {
        return listKanal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mainItemKanal)
        RelativeLayout mainItemKanal;
        @BindView(R.id.titleKanal)
        TextView titleKanal;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
