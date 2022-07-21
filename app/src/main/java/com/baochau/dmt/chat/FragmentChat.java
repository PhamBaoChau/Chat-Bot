package com.baochau.dmt.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baochau.dmt.chat.Model.ItemChat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentChat extends Fragment {
    RecyclerView recyclerView;
    ChatAdapter adapter;
    List<ItemChat> chatList;
    DatabaseReference mRef;

    public FragmentChat(DatabaseReference mRef) {
        this.mRef = mRef;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvChat);
        chatList = new ArrayList<>();

        Bundle bundle = getArguments();
        int idAccount = bundle.getInt(MainActivity.ID_ACCOUNT);
        int idReceiver = bundle.getInt(MainActivity.ID_RECEIVER);

        LinearLayoutManager lout =new LinearLayoutManager(getContext());
        getData(idAccount, idReceiver,mRef,lout);
        adapter = new ChatAdapter(chatList, idAccount,idReceiver,mRef);
        recyclerView.setLayoutManager(lout);
        recyclerView.setAdapter(adapter);
    }

    void getData(int idAccount, int idReceiver,DatabaseReference mRef,LinearLayoutManager lout) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ItemChat itemChat = itemSnapshot.getValue(ItemChat.class);
                    if ((itemChat.idSender == idAccount && itemChat.idReceiver == idReceiver)
                            || (itemChat.idSender == idReceiver && itemChat.idReceiver == idAccount)) {
                        chatList.add(itemChat);
                    }
                }
                adapter.notifyDataSetChanged();
                lout.setStackFromEnd(true);
                lout.scrollToPosition(adapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Chau: ");
            }
        });
    }
}
