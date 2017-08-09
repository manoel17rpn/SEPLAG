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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;

public class CompleteRegister extends AppCompatActivity {
    private Spinner spinner_sex;
    private Spinner spinner_scholarity;
    private Spinner spinner_residents;
    private Spinner spinner_income;
    private Button bt_complete;
    private String UserId;
    private String[] sex = {"Feminino", "Masculino", "Outros"};
    private String[] scholarity = {"1° Grau Completo", "1° Grau Incompleto", "2° Grau Completo", "2° Grau Incompleto",
                                    "Ensino Superior Completo", "Ensino Superior Incompleto"};
    private String[] residents = {"Entre 1 e 3 Pessoas", "Entre 3 e 5 Pessoas", "Mais que 5 Pessoas"};
    private String[] income = {"Até R$3000,00", "Mais de R$3000,00"};
    private UserSessionHelper session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);

        session = new UserSessionHelper(CompleteRegister.this);
        Map<String, String> map = session.getUserDetails();
        UserId = map.get("ID");

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Completar Cadastro");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        spinner_sex = (Spinner) findViewById(R.id.sex);
        spinner_income = (Spinner) findViewById(R.id.income);
        spinner_residents = (Spinner) findViewById(R.id.number_residents);
        spinner_scholarity = (Spinner) findViewById(R.id.scholarity);
        bt_complete = (Button) findViewById(R.id.bt_complete);

        SpinnerAdapter adapterSex = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterScholarity = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterResidents = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterIncome = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        adapterSex.addAll(sex);
        adapterSex.add("Informe seu sexo*");
        spinner_sex.setAdapter(adapterSex);
        spinner_sex.setSelection(adapterSex.getCount());

        adapterScholarity.addAll(scholarity);
        adapterScholarity.add("Informe seu grau de escolaridade*");
        spinner_scholarity.setAdapter(adapterScholarity);
        spinner_scholarity.setSelection(adapterScholarity.getCount());

        adapterResidents.addAll(residents);
        adapterResidents.add("Quantas pessoas moram com você?*");
        spinner_residents.setAdapter(adapterResidents);
        spinner_residents.setSelection(adapterResidents.getCount());

        adapterIncome.addAll(income);
        adapterIncome.add("Qual a sua renda?*");
        spinner_income.setAdapter(adapterIncome);
        spinner_income.setSelection(adapterIncome.getCount());

        bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_sex = spinner_sex.getSelectedItem().toString();
                final String str_scholarity = spinner_scholarity.getSelectedItem().toString();
                final String str_income = spinner_income.getSelectedItem().toString();
                final String str_residents = spinner_residents.getSelectedItem().toString();

                if(new InternetHelper().TestConnection(CompleteRegister.this)) {

                    if (!str_sex.equals("Informe seu sexo*") && !str_income.equals("Qual a sua renda?")
                            && !str_scholarity.equals("Informe seu grau de escolaridade*")
                            && !str_residents.equals("Quantas pessoas moram com você?")) {

                        UserController controller = new UserController();

                        controller.UpdateUser(CompleteRegister.this, Integer.parseInt(UserId), str_sex, str_scholarity, str_residents, str_income,
                                new UserController.VolleyCallbackUpdateUser() {
                                    @Override
                                    public void onResult(boolean resultOffice) {
                                        if (resultOffice) {
                                            session.UpdateRegisterUser(str_sex, str_scholarity, str_residents, str_income);
                                            CreateDialog(CompleteRegister.this, "Parabéns, você completou o cadastro! Você ganhou mais 100 pontos!", true);
                                        } else {
                                            Toast.makeText(CompleteRegister.this, "Ops, algo deu errado! Tente novamente.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    } else {
                        CreateDialog(CompleteRegister.this, "Para ganhar a pontuação extra, é necessário preencher todos os campos.", false);
                    }
                }else {
                    CreateDialog(CompleteRegister.this, "Sem conexão com a internet, por favor, conecte-se!", false);
                }
            }
        });

    }

    public void CreateDialog(Context mContext, String text, final boolean b){
        final AlertDialog alertConnection;

        AlertDialog.Builder builderConnection = new AlertDialog.Builder(mContext);
        builderConnection.setTitle(getResources().getString(R.string.app_name));
        builderConnection.setMessage(text);
        builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(b){
                    dialog.dismiss();
                    Intent intent = new Intent(CompleteRegister.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    dialog.dismiss();
                }
            }
        });
        alertConnection = builderConnection.create();
        alertConnection.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CompleteRegister.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about_app:
                startActivity(new Intent(CompleteRegister.this, AboutApp.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
