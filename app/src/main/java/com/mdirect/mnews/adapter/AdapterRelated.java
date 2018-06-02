package com.mdirect.mnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.R;
import com.mdirect.mnews.utils.ClickListener;
import com.mdirect.mnews.utils.DateFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post.Related;

/**
 * Created by Beni on 11/04/2018.
 */

public class AdapterRelated extends RecyclerView.Adapter<AdapterRelated.ViewHolder> {
    Context context;
    List<Related> relatedList;
    private ClickListener clickListener;
    private DateFormatter dateFormatter;

    public AdapterRelated(Context context, List<Related> relatedList, ClickListener clickListener) {
        this.context = context;
        this.relatedList = relatedList;
        this.clickListener = clickListener;
        this.dateFormatter = new DateFormatter();
    }

    @NonNull
    @Override
    public AdapterRelated.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_item_related, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRelated.ViewHolder holder, int position) {
        final Related related = relatedList.get(position);
        Glide.with(context).load(related.getFeaturedImg()).into(holder.imgRelated);
        holder.txtTitleRelated.setText(related.getTitle());
        holder.txtTanggal.setText(dateFormatter.format(related.getCreatedAt()));
        holder.relatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.clicked(related.getSlugId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return relatedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.relatedView)
        LinearLayout relatedView;

        @BindView(R.id.imgRelated)
        ImageView imgRelated;

        @BindView(R.id.txtTitleRelated)
        TextView txtTitleRelated;

        @BindView(R.id.txtTanggalRelated)
        TextView txtTanggal;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
