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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hub.constructor.constructorhub.Class.User;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.nav.activity.SwitchActivity;


public class SignupActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;

    private static final String TAG= "SignupActivity";

    private EditText editTextUsername;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextPhoneNo;
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

        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        editTextUsername=findViewById(R.id.userNameId);
        editTextAddress=findViewById(R.id.AddressId);
        editTextEmail = findViewById(R.id.emailId);
        editTextPhoneNo = findViewById(R.id.phoneNoId);
        editTextPassword = findViewById(R.id.passwordId);
        cardView = findViewById(R.id.SignupId);

        progressBar = findViewById(R.id.signUpProgressbarId);

        mAuth = FirebaseAuth.getInstance();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

    }



    public void addMember(){
        final String username = editTextUsername.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String phoneNo = editTextPhoneNo.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        //UserName
        if(username.isEmpty()){
            editTextUsername.setError("Insert name");
            editTextUsername.requestFocus();
            return;
        }
        //Address
        if(address.isEmpty()){
            editTextAddress.setError("Insert address");
            editTextAddress.requestFocus();
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
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        String user_uid = current_user.getUid();

                        User newUser = new User(user_uid,username,address,phoneNo,email,password);
                        databaseReference.child(user_uid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(getApplicationContext(),SwitchActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"User Registration Successful",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });


                    }else {
                        progressBar.setVisibility(View.GONE);
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
