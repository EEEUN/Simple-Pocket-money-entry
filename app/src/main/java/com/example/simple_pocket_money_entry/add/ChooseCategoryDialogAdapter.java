package com.example.simple_pocket_money_entry.add;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.simple_pocket_money_entry.R;

public class ChooseCategoryDialogAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;

    ChooseCategoryDialogAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return textList.length;
    }

    @Override
    public Object getItem(int position) {
        return textList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        AlertDialogViewHolder alertDialogViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.dialog_choose_category, null);

            DisplayMetrics metrics = convertView.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;

            convertView.setLayoutParams(new GridView.LayoutParams(screenWidth / 6, screenWidth / 6));
            alertDialogViewHolder = new AlertDialogViewHolder();
            alertDialogViewHolder.text = convertView.findViewById(R.id.choose_category_entry);
            convertView.setTag(alertDialogViewHolder);
        } else {
            alertDialogViewHolder = (AlertDialogViewHolder) convertView.getTag();
        }

        alertDialogViewHolder.text.setPadding(8, 8, 8, 8);
        alertDialogViewHolder.text.setText(textList[position]);
        return convertView;
    }

    private String[] textList = {
            "식비", "카페",
            "문화/오락", "교통",
            "교육", "저축",
            "반려동물", "패션/미용",
            "생필품", "건강/병원",
            "대출이자", "주거비",
            "공과금", "보험",
            "경조사비", "기타"
    };

    private class AlertDialogViewHolder {
        TextView text;
    }
}
