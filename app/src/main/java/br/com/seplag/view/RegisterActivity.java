package br.com.seplag.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.ArrayHelper;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.OfficeHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.UserModel;

public class RegisterActivity extends AppCompatActivity {
    private Spinner neighborhoods;
    private Spinner region;
    private EditText userName;
    private EditText userNumber;
    private EditText userAge;
    private EditText userStreet;
    private EditText userInvite;
    private Button bt_register;
    private ArrayHelper arrayHelper = new ArrayHelper();
    private AuthCallback authCallback;
    private UserModel user;
    private String UserName;
    private String UserNumber;
    private String UserStreet;
    private String UserNeighborhood;
    private String invite;
    private String str_region;
    private String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Cadastro");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        neighborhoods = (Spinner) findViewById(R.id.neighborhood);
        region = (Spinner) findViewById(R.id.region);
        userName = (EditText) findViewById(R.id.edt_name);
        userNumber = (EditText) findViewById(R.id.edt_phoneNumber);
        userStreet = (EditText) findViewById(R.id.edt_street);
        userInvite = (EditText) findViewById(R.id.edt_invite);
        bt_register = (Button) findViewById(R.id.bt_finalregister);
        userAge = (EditText) findViewById(R.id.edt_age);


        final SpinnerAdapter adapterNeighborhoods = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterRegion = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        adapterRegion.addAll(arrayHelper.getArrayRegions());
        adapterRegion.add("Selecione uma Região*");
        region.setAdapter(adapterRegion);
        region.setSelection(adapterRegion.getCount());

        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                str_region = region.getItemAtPosition(position).toString();
                if(position == 0){
                    //ZONA URBANA
                    adapterNeighborhoods.clear();
                    adapterNeighborhoods.addAll(arrayHelper.getNeighborhoodsUrbanArea());
                    adapterNeighborhoods.add("Selecione um Bairro*");
                    neighborhoods.setAdapter(adapterNeighborhoods);
                    neighborhoods.setSelection(adapterNeighborhoods.getCount());
                    neighborhoods.setVisibility(View.VISIBLE);
                }
                if(position == 1){
                    //PRIMEIRO DISTRITO
                    adapterNeighborhoods.clear();
                    adapterNeighborhoods.addAll(arrayHelper.getDistrictOne());
                    adapterNeighborhoods.add("Selecione um Bairro*");
                    neighborhoods.setAdapter(adapterNeighborhoods);
                    neighborhoods.setSelection(adapterNeighborhoods.getCount());
                    neighborhoods.setVisibility(View.VISIBLE);
                }
                if(position == 2){
                    //SEGUNDO DISTRITO
                    adapterNeighborhoods.clear();
                    adapterNeighborhoods.addAll(arrayHelper.getDistrictTwo());
                    adapterNeighborhoods.add("Selecione um Bairro*");
                    neighborhoods.setAdapter(adapterNeighborhoods);
                    neighborhoods.setSelection(adapterNeighborhoods.getCount());
                    neighborhoods.setVisibility(View.VISIBLE);
                }
                if(position == 3){
                    //TERCEIRO DISTRITO
                    adapterNeighborhoods.clear();
                    adapterNeighborhoods.addAll(arrayHelper.getDistrictThree());
                    adapterNeighborhoods.add("Selecione um Bairro*");
                    neighborhoods.setAdapter(adapterNeighborhoods);
                    neighborhoods.setSelection(adapterNeighborhoods.getCount());
                    neighborhoods.setVisibility(View.VISIBLE);
                }
                if(position == 4){
                    //QUARTO DISTRITO
                    adapterNeighborhoods.clear();
                    adapterNeighborhoods.addAll(arrayHelper.getDistrictFour());
                    adapterNeighborhoods.add("Selecione um Bairro*");
                    neighborhoods.setAdapter(adapterNeighborhoods);
                    neighborhoods.setSelection(adapterNeighborhoods.getCount());
                    neighborhoods.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        neighborhoods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserNeighborhood = neighborhoods.getItemAtPosition(position).toString();
                if(str_region.equals("Zona Urbana")){
                    if(UserNeighborhood.equals("SÃO FRANCISCO") || UserNeighborhood.equals("MONTE DO BOM JESUS")||
                            UserNeighborhood.equals("DIVINÓPOLIS") || UserNeighborhood.equals("CENTRO")){
                        Toast.makeText(RegisterActivity.this, "TGS CENTRO", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserController controller = new UserController();
                UserName = userName.getText().toString();
                UserNumber = userNumber.getText().toString();
                UserStreet = userStreet.getText().toString();
                invite = userInvite.getText().toString();
                age = userAge.getText().toString();
                user = new UserModel();

                InternetHelper internet = new InternetHelper();
                boolean b = internet.TestConnection(RegisterActivity.this);
                if(b){
                    if(!str_region.equals("Selecione uma Região*")){
                        if(!UserNeighborhood.equals("Selecione um Bairro*")){
                            if(UserName.isEmpty()) {
                                Toast.makeText(RegisterActivity.this, "Por favor, insira o seu nome...", Toast.LENGTH_SHORT).show();
                            }else if(UserNumber.length() != 11){
                                Toast.makeText(RegisterActivity.this, "Preencha corretamente o seu número de celular...", Toast.LENGTH_SHORT).show();
                            }else if(age.isEmpty()){
                                Toast.makeText(RegisterActivity.this, "Por favor, insira a sua idade...", Toast.LENGTH_SHORT).show();
                            }else{
                                user.setUser_name(UserName);
                                user.setUser_phone(UserNumber);
                                user.setUser_neighborhood(UserNeighborhood);
                                user.setUser_age(age);
                                user.setUser_office(new OfficeHelper().getOffice_1());
                                if(!UserStreet.isEmpty()){
                                    user.setUser_street(UserStreet);
                                }else{
                                    user.setUser_street("");
                                }
                                if(!invite.isEmpty()){
                                    user.setUser_invite(invite);
                                    controller.VerifyNumber(RegisterActivity.this, invite, new UserController.VolleyCallbackVerifyNumber() {
                                        @Override
                                        public void onResult(boolean result) {
                                            if(result){
                                                Toast.makeText(RegisterActivity.this, "Número de indicação não existe...", Toast.LENGTH_SHORT).show();
                                            }else{
                                                controller.VerifyNumber(RegisterActivity.this, UserNumber, new UserController.VolleyCallbackVerifyNumber() {
                                                    @Override
                                                    public void onResult(boolean result) {
                                                        if(result){
                                                            AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                                                                    .withAuthCallBack(authCallback)
                                                                    .withPhoneNumber("+55" + UserNumber);

                                                            Digits.authenticate(authConfigBuilder.build());
                                                        }else{
                                                            Toast.makeText(RegisterActivity.this, "Ops, seu número já está cadastrado!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void error(String error) {

                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void error(String error) {
                                        }
                                    });
                                }else {
                                    user.setUser_invite("");
                                }
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "Selecione um bairro...", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Selecione uma região...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    CreateDialog(RegisterActivity.this, "Sem conexão com internet, por favor conecte-se...");
                }

            }
        });

        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if (!phoneNumber.equals(null)) {
                    user.setUser_phone(phoneNumber);

                    final UserController controller = new UserController();

                    controller.CreateUser(RegisterActivity.this, user, new UserController.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if(result != null) {
                                UserSessionHelper user_session = new UserSessionHelper(RegisterActivity.this);
                                user_session.createUserLoginSession(user.getUser_name(), user.getUser_phone(),
                                        user.getUser_neighborhood(), Integer.toString(100), Integer.parseInt(result),
                                        user.getUser_office());
                                user_session.UpdateRegisterUser("null", "null", "null", "null");

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

    public void CreateDialog(Context mContext, String text){
        final AlertDialog alertConnection;

        AlertDialog.Builder builderConnection = new AlertDialog.Builder(mContext);
        builderConnection.setTitle(getResources().getString(R.string.app_name));
        builderConnection.setMessage(text);
        builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertConnection = builderConnection.create();
        alertConnection.show();
    }

}
