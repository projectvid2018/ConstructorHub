package hub.constructor.constructorhub.chat_activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hub.constructor.constructorhub.Adapter.MessageAdapter;
import hub.constructor.constructorhub.Class.Chat;
import hub.constructor.constructorhub.Class.User;
import hub.constructor.constructorhub.R;

import static hub.constructor.constructorhub.product.buyer.ProductDescriptionActivity.SELLER_PRODUCT_HEADING;
import static hub.constructor.constructorhub.product.buyer.ProductDescriptionActivity.SELLER_PRODUCT_URL;
import static hub.constructor.constructorhub.product.buyer.ProductDescriptionActivity.SELLER_UID;

public class ChatActivity extends AppCompatActivity {

    private TextView textViewSellerName;
    private CircleImageView imageViewSeller;
    private Intent intent;
    private DatabaseReference mRef;
    private ImageButton sendMessageBtn;
    private EditText textMessage;
    private List<Chat> mChat;
    private RecyclerView mRecyclerView;
    private MessageAdapter messageAdapter;
    private String current_user_id;
    private String sellerUid;
    private FirebaseUser current_user;
    private Toolbar toolbarChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textViewSellerName = findViewById(R.id.chatSellerNameId);
        imageViewSeller = findViewById(R.id.chat_product_imageId);
        sendMessageBtn = findViewById(R.id.sendMessageBtnId);
        textMessage = findViewById(R.id.textMessageId);
        toolbarChat = findViewById(R.id.chatToolbarId);
        setSupportActionBar(toolbarChat);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mRecyclerView = findViewById(R.id.recyclerViewMessageBodyId);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        // Product Description Activity
        intent = getIntent();
        sellerUid = intent.getStringExtra(SELLER_UID);
        String sellerProductUrl = intent.getStringExtra(SELLER_PRODUCT_URL);
        String sellerProductHeading = intent.getStringExtra(SELLER_PRODUCT_HEADING);

        current_user = FirebaseAuth.getInstance().getCurrentUser();
        current_user_id = current_user.getUid();


        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMessage.getText().toString().trim();
                if (!message.isEmpty()){
                    sendMessage(current_user.getUid(),sellerUid,message);
                }
                else{
                    Toast.makeText(getApplicationContext()
                            ,"You can't send empty message",Toast.LENGTH_SHORT).show();
                }
                textMessage.setText("");

            }
        });

        mRef = FirebaseDatabase.getInstance().getReference("Registered Users").child(sellerUid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textViewSellerName.setText(user.getUserName());

                readMessage(current_user.getUid(),sellerUid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        dbRef.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance()
                .getReference("Chat List")
                .child(current_user.getUid())
                .child(sellerUid);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(sellerUid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference chatListRef = FirebaseDatabase.getInstance()
                .getReference();
    }

    private void readMessage(final String current_user_id, final String sellerUid){

        mChat = new ArrayList<>();
        DatabaseReference sRef;
        sRef = FirebaseDatabase.getInstance().getReference("Chats");
        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if ( chat.getReceiver().equals(current_user.getUid()) && chat.getSender().equals(sellerUid)
                            || chat.getReceiver().equals(sellerUid) && chat.getSender().equals(current_user_id)){
                        mChat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(ChatActivity.this,mChat);
                    mRecyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
