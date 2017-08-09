package br.com.seplag.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.ProgramController;
import br.com.seplag.helper.ArrayHelper;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.SolicitationsModel;

public class SolicitationsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener  {
    private Spinner activities;
    private Spinner neighborhoods;
    private Spinner region;
    private EditText street;
    private EditText comment;
    private EditText referencepoint;
    private EditText poste;
    private Button bt_program;
    private Button bt_photo;
    private ArrayHelper arrayHelper = new ArrayHelper();
    private SolicitationsModel program;
    private Drawer result;
    private UserSessionHelper session;
    private String userName;
    private String userScore;
    private String userOffice;
    private String userId;
    private InternetHelper internet;
    private String image_encode;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private int TAKE_PICTURE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 200;
    private Uri filePath;
    private ImageView tv_image;
    private String activite = "";
    private String str_region = "";
    private String neighborhood = "";
    private String str_program = "";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private TextView txt_tgs;
    private TextView txt_tgs1;
    String userSex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        final Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            str_program = params.getString("name_program");
        }

        program = new SolicitationsModel();
        internet  = new InternetHelper();

        session = new UserSessionHelper(this);
        Map<String, String> mapUser = session.getUserDetails();
        userName = mapUser.get("Name");
        userScore = mapUser.get("Score");
        userOffice = mapUser.get("Office");
        userId = mapUser.get("ID");
        userSex = mapUser.get("Sex");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(str_program);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        activities = (Spinner) findViewById(R.id.activities);
        region = (Spinner) findViewById(R.id.region);
        neighborhoods = (Spinner) findViewById(R.id.neighborhood);
        referencepoint = (EditText) findViewById(R.id.reference_point);
        poste = (EditText) findViewById(R.id.poste);
        comment = (EditText) findViewById(R.id.user_comment);
        street = (EditText) findViewById(R.id.street);
        bt_program = (Button) findViewById(R.id.bt_program);
        bt_photo = (Button) findViewById(R.id.photo);
        tv_image = (ImageView) findViewById(R.id.success_photo);
        txt_tgs = (TextView) findViewById(R.id.txt_tgs);
        txt_tgs1 = (TextView) findViewById(R.id.txt_tgs1);

        final SpinnerAdapter adapterNeighborhoods = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterPrograms = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter adapterRegion = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        adapterPrograms.addAll(GetArrayActivitiesProgram(str_program));
        adapterPrograms.add("Selecione uma Atividade*");
        activities.setAdapter(adapterPrograms);
        activities.setSelection(adapterPrograms.getCount());

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

        activities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activite = activities.getItemAtPosition(position).toString();
                if(activite.equals("Troca de Lâmpadas")){
                    poste.setHint("Barramento/Número do Poste*");
                    poste.setVisibility(View.VISIBLE);
                }else{
                    poste.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        neighborhoods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                neighborhood = neighborhoods.getItemAtPosition(position).toString();
                if(str_region.equals("Zona Urbana")){
                    if(neighborhood.equals("SÃO FRANCISCO") || neighborhood.equals("MONTE DO BOM JESUS")||
                            neighborhood.equals("DIVINÓPOLIS") || neighborhood.equals("CENTRO")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Centro");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(neighborhood.equals("NOVA CARUARU") || neighborhood.equals("SEVERINO AFONSO")||
                            neighborhood.equals("LUIZ GONZAGA") || neighborhood.equals("MAURICIO DE NASSAU") ||
                            neighborhood.equals("FERNANDO LYRA") || neighborhood.equals("UNIVERSITÁRIO")||
                            neighborhood.equals("LAGOA DE ALGODÃO") || neighborhood.equals("PARQUE DA CIDADE")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Norte");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(neighborhood.equals("RENDEIRAS") || neighborhood.equals("ALTO DA BALANÇA")||
                            neighborhood.equals("CEDRO") || neighborhood.equals("CIDADE JARDIM") ||
                            neighborhood.equals("RIACHÃO") || neighborhood.equals("SALGADO")||
                            neighborhood.equals("SÃO JOÃO DA ESCÓCIA") || neighborhood.equals("LOT. SERRANÓPOLIS")||
                            neighborhood.equals("MORADA NOVA") || neighborhood.equals("SERRA DO VALE")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Leste");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(neighborhood.equals("PETRÓPOLIS") || neighborhood.equals("SANTA ROSA")||
                            neighborhood.equals("VASSOURAL") || neighborhood.equals("JARDIM LIBERDADE") ||
                            neighborhood.equals("ALTO DA BANANA") || neighborhood.equals("INDIANÓPOLIS")||
                            neighborhood.equals("INOCOOP") || neighborhood.equals("JOSÉ LIBERATO I E II")||
                            neighborhood.equals("AGAMENON MAGALHÃES") || neighborhood.equals("WILTON LIRA")||
                            neighborhood.equals("PITOMBEIRAS I E II") || neighborhood.equals("ENCANTO DA SERRA")||
                            neighborhood.equals("ADALGISA NUNES I, II E III") || neighborhood.equals("ROSANÓPOLIS")||
                            neighborhood.equals("PINHEIRÓPOLIS")){

                        txt_tgs.setVisibility(View.VISIBLE);
                        txt_tgs.setText("Você está na no TGS Sul");
                        txt_tgs1.setVisibility(View.VISIBLE);
                    }
                    if(neighborhood.equals("VILA ANDORINHA") || neighborhood.equals("MARIA AUXILIADORA")||
                            neighborhood.equals("BOA VISTA I E II") || neighborhood.equals("JARDIM PANORAMA I E II") ||
                            neighborhood.equals("JARDIM BOA VISTA") || neighborhood.equals("CAIUCÁ")||
                            neighborhood.equals("JOÃO MOTA") || neighborhood.equals("JOSÉ CARLOS DE OLIVEIRA")||
                            neighborhood.equals("VILA KENNEDY") || neighborhood.equals("SOL POENTE")||
                            neighborhood.equals("VILA DO AEROPORTO") || neighborhood.equals("DEMÓSTENES VERAS")||
                            neighborhood.equals("LOT. MOURA BRASIL") || neighborhood.equals("LOT. JOÃO BARRETO")||
                            neighborhood.equals("LOT. NOVO MUNDO") || neighborhood.equals("VILA PADRE INÁCIO")){

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


        bt_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internet.TestConnection(SolicitationsActivity.this)) {
                    String reference_point = referencepoint.getText().toString();
                    String street_name = street.getText().toString();
                    String user_comment = comment.getText().toString();
                    ProgramController controller = new ProgramController();

                    if (isLocationEnabled()) {
                        if (!activite.equals("Selecione uma Atividade*")) {
                            if (!str_region.equals("Selecione uma Região*")) {
                                if (!neighborhood.equals("Selecione um Bairro*")) {
                                    if (!street_name.isEmpty()) {
                                        String program_name = str_program.replaceAll(" ", "_").toUpperCase();
                                        program.setUser_id(Integer.parseInt(userId));
                                        //program.setProgram_service(activite);
                                        //program.setNeighborhood_name(neighborhood);
                                        program.setProgram_name(program_name);
                                        //program.setStreet_name(street_name);
                                        program.setLatitude("0");
                                        program.setLongitude("0");
                                        checkLocation();

                                        if(poste.getText().toString().isEmpty()){
                                            //program.setBarramento("");
                                        }else{
                                            //program.setBarramento(poste.getText().toString());
                                        }
                                        if (reference_point.isEmpty()) {
                                            program.setReference_point("");
                                        } else {
                                            program.setReference_point(reference_point);
                                        }
                                        if (user_comment.isEmpty()) {
                                            //program.setUser_comment("");
                                        } else {
                                            //program.setUser_comment(user_comment);
                                        }
                                        if (image_encode == null) {
                                            //program.setImage("");
                                        } else {
                                            //program.setImage(image_encode);
                                        }
                                        controller.CreateProgram(SolicitationsActivity.this, program, new ProgramController.VolleyCallback() {
                                            @Override
                                            public void onSuccess(String result) {
                                                AlertDialog alertConnection;
                                                AlertDialog.Builder builderConnection = new AlertDialog.Builder(SolicitationsActivity.this);
                                                builderConnection.setTitle(getResources().getString(R.string.app_name));
                                                builderConnection.setMessage("Obrigado pela sua contribuição, ela é muito importante para" + " Caruaru!");
                                                builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(SolicitationsActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                                alertConnection = builderConnection.create();
                                                alertConnection.show();
                                            }
                                                @Override
                                                public void onFailed(int result, String menssager) {
                                                    if (result == 0) {
                                                        CreateDialog(SolicitationsActivity.this, "Tempo expirado, verifique" +
                                                                " sua internet e tente novamente...");
                                                    }
                                                }
                                            });
                                    } else {
                                        Toast.makeText(SolicitationsActivity.this, "Por favor, insira o nome da rua...", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(SolicitationsActivity.this, "Por favor, selecione um bairro...", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                Toast.makeText(SolicitationsActivity.this, "Por favor, selecione uma região...", Toast.LENGTH_LONG).show();
                                }
                        } else {
                            Toast.makeText(SolicitationsActivity.this, "Por favor, selecione a atividade que você deseja...", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        showAlert();
                    }
                } else {
                    CreateDialog(SolicitationsActivity.this, "Sem conexão com internet, por favor conecte-se...");
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

                        if(position == 3){
                            result.closeDrawer();
                            Intent intent = new Intent(SolicitationsActivity.this, HowWorks.class);
                            startActivity(intent);
                        }

                        if(position == 4){
                            result.closeDrawer();
                            Intent intent = new Intent(SolicitationsActivity.this, AwardsActivity.class);
                            startActivity(intent);
                        }

                        if(userSex.equals("null")){
                            if(position == 5){
                                result.closeDrawer();
                                Intent intent = new Intent(SolicitationsActivity.this, CompleteRegister.class);
                                startActivity(intent);
                                finish();
                            }

                            if(position == 6){
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
                                Intent intent = new Intent(SolicitationsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            if(position == 5){
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
                                Intent intent = new Intent(SolicitationsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        return true;
                    }
                }).build();

        result.addItem(new PrimaryDrawerItem().withName("Pontos").withBadge(userScore));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName("Como Funciona?"));
        result.addItem(new PrimaryDrawerItem().withName("Prêmios"));
        result.addItem(new PrimaryDrawerItem().withName("Completar Cadastro"));
        result.addItem(new PrimaryDrawerItem().withName("Sair"));

        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image_encode = getStringImage(bitmap);
                tv_image.setVisibility(View.VISIBLE);
                tv_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && data != null){
            bitmap = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this
            tv_image.setVisibility(View.VISIBLE);
            tv_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent cameraIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    CreateDialog(this, "Para utilizar a camêra é necessário sua permissão!");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    checkLocation();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    CreateDialog(this, "Para continuar é necessário permitir a localização!");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 500, 400, true);
        resized.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    public String[] GetArrayActivitiesProgram(String program){
        String[] activities = null;

        if(program.equals("Cidade Limpa")){
            activities = arrayHelper.getArrayActivitiesCidadeLimpa();
        }else if(program.equals("Ilumina Caruaru")){
            activities = arrayHelper.getArrayActivitiesIlumina();
        }else{
            activities = arrayHelper.getArrayActivitiesCidadeLimpa();
        }

        return  activities;
    }

    private void selectImage() {
        final CharSequence[] options = { "Camêra", "Galeria","Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SolicitationsActivity.this);
        builder.setTitle("Adicionar Foto!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camêra")) {

                    if (ContextCompat.checkSelfPermission(SolicitationsActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(SolicitationsActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);


                        if (ActivityCompat.shouldShowRequestPermissionRationale(SolicitationsActivity.this,
                                Manifest.permission.CAMERA)) {


                        } else {

                            ActivityCompat.requestPermissions(SolicitationsActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                        }
                    }else{
                        Intent cameraIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                    }

                } else if (options[item].equals("Galeria")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
    }

    private void checkLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SolicitationsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


            if (ActivityCompat.shouldShowRequestPermissionRationale(SolicitationsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(
                            SolicitationsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {

                ActivityCompat.requestPermissions(SolicitationsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
        }else{
            startLocationUpdates();

            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(mLocation == null){
                startLocationUpdates();
            }
            if (mLocation != null) {
                latitude = mLocation.getLatitude();
                longitude = mLocation.getLongitude();

                program.setLatitude(Double.toString(latitude));
                program.setLongitude(Double.toString(longitude));
            }
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
                startActivity(new Intent(SolicitationsActivity.this, AboutApp.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
