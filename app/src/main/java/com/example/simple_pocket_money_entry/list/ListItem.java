package com.example.simple_pocket_money_entry.list;

import java.util.List;

// 상위 리사이클러뷰 아이템클래스를 정의
public class ListItem {
    private String itemDate, itemContent, itemCategory, itemAmount;

    public ListItem(String itemTitle, String itemContent, String itemCategory, String itemAmount) {
        this.itemDate = itemTitle;
        this.itemContent = itemContent;
        this.itemCategory = itemCategory;
        this.itemAmount = itemAmount;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
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

}

