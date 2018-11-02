package hub.constructor.constructorhub.seller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.Upload;

public class PostedProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostProductAdapter mProductAdapter;
    private List<Upload> mUploads;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_products);

        recyclerView = findViewById(R.id.postProductRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mRef = FirebaseDatabase.getInstance().getReference("uploads");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mProductAdapter = new PostProductAdapter(PostedProductsActivity.this,mUploads);
                recyclerView.setAdapter(mProductAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}
