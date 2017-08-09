package br.com.seplag.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import br.com.seplag.R;
import br.com.seplag.controller.UserController;
import br.com.seplag.fragments.EixosFragment;
import br.com.seplag.fragments.ListProgramsFragment;
import br.com.seplag.helper.UserSessionHelper;

public class MainActivity extends AppCompatActivity {
    private Drawer result;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private UserSessionHelper session;
    private String userName;
    private String userScore;
    private String userOffice;
    private CardView cardQuestions;
    private CardView cardPrograms;
    private String user_id;
    private UserController userController;
    private String new_office;
    private String userSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Como deseja contribuir?");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Programas"));
        tabLayout.addTab(tabLayout.newTab().setText("A Caruaru que Precisamos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
                            Intent intent = new Intent(MainActivity.this, HowWorks.class);
                            startActivity(intent);
                        }

                        if(position == 4){
                            result.closeDrawer();
                            Intent intent = new Intent(MainActivity.this, AwardsActivity.class);
                            startActivity(intent);
                        }


                        if(position == 5){
                            result.closeDrawer();
                            Intent intent = new Intent(MainActivity.this, CompleteRegister.class);
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
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
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
                startActivity(new Intent(MainActivity.this, AboutApp.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ListProgramsFragment();
                default:
                    return new EixosFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Programas";
                case 1:
                    return "A Caruaru que Precisamos";
            }
            return null;
        }
    }
}
