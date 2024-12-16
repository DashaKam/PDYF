package com.example.pdyf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pdyf.DateBase.DataBaseHandlerCategory;
import com.example.pdyf.Goals.ActivityGoal;
import com.example.pdyf.Report.ActivityChart;
import com.example.pdyf.TransactionManager.Calculator.ActivityCalculator;
import com.example.pdyf.TransactionManager.Categories.Category;
import com.example.pdyf.TransactionManager.Transactions.ActivityAddTransaction;

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

    public void search(View v){
        Intent intent = new Intent(this, ActivitySearch.class);
        startActivity(intent);
    }



}
