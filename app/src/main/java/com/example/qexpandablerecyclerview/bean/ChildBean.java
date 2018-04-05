package com.example.qexpandablerecyclerview.bean;

import java.io.Serializable;

public class ChildBean implements Serializable {
    private static final long serialVersionUID = 6524050317067717837L;

    private String mTitle;
    private String mDesc;

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
}
