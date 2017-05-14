package br.com.seplag.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import br.com.seplag.R;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.UserModel;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {
    private UserModel user;
    private EditText edtPhone;
    private Button bt_login;
    private Button bt_register;
    private static final String TWITTER_KEY = "8BrduvhHOx6nbxK0sPsnIqtiB";
    private static final String TWITTER_SECRET = "w9g0fdSUsDqdsDPIzDlI5B9CefI7pSyx4ps2YJHHIlzafdK3XX";
    private AuthCallback authCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());
        setContentView(R.layout.activity_login);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Login");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        edtPhone = (EditText) findViewById(R.id.edt_phone);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = edtPhone.getText().toString();

                if(phone.length() != 11){
                    Toast.makeText(LoginActivity.this, "Número inválido, verifique se está correto...", Toast.LENGTH_LONG).show();
                }else{
                    if(new InternetHelper().TestConnection(LoginActivity.this)){
                        UserController controller = new UserController();

                        user = controller.LoginUser(LoginActivity.this, phone, new UserController.VolleyCallbackGet() {
                            @Override
                            public void onSucess(boolean result) {
                                if(result){
                                    AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                                            .withAuthCallBack(authCallback)
                                            .withPhoneNumber("+55" + phone);

                                    Digits.authenticate(authConfigBuilder.build());
                                }
                            }

                            @Override
                            public void onFailed(boolean error) {
                                if(error){
                                    Toast.makeText(LoginActivity.this, "Ops, algo saiu errado! Verique sua conexão com a Internet",
                                            Toast.LENGTH_LONG);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this, "Sem conexão a internet, por favor, conecte-se...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if (!phoneNumber.equals(null)) {
                    UserSessionHelper user_session = new UserSessionHelper(LoginActivity.this);
                    user_session.createUserLoginSession(user.getUser_name(), user.getUser_phone(),
                            user.getUser_neighborhood(), Integer.toString(user.getUser_score()), user.getUser_id(),
                            user.getUser_office());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void failure(DigitsException exception) {
                try {

                } catch (Exception e) {
                    //Do noting
                }
            }

        };

    }
}
