package com.example.qexpandablerecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
