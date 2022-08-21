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
import com.example.simple_pocket_money_entry.list.ListItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private int totalBalance = 0, monthIncome = 0, monthExpense = 0;
    private TextView totalView, incomeView, expenseView;
    private AppCompatButton addButton, listButton, chartButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        totalView = view.findViewById(R.id.total_balance_text);
        incomeView = view.findViewById(R.id.month_income_text);
        expenseView = view.findViewById(R.id.month_expense_text);

        listButton = view.findViewById(R.id.home_btn1);
        chartButton = view.findViewById(R.id.home_btn2);
        addButton = view.findViewById(R.id.home_btn3);

        listButton.setOnClickListener(this);
        chartButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        showBalance();
        showMonthBalance();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        totalBalance = 0;
        monthIncome = 0;
        monthExpense = 0;

        showBalance();
        showMonthBalance();
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

        String strTotalBalance = decimalFormat.format(totalBalance);
        totalView.setText(strTotalBalance + "원");
    }

    private void showMonthBalance() {
        DateFormat monthFormat = new SimpleDateFormat("yyyy/MM", Locale.KOREA);
        Date date = new Date();
        String currentMonth = monthFormat.format(date);

        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM myList",null);

        if(cursor!= null) {
            while(cursor.moveToNext()) {
                if(cursor.getString(3).contains(currentMonth)) {
                    if(cursor.getString(7).contains("-")) {
                        monthExpense = cursor.getInt(6) + monthExpense;
                    } else {
                        monthIncome = cursor.getInt(6) + monthIncome;
                    }
                }
            }
        }
        cursor.close();

        String strMonthIncome = decimalFormat.format(monthIncome);
        String strMonthExpense = decimalFormat.format(monthExpense);
        incomeView.setText("이번 달 수입 : " + strMonthIncome + "원");
        expenseView.setText("이번 달 지출 : " + strMonthExpense + "원");
    }
}