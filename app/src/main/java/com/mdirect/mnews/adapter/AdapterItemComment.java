package com.mdirect.mnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdirect.mnews.R;
import com.mdirect.mnews.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.Author;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment.Comment;

/**
 * Created by Beni on 18/04/2018.
 */

public class AdapterItemComment extends RecyclerView.Adapter<AdapterItemComment.Holder> {
    private Context context;
    private List<Comment> listComment;
    private ClickListener clickListener;

    public AdapterItemComment(Context context, List<Comment> listComment, ClickListener clickListener) {
        this.context = context;
        this.listComment = listComment;
        this.clickListener = clickListener;
    }

    @Override
    public AdapterItemComment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder viewHolder = new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_list_comments_item_comments, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemComment.Holder holder, int position) {
        final Comment comment = listComment.get(position);
        Author author = comment.getAuthor();
        holder.txtCommentNama.setText(author.getName());
        holder.txtCommentJam.setText(comment.getCreatedAt());
        holder.txtCommentComment.setText(comment.getContent());
        holder.imgMenu.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("MENU");
                menu.add(0, v.getId(), comment.getId(), "HAPUS");
                menu.add(0, v.getId(), comment.getId(), "LAPORKAN");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCommentNama)
        TextView txtCommentNama;
        @BindView(R.id.txtCommentJam)
        TextView txtCommentJam;
        @BindView(R.id.txtCommentComment)
        TextView txtCommentComment;
        @BindView(R.id.imgMenu)
        ImageView imgMenu;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void updateData(Comment newData){
        listComment.add(newData);
        notifyDataSetChanged();
    }
}
