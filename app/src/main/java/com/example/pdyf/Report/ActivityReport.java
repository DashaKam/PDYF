package com.example.pdyf.Report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pdyf.R;

public class ActivityReport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
        public void report(View v){
            Intent intent = new Intent(this, ActivityChart.class);
            startActivity(intent);
    }

}