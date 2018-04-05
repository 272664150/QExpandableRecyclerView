package com.example.qexpandablerecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.qexpandablerecyclerview.adapter.RecyclerViewAdapter;
import com.example.qexpandablerecyclerview.bean.ChildBean;
import com.example.qexpandablerecyclerview.bean.GroupBean;
import com.example.qexpandablerecyclerview.view.QRecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRecyclerViewEmptySupport recyclerView = findViewById(R.id.empty_and_expandable_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<GroupBean> groupList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            GroupBean groupBean = new GroupBean();
            groupBean.setTitle("分组" + i);
            groupBean.setDesc("共 " + i + " 项");
            List<ChildBean> childList = new ArrayList<>();
            for (int j = 1; j <= i; j++) {
                ChildBean childBean = new ChildBean();
                childBean.setTitle("分组: " + i);
                childBean.setDesc("子项: " + j);
                childList.add(childBean);
            }
            groupBean.setChildList(childList);
            groupList.add(groupBean);
        }
        recyclerView.setAdapter(new RecyclerViewAdapter(groupList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_show_one_btn:
                startActivity(new Intent(this, EmptyShowOneActivity.class));
                break;
            case R.id.empty_show_two_btn:
                startActivity(new Intent(this, EmptyShowTwoActivity.class));
                break;
            case R.id.empty_show_three_btn:
                startActivity(new Intent(this, EmptyShowThreeActivity.class));
                break;
            default:
                break;
        }
    }
}
