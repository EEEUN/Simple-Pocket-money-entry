package com.example.simple_pocket_money_entry.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_pocket_money_entry.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ListItem> listItem;

    // 생성자를 통해서 데이터를 전달받도록 한다.
    public ListAdapter(List<ListItem> listItem) {
        this.listItem = listItem;
    }

    // ViewHolder Class를 선언한다.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView subView;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            subView = itemView.findViewById(R.id.sub_item);
        }
    }

    // ViewHolder 객체를 생성하여 리턴한다.
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);

        return viewHolder;
    }

    // ViewHolder 안에 있는 내용을 position에 해당되는 데이터로 교체한다.
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder viewHolder, int position) {
        ListItem item = this.listItem.get(position);
        viewHolder.title.setText(item.getItemTitle());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                viewHolder.subView.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        // 자식 어답터 설정
        SubListAdapter subListAdapter = new SubListAdapter(item.getSubItemList());

        viewHolder.subView.setLayoutManager(layoutManager);
        viewHolder.subView.setAdapter(subListAdapter);
        viewHolder.subView.setRecycledViewPool(viewPool);
    }

    // 전체 데이터의 개수를 리턴한다.
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
