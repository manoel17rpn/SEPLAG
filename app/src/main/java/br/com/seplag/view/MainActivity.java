package br.com.seplag.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import br.com.seplag.R;
import br.com.seplag.helper.UserSessionHelper;

public class MainActivity extends AppCompatActivity {
    Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Como deseja contribuir?");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        CardView cardQuizz = (CardView) findViewById(R.id.card_quizz);
        CardView cardPrograms = (CardView) findViewById(R.id.card_programs);

        cardPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListProgramsActivity.class);
                startActivity(intent);
            }
        });

        cardQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position == 2){
                            UserSessionHelper session = new UserSessionHelper(MainActivity.this);
                            session.logoutUser();
                            result.closeDrawer();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                }).build();

        result.addItem(new PrimaryDrawerItem().withName("Guia").withSelectable(false));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName("Pontuação"));
        result.addItem(new PrimaryDrawerItem().withName("Sobre"));
        result.addStickyFooterItem(new PrimaryDrawerItem().withName("Sair"));

    }
}
