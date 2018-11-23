package hub.constructor.constructorhub.product.buyer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hub.constructor.constructorhub.chat_activity.ChatListActivity;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.nav.activity.FeedbackActivity;
import hub.constructor.constructorhub.nav.activity.NotificationsActivity;
import hub.constructor.constructorhub.nav.activity.SettingsActivity;
import hub.constructor.constructorhub.nav.activity.SwitchActivity;
import hub.constructor.constructorhub.start.activity.LoginActivity;

public class BuyerModeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){

            sendToStart();

        }
    }
    public void sendToStart(){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

                Intent intent = new Intent(BuyerModeActivity.this,SwitchActivity.class);
                startActivity(intent);

        } else if (id == R.id.nav_notifications) {
            Intent intent = new Intent(BuyerModeActivity.this,NotificationsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(BuyerModeActivity.this,SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            FirebaseAuth.getInstance().signOut();

            AlertDialog.Builder builder=new AlertDialog.Builder(BuyerModeActivity.this); //-------Main is name of the activity
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    sendToStart();
                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert=builder.create();
            alert.show();


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_web) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://www.constructorhub.com"));  //---------------------------------website name
            startActivity(intent);

        }
        else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(BuyerModeActivity.this,FeedbackActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    CardView products, watch, transaction, orders, chat;

    public void products(View view) {
        Intent intent = new Intent(BuyerModeActivity.this,ProductsActivity.class);
        startActivity(intent);
    }

    public void watch(View view) {
        Intent intent = new Intent(BuyerModeActivity.this,WishActivity.class);
        startActivity(intent);
    }

    public void transaction(View view) {
        Intent intent = new Intent(BuyerModeActivity.this,TransactionsActivity.class);
        startActivity(intent);
    }

    public void orders(View view) {
        Intent intent = new Intent(BuyerModeActivity.this,OrdersActivity.class);
        startActivity(intent);
    }

    public void chat(View view) {
        Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
        startActivity(intent);
    }



}
