package br.com.seplag.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.controller.QuestionsController;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.GameOptionsModel;

public class EixosPpa extends AppCompatActivity {
    CardView eixo1;
    CardView eixo2;
    CardView eixo3;
    CardView eixo4;
    private UserSessionHelper session;
    private String user_id;
    private ArrayList<GameOptionsModel> arrayByArea;
    private Drawer result;
    private String userName;
    private String userScore;
    private String userOffice;
    private String userSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eixos_ppa);

        session = new UserSessionHelper(this);
        Map<String, String> mapUser = session.getUserDetails();
        user_id = mapUser.get("ID");
        userName = mapUser.get("Name");
        userScore = mapUser.get("Score");
        userOffice = mapUser.get("Office");
        userSex = mapUser.get("Sex");

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Escolha o Eixo");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        eixo1 = (CardView) findViewById(R.id.eixo1);
        eixo2 = (CardView) findViewById(R.id.eixo2);
        eixo3 = (CardView) findViewById(R.id.eixo3);
        eixo4 = (CardView) findViewById(R.id.eixo4);

        eixo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayByArea = new ArrayList<>();
                InternetHelper internetHelper = new InternetHelper();
                QuestionsController controller = new QuestionsController();
                if (internetHelper.TestConnection(EixosPpa.this)) {
                    controller.ListOptions(EixosPpa.this, Integer.parseInt(user_id), new QuestionsController.VolleyCallbackGetOptions() {
                        @Override
                        public void ListOptions(final ArrayList<GameOptionsModel> options) {
                            if (options.size() > 0) {
                                for (GameOptionsModel op : options) {
                                    if (op.getAxis().equals("Desenvolvimento humano, inclusão e direitos")) {
                                        arrayByArea.add(op);
                                    }
                                }
                                if (arrayByArea.size() > 0) {
                                    Bundle params = new Bundle();
                                    params.putSerializable("list", arrayByArea);
                                    params.putString("eixo", "eixo1");
                                    Intent intent = new Intent(EixosPpa.this, QuestionsActivity.class);
                                    intent.putExtras(params);
                                    startActivity(intent);
                                } else {
                                    CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                                }
                            } else {
                                CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                            }
                        }
                    });
                } else {
                    CreateDialog(EixosPpa.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        eixo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayByArea = new ArrayList<>();
                InternetHelper internetHelper = new InternetHelper();
                QuestionsController controller = new QuestionsController();
                if (internetHelper.TestConnection(EixosPpa.this)) {
                    controller.ListOptions(EixosPpa.this, Integer.parseInt(user_id), new QuestionsController.VolleyCallbackGetOptions() {
                        @Override
                        public void ListOptions(final ArrayList<GameOptionsModel> options) {
                            if (options.size() > 0) {
                                for (GameOptionsModel op : options) {
                                    if (op.getAxis().equals("Sustentabilidade e desenvolvimento econômico")) {
                                        arrayByArea.add(op);
                                    }
                                }
                                if (arrayByArea.size() > 0) {
                                    Bundle params = new Bundle();
                                    params.putSerializable("list", arrayByArea);
                                    params.putString("eixo", "eixo2");
                                    Intent intent = new Intent(EixosPpa.this, QuestionsActivity.class);
                                    intent.putExtras(params);
                                    startActivity(intent);
                                } else {
                                    CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                                }
                            } else {
                                CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                            }
                        }
                    });
                } else {
                    CreateDialog(EixosPpa.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        eixo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayByArea = new ArrayList<>();
                InternetHelper internetHelper = new InternetHelper();
                QuestionsController controller = new QuestionsController();
                if (internetHelper.TestConnection(EixosPpa.this)) {
                    controller.ListOptions(EixosPpa.this, Integer.parseInt(user_id), new QuestionsController.VolleyCallbackGetOptions() {
                        @Override
                        public void ListOptions(final ArrayList<GameOptionsModel> options) {
                            if (options.size() > 0) {
                                for (GameOptionsModel op : options) {
                                    if (op.getAxis().equals("Gestão e território da cidade")) {
                                        arrayByArea.add(op);
                                    }
                                }
                                if (arrayByArea.size() > 0) {
                                    Bundle params = new Bundle();
                                    params.putSerializable("list", arrayByArea);
                                    params.putString("eixo", "eixo3");
                                    Intent intent = new Intent(EixosPpa.this, QuestionsActivity.class);
                                    intent.putExtras(params);
                                    startActivity(intent);
                                } else {
                                    CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                                }
                            } else {
                                CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                            }
                        }
                    });
                } else {
                    CreateDialog(EixosPpa.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });

        eixo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayByArea = new ArrayList<>();
                InternetHelper internetHelper = new InternetHelper();
                QuestionsController controller = new QuestionsController();
                if (internetHelper.TestConnection(EixosPpa.this)) {
                    controller.ListOptions(EixosPpa.this, Integer.parseInt(user_id), new QuestionsController.VolleyCallbackGetOptions() {
                        @Override
                        public void ListOptions(final ArrayList<GameOptionsModel> options) {
                            if (options.size() > 0) {
                                for (GameOptionsModel op : options) {
                                    if (op.getAxis().equals("Planejamento, orçamento e gestão de finanças")) {
                                        arrayByArea.add(op);
                                    }
                                }
                                if (arrayByArea.size() > 0) {
                                    Bundle params = new Bundle();
                                    params.putSerializable("list", arrayByArea);
                                    params.putString("eixo", "eixo4");
                                    Intent intent = new Intent(EixosPpa.this, QuestionsActivity.class);
                                    intent.putExtras(params);
                                    startActivity(intent);
                                } else {
                                    CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                                }
                            } else {
                                CreateDialog(EixosPpa.this, "Opa! Você respondeu todas neste eixo! Em breve teremos mais para você contribuir!");
                            }
                        }
                    });
                } else {
                    CreateDialog(EixosPpa.this, "Sem conexão com internet, por favor conecte-se...");
                }
            }
        });


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimary)
                .withOnlySmallProfileImagesVisible(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(userName).withEmail(userOffice)
                                .withIcon(getResources().getDrawable(R.drawable.business))
                ).build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (position == 3) {
                            result.closeDrawer();
                            Intent intent = new Intent(EixosPpa.this, HowWorks.class);
                            startActivity(intent);
                        }

                        if (position == 4) {
                            result.closeDrawer();
                            Intent intent = new Intent(EixosPpa.this, AwardsActivity.class);
                            startActivity(intent);
                        }

                        if (userSex.equals("null")) {
                            if (position == 5) {
                                result.closeDrawer();
                                Intent intent = new Intent(EixosPpa.this, CompleteRegister.class);
                                startActivity(intent);
                                finish();
                            }

                            if (position == 6) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FirebaseAuth.getInstance().signOut();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 3000);
                                session.logoutUser();
                                result.closeDrawer();
                                Intent intent = new Intent(EixosPpa.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            if (position == 5) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            // Digits.logout();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 3000);
                                session.logoutUser();
                                result.closeDrawer();
                                Intent intent = new Intent(EixosPpa.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }

                        return true;
                    }
                }).build();

        result.addItem(new PrimaryDrawerItem().withName("Pontos").withBadge(userScore));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName("Como Funciona?"));
        result.addItem(new PrimaryDrawerItem().withName("Prêmios"));
        if (userSex.equals("null")) {
            result.addItem(new PrimaryDrawerItem().withName("Completar Cadastro"));
        }
        result.addItem(new PrimaryDrawerItem().withName("Sair"));
    }

    public void CreateDialog(Context mContext, String text) {
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
