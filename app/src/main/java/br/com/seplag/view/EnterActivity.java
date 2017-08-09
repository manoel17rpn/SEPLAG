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

import br.com.seplag.R;
import br.com.seplag.controller.BaseUrl;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.ShowMessage;
import br.com.seplag.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnterActivity extends AppCompatActivity {
    private EditText edt_number;
    private Button bt_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Entrar");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        edt_number = (EditText) findViewById(R.id.edt_phone_number);
        bt_enter = (Button) findViewById(R.id.bt_enter);

        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_text = edt_number.getText().toString();

                if(phone_text.length() != 11){
                    new ShowMessage().showSnackBar(findViewById(R.id.my_layout), "Número inválido, digite novamente");
                }else{
                    if(new InternetHelper().TestConnection(EnterActivity.this)){
                        //Verificar se número existe
                        Retrofit retrofit = buildRetrofit(BaseUrl.URL);
                        UserController userController = retrofit.create(UserController.class);
                        Call<UserModel> user = userController.getUser(phone_text);
                        user.enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                if(response.isSuccessful()){
                                    UserModel model = response.body();
                                }else{
                                    int code = response.code();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {
                                Log.i("Error", t.getMessage());
                            }
                        });

                        Intent intent = new Intent(EnterActivity.this, VerifyUserNumber.class);
                        Bundle params = new Bundle();
                        params.putString("phone", phone_text);
                        params.putString("activity", "enter");
                        //params.putSerializable("user", user);
                        intent.putExtras(params);
                        startActivity(intent);
                    }else{
                        new ShowMessage().showSnackBar(findViewById(R.id.my_layout), "Sem conexão à internet");
                    }
                }
            }
        });

    }

    private Retrofit buildRetrofit(String baseurl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
