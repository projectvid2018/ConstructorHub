package hub.constructor.constructorhub.chat_activity;

import android.content.Intent;
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
import hub.constructor.constructorhub.Class.User;
import hub.constructor.constructorhub.R;

import static hub.constructor.constructorhub.product.buyer.ProductDescriptionActivity.SELLER_UID;

public class ChatListActivity extends AppCompatActivity implements ChatListAdapter.OnItemClickListener{

    public static final String EXTRA_RECEIVER_NAME = "receiverName";
    //public static final String EXTRA_SELLER_UID = "seller_uid";
    private Toolbar toolbarChatList;
    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private List<User> mUser;
    private FirebaseUser firebaseUser;
    private DatabaseReference dRef;

    private List<ChatList> chatUserList;


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

        chatUserList = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

       dRef = FirebaseDatabase.getInstance().getReference("MyChat/Chat List/")
               .child(firebaseUser.getUid());

       dRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               chatUserList.clear();
               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   ChatList chat_list = snapshot.getValue(ChatList.class);
                   chatUserList.add(chat_list);
               }
               myChatList();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }
    private void myChatList(){

        mUser = new ArrayList<>();
        dRef = FirebaseDatabase.getInstance().getReference("Registered Users");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (ChatList chatList: chatUserList){
                        if (user.getUserUid().equals(chatList.id)){
                            mUser.add(user);
                        }
                    }
                    chatListAdapter = new ChatListAdapter(getApplicationContext(),mUser);
                    recyclerView.setAdapter(chatListAdapter);
                    chatListAdapter.setOnItemClickListener(ChatListActivity.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onItemClick(int position) {
        Intent chatIntent = new Intent(this,ChatActivity.class);
        User clickedItem = mUser.get(position);
        chatIntent.putExtra(EXTRA_RECEIVER_NAME,clickedItem.getUserName());
        chatIntent.putExtra(SELLER_UID,clickedItem.getUserUid());

        startActivity(chatIntent);
    }
}
