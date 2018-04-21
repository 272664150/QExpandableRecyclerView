package com.example.qexpandablerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.qexpandablerecyclerview.adapter.RecyclerViewAdapter;
import com.example.qexpandablerecyclerview.bean.ChildBean;
import com.example.qexpandablerecyclerview.bean.GroupBean;
import com.example.qexpandablerecyclerview.view.QRecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

public class EmptyShowOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_show_one);

        final QRecyclerViewEmptySupport recyclerView = findViewById(R.id.empty_and_expandable_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_empty_status, null, false);
        recyclerView.setEmptyView(inflate);
        recyclerView.setAdapter(new RecyclerViewAdapter(new ArrayList<GroupBean>()));

        Switch statusSwitch = findViewById(R.id.status_switch);
        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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
                } else {
                    recyclerView.setAdapter(new RecyclerViewAdapter(new ArrayList<GroupBean>()));
                }
            }
        });

        Button floatBtn = findViewById(R.id.float_btn);
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click FloatBtn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
