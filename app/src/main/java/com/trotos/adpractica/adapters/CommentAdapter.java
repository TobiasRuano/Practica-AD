package com.trotos.adpractica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.adpractica.R;
import com.trotos.adpractica.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<Comment> comments;
    Context context;

    public CommentAdapter(Context cont, List<Comment> com) {
        this.context = cont;
        this.comments = com;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.bindData(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentName, commentBody, commentEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentName = itemView.findViewById(R.id.commentNameText);
            commentBody = itemView.findViewById(R.id.commentBodyText);
            commentEmail = itemView.findViewById(R.id.commentMailText);
        }

        public void bindData(Comment item) {
            commentName.setText(item.getName());
            commentBody.setText(item.getBody());
            commentEmail.setText(item.getEmail());
        }
    }
}
