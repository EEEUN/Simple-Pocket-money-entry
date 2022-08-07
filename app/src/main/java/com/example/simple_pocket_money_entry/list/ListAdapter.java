package com.example.simple_pocket_money_entry.list;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_pocket_money_entry.R;
import com.example.simple_pocket_money_entry.add.EditActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListItem> listItem;

    // 생성자를 통해서 데이터를 전달받도록 한다.
    public ListAdapter(List<ListItem> listItem) {
        this.listItem = listItem;
    }

    // ViewHolder Class를 선언한다.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, fullDate, content, category, amount, fullAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            content = itemView.findViewById(R.id.item_content);
            category = itemView.findViewById(R.id.item_category);
            fullAmount = itemView.findViewById(R.id.item_amount);
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
        viewHolder.date.setText(item.getItemDate());
        viewHolder.content.setText(item.getItemContent());
        viewHolder.category.setText(item.getItemCategory());
        viewHolder.fullAmount.setText(item.getItemFullAmount());

        if(viewHolder.fullAmount.getText().toString().contains("-")) {
            viewHolder.fullAmount.setTextColor(Color.parseColor("#000000"));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditActivity.class);

                int id = viewHolder.getAdapterPosition();
                Log.d("flo###", "onClick: 건네주는 id " + id);

                // below we are passing all our values.
                intent.putExtra("id", id);
                intent.putExtra("date", item.getItemDate());
                intent.putExtra("fullDate", item.getItemFullDate());
                intent.putExtra("content", item.getItemContent());
                intent.putExtra("category", item.getItemCategory());
                intent.putExtra("amount", item.getItemAmount());
                intent.putExtra("fullAmount", item.getItemFullAmount());

                view.getContext().startActivity(intent);
            }
        });
    }

    // 전체 데이터의 개수를 리턴한다.
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
