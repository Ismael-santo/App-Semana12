package com.itca.appmysql.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itca.appmysql.R;


public class Incio extends Fragment {



    public Incio() {

    }



    // TODO: Rename and change types and number of parameters
    public static Incio newInstance(String param1, String param2) {
        Incio fragment = new Incio();
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
        return inflater.inflate(R.layout.fragment_incio, container, false);
    }
}