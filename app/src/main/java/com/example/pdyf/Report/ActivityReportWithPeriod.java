package com.example.pdyf.Report;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.DateBase.DataBaseHandlerTransaction;
import com.example.pdyf.R;
import com.example.pdyf.TransactionManager.Transactions.ActivityAddTransaction;
import com.example.pdyf.TransactionManager.Transactions.ActivityItem;
import com.example.pdyf.TransactionManager.Transactions.Transaction;
import com.example.pdyf.TransactionManager.Transactions.TransactionAdapter;

import java.util.List;

public class ActivityReportWithPeriod extends AppCompatActivity {
    private EditText startDateEditText;
    private EditText endDateEditText;
    private Button buttonLook;
    private Button buttonBack;
    private TransactionAdapter adapter;
    private RecyclerView recyclerView;
    private DataBaseHandlerTransaction databaseHandlerTransaction;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_with_period);

        startDateEditText = findViewById(R.id.start_date);
        endDateEditText = findViewById(R.id.end_date);
        buttonLook = findViewById(R.id.buttonLook);
        buttonBack = findViewById(R.id.button16);

        recyclerView = findViewById(R.id.recyclerView_3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем даты
                String startDate = startDateEditText.getText().toString();
                String endDate = endDateEditText.getText().toString();

                if (!startDate.isEmpty() && !endDate.isEmpty()) {
                    loadTransactions(startDate, endDate);
                    clearForm();
                } else {
                    Log.e("Report", "Amount is empty");
                }
            }
        });
        databaseHandlerTransaction = new DataBaseHandlerTransaction(this);
    }

    private void loadTransactions(String startDate, String endDate) {
        List<Transaction> transactions = databaseHandlerTransaction.getTransactionsByDate(startDate, endDate);
        if (transactions != null) {
            adapter = new TransactionAdapter(transactions);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("Search", "No transactions found or transactions list is null");
        }
    }

    private void clearForm() {
        // Очищаем EditText
        startDateEditText.setText("");
        endDateEditText.setText("");

    }

    public void toAddTransaction(View v) {
        Intent intent = new Intent(this, ActivityAddTransaction.class);
        startActivity(intent);
    }


}
