package br.com.seplag.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import br.com.seplag.helper.IntroHelper;
import io.fabric.sdk.android.Fabric;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;

public class SplashActivity extends AppCompatActivity {
    private IntroHelper helper;
    private static int SPLASH_TIME_OUT = 2500;
    UserSessionHelper session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        helper = new IntroHelper(this);

        session = new UserSessionHelper(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                if(session.isUserLoggedIn()){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    if (!helper.isFirstTimeLaunch()) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashActivity.this, IntoScreen.class);
                        startActivity(intent);
                    }
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
