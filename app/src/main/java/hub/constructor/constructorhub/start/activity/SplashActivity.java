package hub.constructor.constructorhub.start.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.nav.activity.SwitchActivity;

public class SplashActivity extends Activity implements Runnable
{
    Thread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mThread = new Thread(this);
        mThread.start();
    }
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(1500);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null){
                startActivity(new Intent(SplashActivity.this, SwitchActivity.class));
                finish();
            }else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }

        }
    }



}
