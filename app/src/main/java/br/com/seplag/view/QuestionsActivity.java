package br.com.seplag.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.controller.ProgramController;
import br.com.seplag.controller.QuestionsController;
import br.com.seplag.helper.AreasHelper;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.GameAuxModel;
import br.com.seplag.model.GameOptionsModel;

public class QuestionsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
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
    private String eixo;
    private AreasHelper areaHelper;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        internet = new InternetHelper();
        count = 0;

        bt_option1 = (Button) findViewById(R.id.answer1);
        bt_option2 = (Button) findViewById(R.id.answer2);
        bt_option3 = (Button) findViewById(R.id.answer3);
        bt_option4 = (Button) findViewById(R.id.answer4);
        tv_area = (TextView) findViewById(R.id.tv_areaquestions);
        listAreas = new ArrayList<>();
        areaHelper = new AreasHelper();

        session = new UserSessionHelper(QuestionsActivity.this);
        final Map<String, String> user = session.getUserDetails();
        user_id = user.get("ID");
        userName = user.get("Name");
        userScore = user.get("Score");
        userOffice = user.get("Office");
        userSex = user.get("Sex");

        final Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            list = (ArrayList<GameOptionsModel>) params.get("list");
            eixo = params.getString("eixo");
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
                if (isLocationEnabled()) {
                    checkLocation();
                    if (internet.TestConnection(QuestionsActivity.this)) {
                        if (AuxCount(count, list.size())) {
                            SendAnswerNoMoreQuestions("option_one");
                        } else {
                            SendAnswerAndMoreQuestions("option_one");
                        }
                    } else {
                        CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                    }
                }else{
                    showAlert();
                }
            }
        });

        bt_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocationEnabled()) {
                    if (internet.TestConnection(QuestionsActivity.this)) {
                        if (AuxCount(count, list.size())) {
                            SendAnswerNoMoreQuestions("option_two");
                        } else {
                            SendAnswerAndMoreQuestions("option_two");
                        }
                    } else {
                        CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                    }
                }else{
                    showAlert();
                }
            }
        });

        bt_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocationEnabled()) {
                    if (internet.TestConnection(QuestionsActivity.this)) {
                        if (AuxCount(count, list.size())) {
                            SendAnswerNoMoreQuestions("option_three");
                        } else {
                            SendAnswerAndMoreQuestions("option_three");
                        }
                    } else {
                        CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                    }
                }else{
                    showAlert();
                }
            }
        });

        bt_option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocationEnabled()) {
                    if (internet.TestConnection(QuestionsActivity.this)) {
                        if (AuxCount(count, list.size())) {
                            SendAnswerNoMoreQuestions("option_four");
                        } else {
                            SendAnswerAndMoreQuestions("option_four");
                        }
                    } else {
                        CreateDialog(QuestionsActivity.this, "Sem conexão com internet, por favor conecte-se...");
                    }
                } else {
                    showAlert();
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

                        if (position == 2) {
                            GetAllOptions();
                            result.closeDrawer();
                        } else {
                            GetOptionsByArea(areaHelper.GetArea(eixo, position));
                            result.closeDrawer();
                        }

                        return true;
                    }
                }).build();

        result.addItem(new PrimaryDrawerItem().withName("Áreas").withSelectable(false));
        result.addItem(new DividerDrawerItem().withSelectable(false));
        result.addItem(new PrimaryDrawerItem().withName("Todas"));
        if (eixo.equals("eixo1")) {
            result.addItem(new PrimaryDrawerItem().withName("Saúde"));
            result.addItem(new PrimaryDrawerItem().withName("Educação"));
            result.addItem(new PrimaryDrawerItem().withName("Esporte e Lazer"));
            result.addItem(new PrimaryDrawerItem().withName("Desenvolvimento Social"));
            result.addItem(new PrimaryDrawerItem().withName("Mulher"));
            result.addItem(new PrimaryDrawerItem().withName("Direitos Humanos"));
        } else if (eixo.equals("eixo2")) {
            result.addItem(new PrimaryDrawerItem().withName("Desenvolvimento Rural"));
            result.addItem(new PrimaryDrawerItem().withName("Meio Ambiente"));
            result.addItem(new PrimaryDrawerItem().withName("Emprego e Renda"));
            result.addItem(new PrimaryDrawerItem().withName("Inovação"));
            result.addItem(new PrimaryDrawerItem().withName("Cultura"));
        } else if (eixo.equals("eixo3")) {
            result.addItem(new PrimaryDrawerItem().withName("Obras e Serviços"));
            result.addItem(new PrimaryDrawerItem().withName("Segurança"));
            result.addItem(new PrimaryDrawerItem().withName("Estrada e Mobilidade"));
            result.addItem(new PrimaryDrawerItem().withName("Moradia"));
            result.addItem(new PrimaryDrawerItem().withName("A Caruaru do Futuro"));
        } else if (eixo.equals("eixo4")) {
            result.addItem(new PrimaryDrawerItem().withName("Transparência"));
            result.addItem(new PrimaryDrawerItem().withName("Planejamento"));
            result.addItem(new PrimaryDrawerItem().withName("Equilibrio Financeiro"));
            result.addItem(new PrimaryDrawerItem().withName("Redução de Gastos"));
            result.addItem(new PrimaryDrawerItem().withName("Aumento de Eficiência"));
        }

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

    public GameAuxModel AuxObject(int options_id, String user_answer, String area) {
        GameAuxModel aux = new GameAuxModel();
        aux.setOptions_id(options_id);
        aux.setUser_answers(user_answer);
        aux.setUser_id(Integer.parseInt(user_id));
        aux.setArea(area);
        aux.setLatitude(Double.toString(latitude));
        aux.setLongitude(Double.toString(longitude));

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
        String area = list.get(count).getArea_questions();
        final GameOptionsModel gop = list.get(count);
        controller = new QuestionsController();
        controller.CreateAnswer(QuestionsActivity.this, AuxObject(options_id, option, area),
                new ProgramController.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        listaReserva.remove(gop);
                        list.remove(gop);
                        //count++;
                        ChangeButtonText(count);
                        Toast.makeText(QuestionsActivity.this, "Você ganhou 100 pontos!", Toast.LENGTH_SHORT).show();
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
        String area = list.get(count).getArea_questions();
        final GameOptionsModel gop = list.get(count);
        controller = new QuestionsController();
        controller.CreateAnswer(QuestionsActivity.this, AuxObject(options_id, option, area),
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
                                    Intent intent = new Intent(QuestionsActivity.this, EixosPpa.class);
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

                        Toast.makeText(QuestionsActivity.this, "Você ganhou 100 pontos!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    private void checkLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            Log.i("Lat", "" + latitude);
        }
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Localização")
                .setMessage("Sua localização está desativada.\nPor favor, ative a localização para" +
                        " finalizar sua contribuição.")
                .setPositiveButton("Ativar Localização", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.dismiss();
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

}
