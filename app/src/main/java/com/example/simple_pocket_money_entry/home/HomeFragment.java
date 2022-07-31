package com.example.simple_pocket_money_entry.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.simple_pocket_money_entry.AddActivity;
import com.example.simple_pocket_money_entry.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "flo##";
    private AppCompatButton addButton;
    private AppCompatButton listButton;
    private AppCompatButton chartButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listButton = view.findViewById(R.id.home_btn1);
        chartButton = view.findViewById(R.id.home_btn2);
        addButton = view.findViewById(R.id.home_btn3);

        listButton.setOnClickListener(this);
        chartButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.home_btn1:
                ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.tab_list);
                break;
            case R.id.home_btn2:
                ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.tab_chart);
                break;
            case R.id.home_btn3:
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
                break;
        }
    }
}