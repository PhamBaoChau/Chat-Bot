package com.baochau.dmt.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baochau.dmt.chat.Model.ItemChat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText edtChat;
    ImageButton btnSend;
    ItemChat itemChat;
    int idItemChat;

    void init() {
        edtChat = findViewById(R.id.edtText);
        btnSend = findViewById(R.id.btnSend);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        FragmentChat fmChat = new FragmentChat();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.layout_chat_content, fmChat).commit();

        sendMessage();
    }

    void sendMessage() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                edtChat.setText(null);
            }
        });
    }

    void setData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("chats");
        createItemChat(mRef);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yy");
        String time = dateFormat.format(Calendar.getInstance().getTime());
        String sContent = edtChat.getText().toString().trim();
        itemChat = new ItemChat(idItemChat, 1, 2, time, sContent);
        mRef.child(String.valueOf(idItemChat)).setValue(itemChat);
    }

    void createItemChat(DatabaseReference mRef) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idItemChat = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}