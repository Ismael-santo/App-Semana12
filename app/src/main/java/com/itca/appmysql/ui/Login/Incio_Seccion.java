package com.itca.appmysql.ui.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itca.appmysql.R;


public class Incio_Seccion extends Fragment {



    public Incio_Seccion() {

    }


    // TODO: Rename and change types and number of parameters
    public static Incio_Seccion newInstance(String param1, String param2) {
        Incio_Seccion fragment = new Incio_Seccion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incio__seccion, container, false);
    }
}