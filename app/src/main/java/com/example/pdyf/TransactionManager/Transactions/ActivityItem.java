package com.example.pdyf.TransactionManager.Transactions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.DateBase.DataBaseHandlerTransaction;
import com.example.pdyf.MainActivity;
import com.example.pdyf.R;
import com.example.pdyf.TransactionManager.Calculator.ActivityCalculator;

import java.util.List;

public class ActivityItem extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private DataBaseHandlerTransaction databaseHandlerTransaction;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        databaseHandlerTransaction = new DataBaseHandlerTransaction(this);
        loadTransactions();
    }
    private void loadTransactions() {
        List<Transaction> transactions = databaseHandlerTransaction.getAllTransactions();
        if (transactions != null) {
            adapter = new TransactionAdapter(transactions);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("ActivityItem", "No transactions found or transactions list is null");
        }
    }
    public void toAddTransaction(View v){
        Intent intent = new Intent(this, ActivityAddTransaction.class);
        startActivity(intent);
    }
    public void toMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

