package com.baochau.dmt.chat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baochau.dmt.chat.Model.ItemChat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.DateViewHolder> {
    List<ItemChat> chats;
    int idAccount;

    public ChatAdapter(List<ItemChat> chats, int idAccount) {
        this.chats = chats;
        this.idAccount = idAccount;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        ItemChat item = chats.get(position);
        holder.tvLeft.setVisibility(View.GONE);
        holder.tvRight.setVisibility(View.GONE);

        if (item.idSender == idAccount) {
            holder.tvRight.setText(item.content);
            holder.tvRight.setVisibility(View.VISIBLE);
        } else {
            holder.tvLeft.setText(item.content);
            holder.tvLeft.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeft, tvRight;

        public DateViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tvLeft);
            tvRight = itemView.findViewById(R.id.tvRight);
        }
    }
}
