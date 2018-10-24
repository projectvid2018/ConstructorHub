package hub.constructor.constructorhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
    }

    LinearLayout buyer,seller;

    public void seller(View view) {
        Intent intent = new Intent(SwitchActivity.this,Main2Activity.class);
        startActivity(intent);
    }

    public void buyer(View view) {
        Intent intent = new Intent(SwitchActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
