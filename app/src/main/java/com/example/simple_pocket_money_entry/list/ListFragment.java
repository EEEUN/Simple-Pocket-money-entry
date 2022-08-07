package com.example.simple_pocket_money_entry.list;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_pocket_money_entry.DBHelper;
import com.example.simple_pocket_money_entry.R;
import com.example.simple_pocket_money_entry.add.AddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListFragment extends Fragment implements View.OnClickListener {
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView recyclerView;
    private String listMonth;
    private TextView listMonthView;
    private boolean isFullList = false;
    private ImageView leftButton, rightButton;
    private ImageButton typeButton;
    private FloatingActionButton addButton;

    Calendar calendar = Calendar.getInstance();
    DateFormat monthFormat = new SimpleDateFormat("yyyy/MM", Locale.KOREA);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.list_recycler_view);
        listMonthView = view.findViewById(R.id.change_month_center);
        leftButton = view.findViewById(R.id.change_month_left);
        rightButton = view.findViewById(R.id.change_month_right);
        typeButton = view.findViewById(R.id.list_type_button);
        addButton = view.findViewById(R.id.add_button);

        Date date = new Date();
        listMonth = monthFormat.format(date);

        listMonthView.setText(listMonth);
        showList(listMonth);

        typeButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.list_type_button:
                if(isFullList == false) {
                    isFullList = true;
                    listMonthView.setText("전체보기");
                    leftButton.setVisibility(View.INVISIBLE);
                    rightButton.setVisibility(View.INVISIBLE);
                } else {
                    isFullList = false;
                    listMonthView.setText(listMonth);
                    leftButton.setVisibility(View.VISIBLE);
                    rightButton.setVisibility(View.VISIBLE);
                }
                showList(listMonth);
                break;
            case R.id.change_month_left:        // 이전 달 전환
                calendar.add (calendar.MONTH, - 1);
                listMonth = monthFormat.format(calendar.getTime());
                listMonthView.setText(listMonth);
                showList(listMonth);
                break;
            case R.id.change_month_right:       // 다음 달 전환
                calendar.add (calendar.MONTH, + 1);
                listMonth = monthFormat.format(calendar.getTime());
                listMonthView.setText(listMonth);
                showList(listMonth);
                break;
            case R.id.add_button:
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showList(listMonth);
    }

    private void showList(String month) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        // 날짜별로 정렬해서 리스트를 출력하면, db 정렬 순서와 리스트 정렬 순서가 달라 내역을 수정하거나 삭제할 때 꼬인다ㅜㅜ
        Cursor cursor = db.rawQuery("SELECT * FROM myList ORDER BY full_date",null);
        //Cursor cursor = db.rawQuery("SELECT * FROM myList",null);

        List<ListItem> itemList = new ArrayList<>();
        ListAdapter listAdapter = new ListAdapter(itemList);
        if(cursor!= null) {
            while(cursor.moveToNext()) {
                if(isFullList == true) {    // 전체보기
                    ListItem listItem = new ListItem(cursor.getString(2), cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6), cursor.getString(7));
                    itemList.add(listItem);
                } else {                    // 월별 보기
                    if(cursor.getString(3).contains(month)) {
                        ListItem listItem = new ListItem(cursor.getString(2), cursor.getString(3), cursor.getString(4),
                                cursor.getString(5), cursor.getString(6), cursor.getString(7));
                        itemList.add(listItem);
                    }
                }
            }
        }
        recyclerView.setAdapter(listAdapter);
        recyclerView.setRecycledViewPool(viewPool);
    }
}