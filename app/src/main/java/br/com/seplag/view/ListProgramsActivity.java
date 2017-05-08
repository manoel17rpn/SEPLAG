package br.com.seplag.view;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import br.com.seplag.R;
import br.com.seplag.fragments.ProgramFragment;
import br.com.seplag.model.Program;

public class ListProgramsActivity extends AppCompatActivity {

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

    }

    public List<Program> getSetProgramList(int qtd){
        String[] Titulo = new String[]{"Cidade Limpa", "Praça Nova", "Ilumina Caruaru"};
        String[] Info = new String[]{"O Cidade Limpa é um programa para limpar a cidade.", "O Ilumina Caruaru é um programa que vai iluminar a cidade.", "O Praça Nova é um programa que construir novas praças."};
        int[] photos = new int[]{R.drawable.cidadelimpa, R.drawable.cidadelimpa, R.drawable.cidadelimpa};
        List<Program> listAux = new ArrayList<>();

        for(int i = 0; i < Titulo.length; i++){
            Program c = new Program( Titulo[i % Titulo.length], Info[ i % Info.length ], photos[i % Titulo.length] );
            listAux.add(c);
        }
        return(listAux);
    }
}
