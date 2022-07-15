package com.baochau.dmt.chat;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.baochau.dmt.chat.Model.ItemChat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.DateViewHolder> {
    List<ItemChat> chats;

    public ChatAdapter(List<ItemChat> chats) {
        this.chats = chats;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        ItemChat item = chats.get(position);
        if (item.idSender == 1 && item.idReceiver == 2) {
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
