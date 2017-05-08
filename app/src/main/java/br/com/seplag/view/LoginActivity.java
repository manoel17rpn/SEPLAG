package br.com.seplag.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.seplag.R;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.UserModel;

public class LoginActivity extends AppCompatActivity {
    private UserModel user;
    private EditText edtPhone;
    private Button bt_login;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String phone = edtPhone.getText().toString();

                if(phone.length() != 11){
                    Toast.makeText(LoginActivity.this, "Número inválido, verifique se está correto...", Toast.LENGTH_LONG).show();
                }else{
                    if(new InternetHelper().TestConnection(LoginActivity.this)){
                        UserController controller = new UserController();

                        user = controller.LoginUser(LoginActivity.this, phone, new UserController.VolleyCallbackGet() {
                            @Override
                            public void onSucess(boolean result) {
                                if(result){
                                    UserSessionHelper session = new UserSessionHelper(LoginActivity.this);
                                    session.createUserLoginSession(user.getUser_name(), user.getUser_phone(),
                                            user.getUser_neighborhood(), user.getUser_score());
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
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

    }
}
