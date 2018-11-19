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

import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.nav.activity.SwitchActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private CardView cardView;
    private ProgressBar progressBar;


    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        cardView = findViewById(R.id.cardViewIdLogIn);
        editTextEmail = findViewById(R.id.logInEmailId);
        editTextPassword = findViewById(R.id.logInPasswordId);
        progressBar = findViewById(R.id.logInProgressbarId);

        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void LoginActivity(View view) {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("please insert email");
            editTextEmail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("please Insert valid email");
            editTextEmail.requestFocus();
            return;
        }


        //password
        else if(password.isEmpty()){
            editTextPassword.setError("please insert password");
            editTextPassword.requestFocus();
            return;
        }

        else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this,SwitchActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                finish();

                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Please enter correct password or email.",Toast.LENGTH_LONG).show();

                            }
                        }
                    });


        }

    }

    public void SignUp(View view) {
        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(intent);
    }


    public void forgotpass(View view) {
        Intent intent = new Intent(getApplicationContext(),ForgotpassActivity.class);
        startActivity(intent);
    }
}
