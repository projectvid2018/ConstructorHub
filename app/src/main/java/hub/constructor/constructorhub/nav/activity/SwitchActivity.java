package hub.constructor.constructorhub.nav.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import hub.constructor.constructorhub.seller.sellerModeActivity;
import hub.constructor.constructorhub.product.byer.ByerModeActivity;
import hub.constructor.constructorhub.R;

public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
    }

    LinearLayout buyer,seller;

    public void seller(View view) {
        Intent intent = new Intent(SwitchActivity.this,sellerModeActivity.class);
        startActivity(intent);
    }

    public void buyer(View view) {
        Intent intent = new Intent(SwitchActivity.this,ByerModeActivity.class);
        startActivity(intent);
    }
}
