package br.com.seplag.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.seplag.R;
import br.com.seplag.adapters.ProgramAdapter;
import br.com.seplag.interfaces.RecyclerViewOnClickListenerHack;
import br.com.seplag.model.Program;
import br.com.seplag.view.ListProgramsActivity;
import br.com.seplag.view.MainActivity;
import br.com.seplag.view.ProgramActivity;

/**
 * Created by Manoel Neto on 05/04/2017.
 */

public class ProgramFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private List<Program> mList = new ArrayList<>();
    String[] Titulo = new String[]{"Cidade Limpa", "Praça Nova", "Ilumina Caruaru"};
    String[] Info = new String[]{"O Cidade Limpa é um programa para limpar a cidade.", "O Ilumina Caruaru é um programa que vai iluminar a cidade.", "O Praça Nova é um programa que construir novas praças."};
    int[] photos = new int[]{R.drawable.cidadelimpa, R.drawable.cidadelimpa, R.drawable.cidadelimpa};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mList.size() > 0 & mRecyclerView != null) {
            mRecyclerView.setAdapter(new ProgramAdapter(getContext(), mList));
        }
        mRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    public void initializeList() {
        mList.clear();

        for(int i = 0; i < 3; i++){
            Program item = new Program();
            item.setTitleProgram(Titulo[i]);
            item.setInfoProgram(Info[i]);
            item.setImageProgram(photos[i]);
            mList.add(item);

        }
    }
}