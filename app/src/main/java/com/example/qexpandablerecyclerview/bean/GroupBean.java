package com.example.qexpandablerecyclerview.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupBean implements Serializable {
    private static final long serialVersionUID = 7475090542038056759L;

    private String mTitle;
    private String mDesc;
    private List<ChildBean> mChildList = new ArrayList<>();

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    public List<ChildBean> getChildList() {
        return mChildList;
    }

    public void setChildList(List<ChildBean> childList) {
        this.mChildList = childList;
    }
}
