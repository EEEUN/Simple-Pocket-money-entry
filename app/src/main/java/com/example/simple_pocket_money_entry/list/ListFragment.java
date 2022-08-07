package com.example.simple_pocket_money_entry.list;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_pocket_money_entry.DBHelper;
import com.example.simple_pocket_money_entry.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView recyclerView;
    private TextView listMonthView;
    private ImageView leftButton, rightButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.list_recycler_view);
        listMonthView = view.findViewById(R.id.change_month_center);
        leftButton =  view.findViewById(R.id.change_month_left);
        rightButton = view.findViewById(R.id.change_month_right);

        showList();     // about RecyclerView

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이전 달 전환
            }
        });
        
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다음 달 전환
            }
        });
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showList();
    }

    private void showList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        // 날짜별로 정렬해서 리스트를 출력하면, db 정렬 순서와 리스트 정렬 순서가 달라 내역을 수정하거나 삭제할 때 꼬인다ㅜㅜ
        //Cursor cursor = db.rawQuery("SELECT * FROM myList ORDER BY full_date",null);
        Cursor cursor = db.rawQuery("SELECT * FROM myList",null);


        List<ListItem> itemList = new ArrayList<>();
        ListAdapter listAdapter = new ListAdapter(itemList);
        if(cursor!= null) {
            while(cursor.moveToNext()) {
                ListItem listItem = new ListItem(cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
                itemList.add(listItem);
            }
        }
        recyclerView.setAdapter(listAdapter);
        recyclerView.setRecycledViewPool(viewPool);
    }
}