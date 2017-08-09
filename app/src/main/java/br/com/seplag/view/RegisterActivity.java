package br.com.seplag.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.BaseUrl;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.ArrayHelper;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.OfficeHelper;
import br.com.seplag.helper.ShowMessage;
import br.com.seplag.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Spinner neighborhoods;
    private Spinner region;
    private EditText edt_user_name;
    private EditText edt_user_phone;
    private EditText edt_user_age;
    private EditText edt_user_street;
    private EditText edt_user_code;
    private TextView txt_tgs;
    private TextView txt_tgs1;
    private Button bt_register;
    private ArrayHelper arrayHelper = new ArrayHelper();
    private UserModel user;
    private String UserName;
    private String UserPhone;
    private String UserStreet;
    private String UserNeighborhood;
    private String UserCode;
    private String str_region;
    private String UserAge;

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
        edt_user_name = (EditText) findViewById(R.id.edt_name);
        edt_user_phone = (EditText) findViewById(R.id.edt_phoneNumber);
        edt_user_street = (EditText) findViewById(R.id.edt_street);
        edt_user_code = (EditText) findViewById(R.id.edt_invite);
        edt_user_age = (EditText) findViewById(R.id.edt_age);
        bt_register = (Button) findViewById(R.id.bt_finalregister);
        txt_tgs = (TextView) findViewById(R.id.txt_tgs);
        txt_tgs1 = (TextView) findViewById(R.id.txt_tgs1);

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

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Centro");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(UserNeighborhood.equals("NOVA CARUARU") || UserNeighborhood.equals("SEVERINO AFONSO")||
                            UserNeighborhood.equals("LUIZ GONZAGA") || UserNeighborhood.equals("MAURICIO DE NASSAU") ||
                            UserNeighborhood.equals("FERNANDO LYRA") || UserNeighborhood.equals("UNIVERSITÁRIO")||
                            UserNeighborhood.equals("LAGOA DE ALGODÃO") || UserNeighborhood.equals("PARQUE DA CIDADE")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Norte");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(UserNeighborhood.equals("RENDEIRAS") || UserNeighborhood.equals("ALTO DA BALANÇA")||
                            UserNeighborhood.equals("CEDRO") || UserNeighborhood.equals("CIDADE JARDIM") ||
                            UserNeighborhood.equals("RIACHÃO") || UserNeighborhood.equals("SALGADO")||
                            UserNeighborhood.equals("SÃO JOÃO DA ESCÓCIA") || UserNeighborhood.equals("LOT. SERRANÓPOLIS")||
                            UserNeighborhood.equals("MORADA NOVA") || UserNeighborhood.equals("SERRA DO VALE")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Leste");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(UserNeighborhood.equals("PETRÓPOLIS") || UserNeighborhood.equals("SANTA ROSA")||
                            UserNeighborhood.equals("VASSOURAL") || UserNeighborhood.equals("JARDIM LIBERDADE") ||
                            UserNeighborhood.equals("ALTO DA BANANA") || UserNeighborhood.equals("INDIANÓPOLIS")||
                            UserNeighborhood.equals("INOCOOP") || UserNeighborhood.equals("JOSÉ LIBERATO I E II")||
                            UserNeighborhood.equals("AGAMENON MAGALHÃES") || UserNeighborhood.equals("WILTON LIRA")||
                            UserNeighborhood.equals("PITOMBEIRAS I E II") || UserNeighborhood.equals("ENCANTO DA SERRA")||
                            UserNeighborhood.equals("ADALGISA NUNES I, II E III") || UserNeighborhood.equals("ROSANÓPOLIS")||
                            UserNeighborhood.equals("PINHEIRÓPOLIS")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Sul");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(UserNeighborhood.equals("VILA ANDORINHA") || UserNeighborhood.equals("MARIA AUXILIADORA")||
                            UserNeighborhood.equals("BOA VISTA I E II") || UserNeighborhood.equals("JARDIM PANORAMA I E II") ||
                            UserNeighborhood.equals("JARDIM BOA VISTA") || UserNeighborhood.equals("CAIUCÁ")||
                            UserNeighborhood.equals("JOÃO MOTA") || UserNeighborhood.equals("JOSÉ CARLOS DE OLIVEIRA")||
                            UserNeighborhood.equals("VILA KENNEDY") || UserNeighborhood.equals("SOL POENTE")||
                            UserNeighborhood.equals("VILA DO AEROPORTO") || UserNeighborhood.equals("DEMÓSTENES VERAS")||
                            UserNeighborhood.equals("LOT. MOURA BRASIL") || UserNeighborhood.equals("LOT. JOÃO BARRETO")||
                            UserNeighborhood.equals("LOT. NOVO MUNDO") || UserNeighborhood.equals("VILA PADRE INÁCIO")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Oeste");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }

                }else{
                    txt_tgs.setVisibility(View.GONE);
                    txt_tgs1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserName = edt_user_name.getText().toString();
                UserPhone = edt_user_phone.getText().toString();
                UserStreet = edt_user_street.getText().toString();
                UserCode = edt_user_code.getText().toString();
                UserAge = edt_user_age.getText().toString();
                user = new UserModel();

                InternetHelper internet = new InternetHelper();
                boolean b = internet.TestConnection(RegisterActivity.this);
                if(b){
                    if(!str_region.equals("Selecione uma Região*")){
                        if(!UserNeighborhood.equals("Selecione um Bairro*")){
                            if(UserName.isEmpty()){
                                new ShowMessage().showSnackBar(findViewById(R.id.activity_cadastro),
                                        "Por favor, insira seu nome");
                            }else if(UserPhone.isEmpty()){
                                new ShowMessage().showSnackBar(findViewById(R.id.activity_cadastro),
                                        "Número de usuário inválido, deve conter 11 dígitos");
                            }else if(UserStreet.isEmpty()){
                                new ShowMessage().showSnackBar(findViewById(R.id.activity_cadastro),
                                        "Por favor, insira o nome da rua");
                            }else if(UserAge.isEmpty()){
                                new ShowMessage().showSnackBar(findViewById(R.id.activity_cadastro),
                                        "Por favor, insira a idade");
                            }else{

                                if(UserCode.isEmpty()){
                                    UserCode = "";
                                }

                                user.setName(UserName);
                                user.setPhone(UserPhone);
                                user.setStreet(UserStreet);
                                user.setCode(UserCode);
                                user.setNeighborhood(UserNeighborhood);
                                user.setAge(Integer.parseInt(UserAge));

                                Retrofit retrofit = buildRetrofit(BaseUrl.URL);
                                final UserController userController = retrofit.create(UserController.class);
                                Call<Boolean> bool = userController.verifyNumber(user.getPhone());
                                bool.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if(response.isSuccessful()){
                                            if(response.body()){
                                                Call<UserModel> userModel = userController.getUser(user.getPhone());
                                                userModel.enqueue(new Callback<UserModel>() {
                                                    @Override
                                                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                                        if(response.isSuccessful()){

                                                        }else{
                                                            Log.i("Erro", "" + response.code());
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<UserModel> call, Throwable t) {

                                                    }
                                                });
                                            }
                                        }else{
                                            Log.i("Error", "" + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {

                                    }
                                });
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

    private Retrofit buildRetrofit(String baseurl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
