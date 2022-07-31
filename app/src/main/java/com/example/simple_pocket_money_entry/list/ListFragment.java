package com.example.simple_pocket_money_entry.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_pocket_money_entry.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // about RecyclerView
        recyclerView = view.findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);
        listAdapter = new ListAdapter(buildItemList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        recyclerView.setRecycledViewPool(viewPool);

        return view;
    }

    // 상위아이템 큰박스 아이템을 10개 만듭니다. (내역추가 기능 추가 시 삭제 예정)
    private List<ListItem> buildItemList() {
        List<ListItem> itemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ListItem listItem = new ListItem(i + " 2022/07/31", buildSubItemList());
            itemList.add(listItem);
        }
        return itemList;
    }
    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들) (내역추가 기능 추가 시 삭제 예정)
    private List<ListSubItem> buildSubItemList() {
        List<ListSubItem> subItemList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ListSubItem listSubItem = new ListSubItem("주식회사 드림에이스 "+i, "Description "+i);
            subItemList.add(listSubItem);
        }
        return subItemList;
    }
}