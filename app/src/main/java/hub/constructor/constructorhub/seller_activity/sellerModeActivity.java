package hub.constructor.constructorhub.seller_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hub.constructor.constructorhub.chat_activity.ChatActivity;
import hub.constructor.constructorhub.chat_activity.ChatListActivity;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.nav.activity.FeedbackActivity;
import hub.constructor.constructorhub.nav.activity.SettingsActivity;
import hub.constructor.constructorhub.nav.activity.SwitchActivity;
import hub.constructor.constructorhub.start.activity.LoginActivity;

public class sellerModeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent intent = new Intent(sellerModeActivity.this,SwitchActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_notifications) {
            Intent intent = new Intent(sellerModeActivity.this,SupNotificationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(sellerModeActivity.this,SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            FirebaseAuth.getInstance().signOut();

            AlertDialog.Builder builder=new AlertDialog.Builder(sellerModeActivity.this); //-------Main is name of the activity
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent=new Intent(sellerModeActivity.this,LoginActivity.class);
                    intent.putExtra("finish", true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //----------------------------------- To clean up all activities
                    startActivity(intent);
                    finish();

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
            Intent intent = new Intent(sellerModeActivity.this,FeedbackActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView textView1 = headerView.findViewById(R.id.navSellerUsernameId);
        final TextView textView2 = headerView.findViewById(R.id.navSellerEmailID);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = currentUser.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Registered Users/" + user_id);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("userName").getValue().toString();
                String email = dataSnapshot.child("userEmail").getValue().toString();

                textView1.setText(name);
                textView2.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void add_new_product(View view) {
        Intent intent = new Intent(sellerModeActivity.this,AddNewProductActivity.class);
        startActivity(intent);
    }

    public void posted_prducts(View view) {
        Intent intent = new Intent(sellerModeActivity.this,PostedProductsActivity.class);
        startActivity(intent);
    }

    public void sup_transaction(View view) {
        Intent intent = new Intent(sellerModeActivity.this,SupTransactionActivity.class);
        startActivity(intent);
    }

    public void sup_orders(View view) {
        Intent intent = new Intent(sellerModeActivity.this,SupOrdersActivity.class);
        startActivity(intent);
    }


    public void goChatList(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
        startActivity(intent);
    }
}