package hub.constructor.constructorhub.product.buyer;

import android.content.Intent;
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
import hub.constructor.constructorhub.Class.Upload;
import hub.constructor.constructorhub.Adapter.PostProductAdapter;

public class ProductsActivity extends AppCompatActivity implements PostProductAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_HEADING = "headingName";
    public static final String EXTRA_COMPANY_NAME = "companyName";
    public static final String EXTRA_COMPANY_ADDRESS = "companyAddress";
    public static final String EXTRA_SERVICE = "service";
    public static final String EXTRA_PRICE = "price";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_SELLER_UID = "seller_uid";
    public static final String EXTRA_BUYER_UID = "buyer_uid";

    private RecyclerView recyclerView;
    private PostProductAdapter mProductAdapter;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.buyerProductRecycleViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Uploaded Products");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mProductAdapter = new PostProductAdapter(ProductsActivity.this,mUploads);
                recyclerView.setAdapter(mProductAdapter);
                mProductAdapter.setOnItemClickListener(ProductsActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onItemClick(int position) {

        Intent detailIntent = new Intent(this, ProductDescriptionActivity.class);
        Upload clickedItem = mUploads.get(position);
        detailIntent.putExtra(EXTRA_URL, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_HEADING, clickedItem.getHeading());
        detailIntent.putExtra(EXTRA_COMPANY_NAME, clickedItem.getCompanyName());
        detailIntent.putExtra(EXTRA_COMPANY_ADDRESS, clickedItem.getCompanyAddress());
        detailIntent.putExtra(EXTRA_SERVICE, clickedItem.getService());
        detailIntent.putExtra(EXTRA_PRICE, clickedItem.getPrice());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescription());
        detailIntent.putExtra(EXTRA_SELLER_UID,clickedItem.getUserUid());

        startActivity(detailIntent);

    }
}
