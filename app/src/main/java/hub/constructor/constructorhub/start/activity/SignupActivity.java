package hub.constructor.constructorhub.start.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hub.constructor.constructorhub.People;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.nav.activity.SwitchActivity;


public class SignupActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;

    private static final String TAG= "SignupActivity";

    private EditText editTextAddress;
    private EditText editTextUsername;
    private EditText editTextPhoneNo;
    private EditText editTextEmail;
    private EditText editTextPassword;
    CardView cardView;

    private ProgressBar progressBar;

    private  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseReference = FirebaseDatabase.getInstance().getReference("people");

        editTextAddress=findViewById(R.id.AddressId);
        editTextUsername=findViewById(R.id.userNameId);
        editTextPhoneNo = findViewById(R.id.phoneNoId);
        editTextEmail = findViewById(R.id.emailId);
        editTextPassword = findViewById(R.id.passwordId);
        cardView = findViewById(R.id.SignupId);

        mAuth = FirebaseAuth.getInstance();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

    }



    public void addMember(){
        final String address = editTextAddress.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String phoneNo = editTextPhoneNo.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Address
        if(address.isEmpty()){
            editTextUsername.setError("Insert address");
            editTextUsername.requestFocus();
            return;
        }

        //UserName
        else if(username.isEmpty()){
            editTextUsername.setError("Insert name");
            editTextUsername.requestFocus();
            return;
        }

        //Phone Number
        else if(phoneNo.isEmpty()){
            editTextPhoneNo.setError("Insert phone number");
            editTextPhoneNo.requestFocus();
            return;
        }
        else if(phoneNo.length()<11  ){
            editTextPhoneNo.setError("Insert valid phone number");
            editTextPhoneNo.requestFocus();
            return;
        }

        //Email
        else if(email.isEmpty()){
            editTextEmail.setError("Insert email");
            editTextEmail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Insert valid email");
            editTextEmail.requestFocus();
            return;
        }

        //password
        else if(password.isEmpty()){
            editTextPassword.setError("Insert password");
            editTextPassword.requestFocus();
            return;
        }
        else if(password.length()<6){
            editTextPassword.setError("minimum length is 6");
            editTextPassword.requestFocus();
            return;
        }


        else{



//            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   // progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"User Registration Successful",Toast.LENGTH_LONG).show();

                        String id = databaseReference.push().getKey();

                        People people = new People(id,address,username,phoneNo,email,password);
                        databaseReference.child(id).setValue(people);
                        Intent intent = new Intent(getApplicationContext(),SwitchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }else {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }



}
