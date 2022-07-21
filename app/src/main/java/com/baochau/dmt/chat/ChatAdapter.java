package com.baochau.dmt.chat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baochau.dmt.chat.Model.ItemChat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.DateViewHolder> {
    List<ItemChat> chats;
    int idAccount;
    int idReceiver;
    DatabaseReference mRef;

    public ChatAdapter(List<ItemChat> chats, int idAccount, int idReceiver, DatabaseReference mRef) {
        this.chats = chats;
        this.idAccount = idAccount;
        this.idReceiver = idReceiver;
        this.mRef = mRef;
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
        holder.statusLeft.setVisibility(View.GONE);
        holder.statusRight.setVisibility(View.GONE);

        if (item.idSender == idAccount) {
            holder.tvRight.setText(item.content);
            holder.statusRight.setText(item.status);
            holder.tvRight.setVisibility(View.VISIBLE);
            holder.statusRight.setVisibility(View.VISIBLE);
        } else {
            holder.tvLeft.setText(item.content);
            holder.statusLeft.setText(item.status);
            holder.tvLeft.setVisibility(View.VISIBLE);
            holder.statusLeft.setVisibility(View.VISIBLE);
        }
        updateStatus(item,position,idReceiver);
    }
    void updateStatus(ItemChat itemChat, int pos, int idReceiver) {
        String seen = "Đã xem";
        if (itemChat.idSender == idReceiver &&
                itemChat.status != seen) {
            if (pos!=chats.size()-1)
                mRef.child(String.valueOf(itemChat.idItemChat)).child("status").setValue(seen);
            else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yy");
                String time = dateFormat.format(Calendar.getInstance().getTime());
                if (itemChat.idSender != idAccount) {
                    mRef.child(String.valueOf(itemChat.idItemChat)).child("status").setValue(seen + " " + time);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeft, tvRight;
        TextView statusLeft, statusRight;

        public DateViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tvLeft);
            tvRight = itemView.findViewById(R.id.tvRight);
            statusLeft = itemView.findViewById(R.id.statusLeft);
            statusRight = itemView.findViewById(R.id.statusRight);
        }
    }
}
