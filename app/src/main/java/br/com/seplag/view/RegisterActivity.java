package br.com.seplag.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.ArrayHelper;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.UserModel;

public class RegisterActivity extends AppCompatActivity {
    private Spinner neighborhoods;
    private EditText userName;
    private EditText userNumber;
    private EditText userStreet;
    private Button bt_register;
    private ArrayHelper arrayHelper = new ArrayHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Cadastro");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        neighborhoods = (Spinner) findViewById(R.id.neighborhood);
        userName = (EditText) findViewById(R.id.edt_name);
        userNumber = (EditText) findViewById(R.id.edt_phoneNumber);
        userStreet = (EditText) findViewById(R.id.edt_street);
        bt_register = (Button) findViewById(R.id.bt_finalregister);

        SpinnerAdapter adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        adapter.addAll(arrayHelper.getArrayNeighborhoods());
        adapter.add("Selecione um Bairro*");
        neighborhoods.setAdapter(adapter);
        neighborhoods.setSelection(adapter.getCount());


        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserName = userName.getText().toString();
                final String UserNumber = userNumber.getText().toString();
                String UserStreet = userStreet.getText().toString();
                final String UserNeighborhood = neighborhoods.getSelectedItem().toString();
                UserModel user = new UserModel();

                if(!UserNeighborhood.equals("Selecione um Bairro*")){
                    if(UserName.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Por favor, insira o seu nome...", Toast.LENGTH_SHORT).show();
                    }else if(UserNumber.length() != 11){
                        Toast.makeText(RegisterActivity.this, "Preencha corretamente o seu número de celular...", Toast.LENGTH_SHORT).show();
                    }else{
                        user.setUser_name(UserName);
                        user.setUser_phone(UserNumber);
                        user.setUser_neighborhood(UserNeighborhood);
                        if(!UserStreet.isEmpty()){
                            user.setUser_street(UserStreet);
                        }else{
                            user.setUser_street("");
                        }

                        InternetHelper internet = new InternetHelper();
                        boolean b = internet.TestConnection(RegisterActivity.this);
                        if(b){
                            UserController controller = new UserController();
                            controller.CreateUser(RegisterActivity.this, user, new UserController.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    if(result != null) {
                                        UserSessionHelper session = new UserSessionHelper(RegisterActivity.this);
                                        session.createUserLoginSession(UserName, UserNumber,
                                                UserNeighborhood, 0);
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailed(int result, String menssager) {
                                    if(result == 500){
                                        Toast.makeText(RegisterActivity.this, "Número já Cadastrado!", Toast.LENGTH_SHORT).show();
                                    }else if(result == 0){
                                        Toast.makeText(RegisterActivity.this, "Tempo expirado, tente novamente...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Sem conexão com a internet...", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Selecione um bairro...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
