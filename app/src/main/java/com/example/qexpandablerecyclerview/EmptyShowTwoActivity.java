package com.example.qexpandablerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.qexpandablerecyclerview.adapter.RecyclerViewAdapter;
import com.example.qexpandablerecyclerview.bean.GroupBean;
import com.example.qexpandablerecyclerview.view.QRecyclerViewEmptySupport;

import java.util.ArrayList;

public class EmptyShowTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_show_two);

        QRecyclerViewEmptySupport recyclerView = findViewById(R.id.empty_and_expandable_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View inflate = View.inflate(this, R.layout.view_empty_status, null);
        recyclerView.setEmptyView(inflate);
        recyclerView.setAdapter(new RecyclerViewAdapter(new ArrayList<GroupBean>())); //可以不setAdapter，与setEmptyView无次序关系
    }
}
