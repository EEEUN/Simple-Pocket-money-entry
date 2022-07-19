package com.example.simple_pocket_money_entry.list;

import java.util.List;

// 상위 리사이클러뷰 아이템클래스를 정의
public class ListItem {
    private String itemTitle;
    // 하위 리사이클러뷰 아이템으로 정의한 subItemList를 전역변수로 선언한다.
    private List<ListSubItem> subItemList;

    public ListItem(String itemTitle, List<ListSubItem> subItemList) {
        this.itemTitle = itemTitle;
        // 하위 리사이클러뷰
        this.subItemList = subItemList;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<ListSubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<ListSubItem> subItemList) {
        this.subItemList = subItemList;
    }
}

