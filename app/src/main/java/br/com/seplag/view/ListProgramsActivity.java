package br.com.seplag.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.seplag.R;
import br.com.seplag.fragments.ProgramFragment;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.Program;

public class ListProgramsActivity extends AppCompatActivity {
    Drawer result;
    UserSessionHelper session;
    String userName;
    String userScore;
    String userOffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_programs);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Programas da Prefeitura");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        ProgramFragment frag = (ProgramFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new ProgramFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }


        session = new UserSessionHelper(this);
        Map<String, String> mapUser = session.getUserDetails();
        userName = mapUser.get("Name");
        userScore = mapUser.get("Score");
        userOffice = mapUser.get("Office");

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
                            Toast.makeText(ListProgramsActivity.this, "Como Funciona", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            //startActivity(intent);
                        }

                        if(position == 4){
                            result.closeDrawer();
                            Toast.makeText(ListProgramsActivity.this, "Prêmios", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            //startActivity(intent);
                        }

                        if(position == 5){
                            result.closeDrawer();
                            Toast.makeText(ListProgramsActivity.this, "Sobre", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            //startActivity(intent);
                        }

                        if(position == 6){
                            session.logoutUser();
                            result.closeDrawer();
                            Intent intent = new Intent(ListProgramsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        return true;
                    }
                }).build();

        result.addItem(new PrimaryDrawerItem().withName("Pontos").withBadge(userScore));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName("Como Funciona?"));
        result.addItem(new PrimaryDrawerItem().withName("Prêmios"));
        result.addItem(new PrimaryDrawerItem().withName("Sobre"));
        result.addItem(new PrimaryDrawerItem().withName("Sair"));

    }
}
