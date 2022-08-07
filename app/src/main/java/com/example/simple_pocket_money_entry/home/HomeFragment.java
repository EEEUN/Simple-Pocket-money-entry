package com.example.simple_pocket_money_entry.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.simple_pocket_money_entry.DBHelper;
import com.example.simple_pocket_money_entry.TableInfo;
import com.example.simple_pocket_money_entry.add.AddActivity;
import com.example.simple_pocket_money_entry.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "flo##";

    private int totalBalance;

    private TextView totalView, incomeView, expenseView;
    private AppCompatButton addButton, listButton, chartButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        totalView = view.findViewById(R.id.total_balance_text);
        listButton = view.findViewById(R.id.home_btn1);
        chartButton = view.findViewById(R.id.home_btn2);
        addButton = view.findViewById(R.id.home_btn3);

        listButton.setOnClickListener(this);
        chartButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        showBalance();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showBalance();
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

    private void showBalance() {
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(CASE type WHEN '수입' THEN amount WHEN '지출' THEN -amount END) FROM myList", null);
        if (cursor.moveToFirst())
            totalBalance = cursor.getInt(0);
        else
            totalBalance = -1;
        cursor.close();

        totalView.setText(totalBalance + "원");
    }

}