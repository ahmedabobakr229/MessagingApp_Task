package com.example.documents.messagingapp_task;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity implements UserClick {

    TextInputLayout textInputLayout ;
    ImageView send ;
    private RecyclerView mUsersList;
    private UserAdapter userAdapter ;
    private EditText sendText ;
    LinearLayout linearLayout ;
    //Tag
    private String TAG = "hhhhhhhhhh";

    //Firebase
    private DatabaseReference msgDatabase;
    private FirebaseUser mCurrentUser , userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        send = (ImageView)findViewById(R.id.send_btn);
        sendText = (EditText)findViewById(R.id.msgSendText);
        mUsersList = (RecyclerView)findViewById(R.id.recycler);
        linearLayout = (LinearLayout)findViewById(R.id.linear);

        RecyclerView.LayoutManager recyce = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        mUsersList.setLayoutManager(recyce);
//        mUsersList.setAdapter(userAdapter);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = FirebaseAuth.getInstance().getCurrentUser();


        Toast.makeText(this, userId.getUid(), Toast.LENGTH_SHORT).show();
        fetchFeeds();

        String current_uid = mCurrentUser.getUid();
        msgDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   fetchFeeds();

                String Msgs = sendText.getText().toString();

                Map map = new HashMap();
                final String key = msgDatabase.push().getKey();
                Map map1 = new HashMap();
                map1.put(key, map);

                Map mParent = new HashMap();
                msgDatabase.push().setValue(mParent);

                msgDatabase.child("Msg").child("msg" + key).setValue(Msgs)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(ChattingActivity.this, "Message sent", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                sendText.setText("");
            }
        });
    }

    public void fetchFeeds() {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("Users").child(userId.getUid()).child("Msg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                      ArrayList<MsgsClass> feeds = new ArrayList<>();
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                        Log.e(TAG , "dataaaaaa" + noteDataSnapshot.getValue());

                            MsgsClass feed = new MsgsClass( noteDataSnapshot.getValue().toString());
                            feeds.add(feed);

                    }
                    userAdapter = new UserAdapter(ChattingActivity.this);
                    userAdapter.setUsersData(feeds, ChattingActivity.this);
                    mUsersList.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    @Override
    public void asd(MsgsClass msgsClass) {

    }
}
