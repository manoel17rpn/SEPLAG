package br.com.seplag.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.seplag.R;
import br.com.seplag.helper.UserSessionHelper;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                UserSessionHelper session = new UserSessionHelper(SplashActivity.this);
                if(session.isUserLoggedIn()){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
