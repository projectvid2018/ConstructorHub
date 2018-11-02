package hub.constructor.constructorhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hub.constructor.constructorhub.product.byer.ChatActivity;

import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_COMPANY_ADDRESS;
import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_COMPANY_NAME;
import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_DESCRIPTION;
import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_HEADING;
import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_PRICE;
import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_SERVICE;
import static hub.constructor.constructorhub.product.byer.ProductsActivity.EXTRA_URL;

public class ProductDescriptionActivity extends AppCompatActivity {

    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        chatButton = findViewById(R.id.gotoChatId);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String heading = intent.getStringExtra(EXTRA_HEADING);
        String companyName = intent.getStringExtra(EXTRA_COMPANY_NAME);
        String address = intent.getStringExtra(EXTRA_COMPANY_ADDRESS);
        String service = intent.getStringExtra(EXTRA_SERVICE);
        String price = intent.getStringExtra(EXTRA_PRICE);
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
        textViewPrice.setText("Price : " + price+ "TK");
        textViewDescription.setText(description);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatButton.setEnabled(false);
                Intent intent1 = new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(intent1);
            }
        });
    }
}
