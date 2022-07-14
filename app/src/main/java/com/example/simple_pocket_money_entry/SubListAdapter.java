package com.example.simple_pocket_money_entry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.SubViewHolder> {

    private List<ListSubItem> listSubItem;

    public SubListAdapter(List<ListSubItem> dataSet) {
        this.listSubItem = dataSet;
    }

    public static class SubViewHolder extends RecyclerView.ViewHolder {
        private TextView subTitle;
        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            subTitle = itemView.findViewById(R.id.sub_item_title);
        }
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_item_list, parent, false);
        SubListAdapter.SubViewHolder viewHolder = new SubListAdapter.SubViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder subViewHolder, int position) {
        ListSubItem subItem = listSubItem.get(position);
        subViewHolder.subTitle.setText(subItem.getSubItemTitle());
    }

    @Override
    public int getItemCount() {
        return listSubItem.size();
    }
}
