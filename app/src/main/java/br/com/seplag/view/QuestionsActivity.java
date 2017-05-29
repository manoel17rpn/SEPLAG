package br.com.seplag.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.Digits;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.controller.ProgramController;
import br.com.seplag.controller.QuestionsController;
import br.com.seplag.controller.UserController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.OfficeHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.GameAuxModel;
import br.com.seplag.model.GameOptionsModel;

public class QuestionsActivity extends AppCompatActivity {
    private QuestionsController controller;
    private String user_id;
    private String userName;
    private String userScore;
    private String userOffice;
    private int count;
    private ArrayList<GameOptionsModel> list;
    private Button bt_option1;
    private Button bt_option2;
    private Button bt_option3;
    private Button bt_option4;
    private TextView tv_area;
    private InternetHelper internet;
    private UserSessionHelper session;
    private String userSex;
    private Drawer result;
    private List<String> listAreas;
    private ArrayList<GameOptionsModel> listaReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        internet = new InternetHelper();
        count = 0;

        bt_option1 = (Button) findViewById(R.id.answer1);
        bt_option2 = (Button) findViewById(R.id.answer2);
        bt_option3 = (Button) findViewById(R.id.answer3);
        bt_option4 = (Button) findViewById(R.id.answer4);
        tv_area = (TextView) findViewById(R.id.tv_areaquestions);
        listAreas = new ArrayList<>();

        session = new UserSessionHelper(QuestionsActivity.this);
        Map<String, String> user = session.getUserDetails();
        user_id = user.get("ID");
        userName = user.get("Name");
        userScore = user.get("Score");
        userOffice = user.get("Office");
        userSex = user.get("Sex");

        final Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            list = (ArrayList<GameOptionsModel>) params.get("list");
            listaReserva = (ArrayList<GameOptionsModel>) params.get("list");
            for(GameOptionsModel game : list){
                listAreas.add(game.getArea_questions());
            }
            ChangeButtonText(count);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("O que precisamos?");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        bt_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet.TestConnection(QuestionsActivity.this)) {
                    if (AuxCount(count, list.size())) {
                        SendAnswerNoMoreQuestions("option_one");
                    } else {
                        SendAnswerAndMoreQuestions("option_one");
                    }
                }else{
                    CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        bt_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet.TestConnection(QuestionsActivity.this)) {
                    if (AuxCount(count, list.size())) {
                        SendAnswerNoMoreQuestions("option_two");
                    } else {
                        SendAnswerAndMoreQuestions("option_two");
                    }
                }else{
                    CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        bt_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet.TestConnection(QuestionsActivity.this)) {
                    if (AuxCount(count, list.size())) {
                        SendAnswerNoMoreQuestions("option_three");
                    } else {
                        SendAnswerAndMoreQuestions("option_three");
                    }
                }else{
                    CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        bt_option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet.TestConnection(QuestionsActivity.this)) {
                    if (AuxCount(count, list.size())) {
                        SendAnswerNoMoreQuestions("option_four");
                    } else {
                        SendAnswerAndMoreQuestions("option_four");
                    }
                }else{
                    CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .withSavedInstance(savedInstanceState)
                .withSelectedItemByPosition(2)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if(position == 2){
                            GetAllOptions();
                            result.closeDrawer();
                        }

                        if(position == 3){
                            GetOptionsByArea("Qualidade de Vida");
                            result.closeDrawer();
                        }

                        if(position == 4){
                            GetOptionsByArea("Educação");
                            result.closeDrawer();
                        }

                        if(position == 5){
                            GetOptionsByArea("Saúde");
                            result.closeDrawer();
                        }

                        if(position == 6){
                            GetOptionsByArea("Esporte, Cultura e Lazer");
                            result.closeDrawer();
                        }

                        if(position == 7){
                            GetOptionsByArea("Direitos das Minorias");
                            result.closeDrawer();
                        }

                        return true;
                    }
                }).build();

        result.addItem(new PrimaryDrawerItem().withName("Áreas"));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName("Todas"));
        result.addItem(new PrimaryDrawerItem().withName("Qualidade de Vida"));
        result.addItem(new PrimaryDrawerItem().withName("Educação"));
        result.addItem(new PrimaryDrawerItem().withName("Saúde"));
        result.addItem(new PrimaryDrawerItem().withName("Esporte, Cultura e Lazer"));
        result.addItem(new PrimaryDrawerItem().withName("Direitos das Minorias"));

    }

    public boolean AuxCount(int count, int listSize){
        if((count + 1) == listSize){
            return true;
        }else{
            return false;
        }
    }

    public void ChangeButtonText(int count){
        tv_area.setText(list.get(count).getArea_questions());
        bt_option1.setText(list.get(count).getOption_one());
        bt_option2.setText(list.get(count).getOption_two());
        bt_option3.setText(list.get(count).getOption_three());
        bt_option4.setText(list.get(count).getOption_four());
    }

    public GameAuxModel AuxObject(int options_id, String user_answer, String name_list){
        GameAuxModel aux = new GameAuxModel();
        aux.setOptions_id(options_id);
        aux.setUser_answers(user_answer);
        aux.setUser_id(Integer.parseInt(user_id));
        aux.setName_list(name_list);

        return aux;
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

    public void SendAnswerAndMoreQuestions(String option){
        int options_id = list.get(count).getOptions_id();
        String name_list = list.get(count).getName_list();
        final GameOptionsModel gop = list.get(count);
        controller = new QuestionsController();
        controller.CreateAnswer(QuestionsActivity.this, AuxObject(options_id, option, name_list),
                new ProgramController.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        listaReserva.remove(gop);
                        list.remove(gop);
                        //count++;
                        ChangeButtonText(count);
                    }

                    @Override
                    public void onFailed(int result, String menssager) {
                        if (result == 0) {
                            Toast.makeText(QuestionsActivity.this, "Tempo expirado, verifique" +
                                            "sua internet e tente novamente...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SendAnswerNoMoreQuestions(String option){
        //Fazer requisição e ao final encerrar, sem count++
        int options_id = list.get(count).getOptions_id();
        String name_list = list.get(count).getName_list();
        final GameOptionsModel gop = list.get(count);
        controller = new QuestionsController();
        controller.CreateAnswer(QuestionsActivity.this, AuxObject(options_id, option, name_list),
                new ProgramController.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        AlertDialog alertConnection;

                        if(!(listaReserva.size() > 1)){
                            AlertDialog.Builder builderConnection = new AlertDialog.Builder(QuestionsActivity.this);
                            builderConnection.setTitle(getResources().getString(R.string.app_name));
                            builderConnection.setMessage("Uau, você respondeu todas! Obrigado pela sua contribuição, ela é muito importante para" +
                                    " Caruaru! Em breve teremos mais opções!");
                            builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            alertConnection = builderConnection.create();
                            alertConnection.show();
                        }else{

                            AlertDialog.Builder builderConnection = new AlertDialog.Builder(QuestionsActivity.this);
                            builderConnection.setTitle(getResources().getString(R.string.app_name));
                            builderConnection.setMessage("Parabéns! Você respondeu todas nesta área, mas outras áreas esperam por você!");
                            builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    listaReserva.remove(gop);
                                    list.remove(gop);
                                    GetAllOptions();
                                }
                            });
                            alertConnection = builderConnection.create();
                            alertConnection.show();
                        }
                    }

                    @Override
                    public void onFailed(int result, String menssager) {
                        if (result == 0) {
                            Toast.makeText(QuestionsActivity.this, "Tempo expirado, verifique" +
                                            "sua internet e tente novamente...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GetOptionsByArea(String area){
        ArrayList<GameOptionsModel> lista = new ArrayList<>();
        if(listAreas.contains(area)){
            for(int i = 0; i < listaReserva.size(); i++){
                if(listaReserva.get(i).getArea_questions().equals(area)){
                    lista.add(listaReserva.get(i));
                }
            }
            list = lista;
            count = 0;
            ChangeButtonText(count);
        }else{
            CreateDialog(QuestionsActivity.this, "Você já respondeu todas nessa área, em breve teremos mais!");
            result.setSelection(0);
        }
        /*if(listaReserva.size() > 0){
            list = listaReserva;
            count = 0;
            ChangeButtonText(count);
        }else{
            CreateDialog(QuestionsActivity.this, "Você já respondeu todas, em breve teremos mais!");
        }*/

    }

    public void GetAllOptions(){
        result.setSelection(0);
        list = listaReserva;
        count = 0;
        ChangeButtonText(0);
    }

}
