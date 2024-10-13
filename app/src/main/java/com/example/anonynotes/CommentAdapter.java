package com.example.anonynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<Comment> commentArraylist;
    private Context context;

    public CommentAdapter(ArrayList<Comment> commentArraylist, Context context) {
        this.commentArraylist = commentArraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentArraylist.get(position);
        holder.tvMainUsername.setText(comment.getName());
        holder.tvdateCreated.setText(comment.getDateCreated());
        holder.tvMainNote.setText(comment.getContent());

        // Set up the replies RecyclerView
        holder.repliesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        ReplyAdapter replyAdapter = new ReplyAdapter(comment.getReplies(), context);
        holder.repliesRecyclerView.setAdapter(replyAdapter);
    }


    @Override
    public int getItemCount() {
        return commentArraylist.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainUsername, tvdateCreated, tvMainNote;
        RecyclerView repliesRecyclerView;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMainUsername = itemView.findViewById(R.id.tvMainUsername);
            tvdateCreated= itemView.findViewById(R.id.tvdateCreated);
            tvMainNote = itemView.findViewById(R.id.tvMainNote);
            repliesRecyclerView = itemView.findViewById(R.id.recycler_view_replies);
        }
    }

    public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {
        private List<Comment> replyList;
        private Context context;

        public ReplyAdapter(List<Comment> replyList, Context context) {
            this.replyList = replyList;
            this.context = context;
        }

        @NonNull
        @Override
        public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_reply, parent, false);
            return new ReplyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
            Comment reply = replyList.get(position);
            holder.tvReplyUsername.setText(reply.getName());
            holder.tvReplyContent.setText(reply.getContent());
            holder.replydateCreated.setText(reply.getDateCreated());
        }

        @Override
        public int getItemCount() {
            return replyList.size();
        }

        public class ReplyViewHolder extends RecyclerView.ViewHolder {
            TextView tvReplyUsername, tvReplyContent, replydateCreated;

            public ReplyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvReplyUsername = itemView.findViewById(R.id.tvReplyUsername); // Make sure this ID exists in reply_item.xml
                tvReplyContent = itemView.findViewById(R.id.tvReply); // Make sure this ID exists in reply_item.xml
                replydateCreated = itemView.findViewById(R.id.replydateCreated);
            }
        }
    }
}
