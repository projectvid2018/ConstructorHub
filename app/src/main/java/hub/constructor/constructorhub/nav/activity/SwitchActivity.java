package hub.constructor.constructorhub.nav.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hub.constructor.constructorhub.seller_activity.sellerModeActivity;
import hub.constructor.constructorhub.product.buyer.BuyerModeActivity;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.start.activity.LoginActivity;

public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
    }

    CardView buyer,seller;

    public void seller(View view) {
        Intent intent = new Intent(SwitchActivity.this,sellerModeActivity.class);
        startActivity(intent);
    }

    public void buyer(View view) {
        Intent intent = new Intent(SwitchActivity.this,BuyerModeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
