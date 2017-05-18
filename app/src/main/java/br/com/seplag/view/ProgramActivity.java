package br.com.seplag.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.adapters.SpinnerAdapter;
import br.com.seplag.controller.ProgramController;
import br.com.seplag.helper.ArrayHelper;
import br.com.seplag.helper.InternetHelper;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.ProgramModel;

public class ProgramActivity extends AppCompatActivity {
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
    private ProgramModel program;
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
    private Uri filePath;
    private ImageView tv_image;
    private String activite = "";
    private String str_region = "";
    private String neighborhood = "";
    private String str_program =  "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        final Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params != null){
            str_program = params.getString("name_program");
        }

        program = new ProgramModel();
        internet  = new InternetHelper();

        session = new UserSessionHelper(this);
        Map<String, String> mapUser = session.getUserDetails();
        userName = mapUser.get("Name");
        userScore = mapUser.get("Score");
        userOffice = mapUser.get("Office");
        userId = mapUser.get("ID");

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        bt_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internet.TestConnection(ProgramActivity.this)) {
                    String reference_point = referencepoint.getText().toString();
                    String street_name = street.getText().toString();
                    String user_comment = comment.getText().toString();

                    ProgramController controller = new ProgramController();

                    if(!activite.equals("Selecione uma Atividade*")){
                        if(!str_region.equals("Selecione uma Região*")){
                            if(!neighborhood.equals("Selecione um Bairro*")){
                                if(!street_name.isEmpty()) {
                                    String program_name = str_program.replaceAll(" ", "_").toUpperCase();
                                    program.setUser_id(Integer.parseInt(userId));
                                    program.setService_program(activite);
                                    program.setName_neighborhood(neighborhood);
                                    program.setName_program(program_name);
                                    program.setName_street(street_name);
                                    program.setPoste(poste.getText().toString());
                                    if(reference_point.isEmpty()) {
                                        program.setReference_point("");
                                    }else {
                                        program.setReference_point(reference_point);
                                    }
                                    if (user_comment.isEmpty()) {
                                        program.setUser_comment("");
                                    } else {
                                        program.setUser_comment(user_comment);
                                    }
                                    if(image_encode == null){
                                        program.setImage("");
                                    }else{
                                        program.setImage(image_encode);
                                    }
                                    controller.CreateProgram(ProgramActivity.this, program, new ProgramController.VolleyCallback() {
                                        @Override
                                        public void onSuccess(String result) {
                                            AlertDialog alertConnection;
                                            AlertDialog.Builder builderConnection = new AlertDialog.Builder(ProgramActivity.this);
                                            builderConnection.setTitle(getResources().getString(R.string.app_name));
                                            builderConnection.setMessage("Obrigado pela sua contribuição, ele é muito importante para" + " Caruaru!");
                                            builderConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {Intent intent = new Intent(ProgramActivity.this, MainActivity.class);
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
                                                    CreateDialog(ProgramActivity.this, "Tempo expirado, verifique" +
                                                            "sua internet e tente novamente...");
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(ProgramActivity.this, "Por favor, insira o nome da rua...", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(ProgramActivity.this, "Por favor, selecione um bairro...", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(ProgramActivity.this, "Por favor, selecione uma região...", Toast.LENGTH_LONG).show();
                            }
                    }else{
                        Toast.makeText(ProgramActivity.this, "Por favor, selecione a atividade que você deseja...", Toast.LENGTH_LONG).show();
                    }
                } else{
                    CreateDialog(ProgramActivity.this, "Sem conexão com internet, por favor conecte-se...");
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
                            Intent intent = new Intent(ProgramActivity.this, HowWorks.class);
                            startActivity(intent);
                        }

                        if(position == 4){
                            result.closeDrawer();
                            Intent intent = new Intent(ProgramActivity.this, AwardsActivity.class);
                            startActivity(intent);
                        }

                        if(position == 5){
                            result.closeDrawer();
                            Intent intent = new Intent(ProgramActivity.this, CompleteRegister.class);
                            startActivity(intent);
                        }

                        if(position == 6){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Digits.logout();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 3000);
                            session.logoutUser();
                            result.closeDrawer();
                            Intent intent = new Intent(ProgramActivity.this, LoginActivity.class);
                            startActivity(intent);
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
                showFileChooser();
            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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

}
