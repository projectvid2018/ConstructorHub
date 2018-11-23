package hub.constructor.constructorhub.product.buyer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.Class.Upload;
import hub.constructor.constructorhub.Adapter.PostProductAdapter;

public class WishActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostProductAdapter mProductAdapter;
    private List<Upload> mUploads;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        recyclerView = findViewById(R.id.wishListRecyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String user_uid = current_user.getUid();

        mRef = FirebaseDatabase.getInstance().getReference("My WishList/"+user_uid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mProductAdapter = new PostProductAdapter(WishActivity.this,mUploads);
                recyclerView.setAdapter(mProductAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
