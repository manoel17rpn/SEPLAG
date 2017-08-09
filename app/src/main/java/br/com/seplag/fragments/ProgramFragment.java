package br.com.seplag.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.seplag.R;
import br.com.seplag.adapters.ProgramAdapter;
import br.com.seplag.model.Program;

/**
 * Created by Manoel Neto on 05/04/2017.
 */

public class ProgramFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private List<Program> mList = new ArrayList<>();
    String[] Titulo = new String[]{"Cidade Limpa", "Ilumina Caruaru"};
    String[] Info = new String[]{"Atividades de limpeza do município.",
            "Manutenção da iluminação pública."};
    int[] photos = new int[]{R.drawable.bg_cidadelimpa, R.drawable.bg_ilumina};

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

        for(int i = 0; i < 2; i++){
            Program item = new Program();
            item.setTitleProgram(Titulo[i]);
            item.setInfoProgram(Info[i]);
            item.setImageProgram(photos[i]);
            mList.add(item);

        }
    }
}