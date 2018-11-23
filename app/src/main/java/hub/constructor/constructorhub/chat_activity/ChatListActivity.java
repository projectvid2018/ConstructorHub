package hub.constructor.constructorhub.chat_activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hub.constructor.constructorhub.Adapter.ChatListAdapter;
import hub.constructor.constructorhub.Class.Chat;
import hub.constructor.constructorhub.Class.Upload;
import hub.constructor.constructorhub.R;

public class ChatListActivity extends AppCompatActivity {

    private Toolbar toolbarChatList;
    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private List<Upload> mUser;
    private FirebaseUser firebaseUser;
    private DatabaseReference dRef;

    private List<ChatList> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);


        toolbarChatList = findViewById(R.id.chatListToolbarId);
        setSupportActionBar(toolbarChatList);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChatList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.chatListRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userList = new ArrayList<>();

       dRef = FirebaseDatabase.getInstance().getReference("Chat List")
               .child(firebaseUser.getUid());

       dRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               userList.clear();
               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   ChatList chatList = snapshot.getValue(ChatList.class);
                   userList.add(chatList);
               }
               chatList();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }
    private void chatList(){

        mUser = new ArrayList<>();
        dRef = FirebaseDatabase.getInstance().getReference("Uploaded Products");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    for (ChatList chatList: userList){
                        if (upload.getUserUid().equals(chatList.id)){
                            mUser.add(upload);
                        }
                    }
                    chatListAdapter = new ChatListAdapter(getApplicationContext(),mUser);
                    recyclerView.setAdapter(chatListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
