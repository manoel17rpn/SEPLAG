package br.com.seplag.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.ProgramController;
import br.com.seplag.helper.ArrayHelper;
import br.com.seplag.model.ProgramModel;

public class ProgramActivity extends AppCompatActivity {
    private Spinner activities;
    private Spinner neighborhoods;
    private EditText street;
    private EditText comment;
    private EditText referencepoint;
    private Button bt_program;
    private ArrayHelper arrayHelper = new ArrayHelper();
    private ProgramModel program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Nome do Programa");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        program = new ProgramModel();

        activities = (Spinner) findViewById(R.id.spinner);
        neighborhoods = (Spinner) findViewById(R.id.neighborhood);
        referencepoint = (EditText) findViewById(R.id.reference_point);
        comment = (EditText) findViewById(R.id.user_comment);
        street = (EditText) findViewById(R.id.street);
        bt_program = (Button) findViewById(R.id.bt_program);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            program.setName_program(params.getString("name_program"));
        }

        SpinnerAdapter adapterNeighborhoods = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterPrograms = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        adapterPrograms.addAll(arrayHelper.getArrayProgram());
        adapterPrograms.add("Selecione uma Atividade*");
        activities.setAdapter(adapterPrograms);
        activities.setSelection(adapterPrograms.getCount());

        adapterNeighborhoods.addAll(arrayHelper.getArrayNeighborhoods());
        adapterNeighborhoods.add("Selecione um Bairro*");
        neighborhoods.setAdapter(adapterNeighborhoods);
        neighborhoods.setSelection(adapterNeighborhoods.getCount());

        bt_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String neighborhood = neighborhoods.getSelectedItem().toString();
                String activite = activities.getSelectedItem().toString();
                String reference_point = referencepoint.getText().toString();
                String street_name = street.getText().toString();
                String user_comment = comment.getText().toString();

                ProgramController controller = new ProgramController();

                if(!neighborhood.equals("Selecione um Bairro*")){
                    if(!activite.equals("Selecione uma Atividade*")){
                        if(!street_name.isEmpty()){
                            program.setUser_id(1);
                            program.setService_program(activite);
                            program.setName_neighborhood(neighborhood);
                            program.setName_street(street_name);
                            if(reference_point.isEmpty()){
                                program.setReference_point("");
                            }else {
                                program.setReference_point(reference_point);
                            }

                            if(user_comment.isEmpty()){
                                program.setUser_comment("");
                            }else{
                                program.setUser_comment(user_comment);
                            }

                            controller.CreateProgram(ProgramActivity.this, program, new ProgramController.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    AlertDialog alertConnection;

                                    AlertDialog.Builder builderConnection = new AlertDialog.Builder(ProgramActivity.this);
                                    builderConnection.setTitle(getResources().getString(R.string.app_name));
                                    builderConnection.setMessage("Obrigado pela sua contribuição, ele é muito importante para" +
                                            " Caruaru!");
                                    builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ProgramActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    alertConnection = builderConnection.create();
                                    alertConnection.show();
                                }

                                @Override
                                public void onFailed(int result, String menssager) {
                                    if(result == 0){
                                        Toast.makeText(ProgramActivity.this, "Tempo expirado, verifique" +
                                                "sua internet e tente novamente...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(ProgramActivity.this, "Por favor, insira o nome da rua...", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(ProgramActivity.this, "Por favor, selecione a atividade que você deseja...",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ProgramActivity.this, "Por favor selecione o bairro...", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
