package com.example.simple_pocket_money_entry;

// 자식 아이템
public class ListSubItem {
    private int subItemImage;
    private String subItemTitle;
    private String subItemDesc;

    public ListSubItem(String subItemTitle, String subItemDesc) {
        this.subItemTitle = subItemTitle;
        this.subItemDesc = subItemDesc;
    }

    public int getSubItemImage() {
        return subItemImage;
    }

    public void setSubItemImage(int subItemImage) {
        this.subItemImage = subItemImage;
    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }

    public String getSubItemDesc() {
        return subItemDesc;
    }

    public void setSubItemDesc(String subItemDesc) {
        this.subItemDesc = subItemDesc;
    }
}


