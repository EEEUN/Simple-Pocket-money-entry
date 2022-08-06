package com.example.simple_pocket_money_entry.list;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.list_recycler_view);

        showList();     // about RecyclerView

        return view;
    }

    private void showList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM myBreakdown ORDER BY full_date",null);

        List<ListItem> itemList = new ArrayList<>();
        ListAdapter listAdapter = new ListAdapter(itemList);
        if(cursor!= null) {
            while (cursor.moveToNext()) {
                ListItem listItem = new ListItem(cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(7));
                itemList.add(listItem);
            }
        }
        recyclerView.setAdapter(listAdapter);
        recyclerView.setRecycledViewPool(viewPool);
    }
}