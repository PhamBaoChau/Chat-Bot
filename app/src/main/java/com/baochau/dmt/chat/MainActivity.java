package com.baochau.dmt.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.baochau.dmt.chat.Model.ItemChat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ID_ACCOUNT = "idAccount";
    public static final String ID_RECEIVER = "idReceiver";
    EditText edtChat;
    ImageButton btnSend;
    ItemChat itemChat;
    TextView acc1, acc2;
    int idItemChat, idAccount = 1, idReceiver = 2;
    FragmentChat fmChat;

    void init() {
        edtChat = findViewById(R.id.edtText);
        btnSend = findViewById(R.id.btnSend);
        acc1 = findViewById(R.id.account1);
        acc2 = findViewById(R.id.account2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        displayListChat();
        acc1.setOnClickListener(this);
        acc2.setOnClickListener(this);
        sendMessage();
    }

    void displayListChat() {
        fmChat = new FragmentChat();
        Bundle bundle = new Bundle();
        bundle.putInt(ID_ACCOUNT, idAccount);
        bundle.putInt(ID_RECEIVER, idReceiver);
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        fmChat.setArguments(bundle);
        manager.replace(R.id.layout_chat_content, fmChat).commit();
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

        itemChat = new ItemChat(idItemChat, idAccount, idReceiver, time, sContent);
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

    @Override
    public void onClick(View view) {
        view.setVisibility(View.INVISIBLE);
        if (view == acc1) {
            acc2.setVisibility(View.VISIBLE);
            idAccount = 2;
            idReceiver = 1;
        } else {
            acc1.setVisibility(View.VISIBLE);
            idAccount = 1;
            idReceiver = 2;
        }
        displayListChat();
    }
}