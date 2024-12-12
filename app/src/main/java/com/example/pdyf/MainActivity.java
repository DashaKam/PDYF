package com.example.pdyf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHandlerCategory dataBaseHandlerCategory = new DataBaseHandlerCategory(this);
        dataBaseHandlerCategory.getWritableDatabase();
        List<Category> categoryList = dataBaseHandlerCategory.getAllCategories();

        // вывод бд в консоль
        for (Category category : categoryList) {
            Log.d("Categories info: ", "Id " + category.getId() + ", Name: " + category.getName() + ", Amount: " + category.getTotalSpent());
        }

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ActivityChart.class);
        startActivity(intent);
    }
    public void goal(View v){
        Intent intent = new Intent(this, ActivityGoal.class);
        startActivity(intent);
    }
    public void addTransaction(View v){
        Intent intent = new Intent(this, ActivityAddTransaction.class);
        startActivity(intent);
    }
    public void calculate(View v){
        Intent intent = new Intent(this, ActivityCalculator.class);
        startActivity(intent);
    }




}
