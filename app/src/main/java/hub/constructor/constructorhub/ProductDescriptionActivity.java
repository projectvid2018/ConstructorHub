package hub.constructor.constructorhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import hub.constructor.constructorhub.product.buyer.ChatActivity;

import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_COMPANY_ADDRESS;
import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_COMPANY_NAME;
import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_DESCRIPTION;
import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_HEADING;
import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_PRICE;
import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_SERVICE;
import static hub.constructor.constructorhub.product.buyer.ProductsActivity.EXTRA_URL;

public class ProductDescriptionActivity extends AppCompatActivity {

    private Button chatButton;
    private Button wishListButton;
    private Button orderButton;

    private StorageReference storageReference;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        chatButton = findViewById(R.id.gotoChatId);
        wishListButton = findViewById(R.id.wishListId);
        orderButton = findViewById(R.id.setOrderId);

        mRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        final String imageUrl = intent.getStringExtra(EXTRA_URL);
        final String heading = intent.getStringExtra(EXTRA_HEADING);
        String companyName = intent.getStringExtra(EXTRA_COMPANY_NAME);
        String address = intent.getStringExtra(EXTRA_COMPANY_ADDRESS);
        final String service = intent.getStringExtra(EXTRA_SERVICE);
        final String price = intent.getStringExtra(EXTRA_PRICE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);


        ImageView imageView = findViewById(R.id.productDetailsImageId);
        TextView textViewHeading = findViewById(R.id.productDetailsHeadingId);
        TextView textViewCompanyName = findViewById(R.id.productDetailsCompanyNameId);
        TextView textViewAddress = findViewById(R.id.productDetailsAddressId);
        TextView textViewService = findViewById(R.id.productDetailsServiceId);
        TextView textViewPrice = findViewById(R.id.productDetailsPriceId);
        TextView textViewDescription = findViewById(R.id.productDetailsDescriptionId);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewHeading.setText(heading);
        textViewCompanyName.setText("Company: " + companyName);
        textViewAddress.setText("Address: " + address);
        textViewService.setText(service);
        textViewPrice.setText("Price : " + price+ " TK");
        textViewDescription.setText(description);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatButton.setEnabled(false);
                Intent intent1 = new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(intent1);
            }
        });

        wishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Upload order = new Upload(heading,service,price,imageUrl);
                mRef.child("WishList").push().setValue(order);
                Toast.makeText(getApplicationContext(),"Add into wish list",Toast.LENGTH_LONG).show();

            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload order = new Upload(heading,service,price,imageUrl);
                mRef.child("Order").push().setValue(order);
                Toast.makeText(getApplicationContext(),"Order Successful",Toast.LENGTH_LONG).show();
            }
        });
    }
}
