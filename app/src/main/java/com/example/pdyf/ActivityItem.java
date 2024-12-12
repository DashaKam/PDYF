package com.example.pdyf;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
}

