package com.example.simple_pocket_money_entry.list;

import java.util.List;

// 상위 리사이클러뷰 아이템클래스를 정의
public class ListItem {
    private String itemDate, itemFullDate, itemContent, itemCategory, itemAmount, itemFullAmount;

    public ListItem(String itemDate, String itemFullDate, String itemContent, String itemCategory, String itemAmount, String itemFullAmount) {
        this.itemDate = itemDate;
        this.itemFullDate = itemFullDate;
        this.itemContent = itemContent;
        this.itemCategory = itemCategory;
        this.itemAmount = itemAmount;
        this.itemFullAmount = itemFullAmount;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemFullDate() {
        return itemFullDate;
    }

    public void setItemFullDate(String itemFullDate) {
        this.itemFullDate = itemFullDate;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemFullAmount() {
        return itemFullAmount;
    }

    public void setItemFullAmount(String itemFullAmount) {
        this.itemFullAmount = itemFullAmount;
    }

}

