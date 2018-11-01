package hub.constructor.constructorhub.start.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import hub.constructor.constructorhub.R;

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
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

}
