package com.izv.fragmentoshorver;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentoDos extends Fragment {



    TextView tvTituloFoto;
    ImageView iv;
    View v;

    public FragmentoDos() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragmento_dos, container, false);
        iv =(ImageView) v.findViewById(R.id.ivFotos);
        return v;
    }

    public void setText(String s){
        this.tvTituloFoto = (TextView)v.findViewById(R.id.tvTituloFoto);
        this.tvTituloFoto.setText(s);
    }


}
