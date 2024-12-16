package com.example.pdyf.TransactionManager.Transactions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.DateBase.DataBaseHandlerTransaction;
import com.example.pdyf.MainActivity;
import com.example.pdyf.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitySearch extends AppCompatActivity {
    private EditText amountEditText;
    private Spinner typeSpinner;
    private String selectedType;
    private Button buttonSearch;
    private TransactionAdapter adapter;
    private RecyclerView recyclerView;
    private DataBaseHandlerTransaction databaseHandlerTransaction;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        typeSpinner = findViewById(R.id.type_spinner_2);
        amountEditText = findViewById(R.id.amount_2);
        buttonSearch = findViewById(R.id.buttonSearch);
        loadType();


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем значение
                selectedType = parent.getItemAtPosition(position).toString();
                Log.d("Type", selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Search: ", "Check if");
                if (amountEditText != null && selectedType != null) {
                    String amount = amountEditText.getText().toString();
                    if (!amount.isEmpty()) {
                        Log.d("Search: ", "Try load");
                        loadTransactions(amount, selectedType);
                        clearForm();
                        Log.d("Search: ", "In listener");
                    } else {
                        Log.e("Search", "Amount is empty");
                    }
                }
            }
        });
        databaseHandlerTransaction = new DataBaseHandlerTransaction(this);
    }

    private void loadType() {
        List<String> typeList = new ArrayList<>();
        typeList.add("Тип транзакции");
        typeList.add("Получили");
        typeList.add("Потратили");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, typeList);

        typeSpinner.setAdapter(adapter);

    }

    private void loadTransactions(String amount, String type) {
        List<Transaction> transactions = databaseHandlerTransaction.getTransactionsByAmountAndType(Double.parseDouble(amount), type);
        if (transactions != null) {
            adapter = new TransactionAdapter(transactions);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("Search", "No transactions found or transactions list is null");
        }
    }
    private void clearForm() {
        // Очищаем EditText
        amountEditText.setText("");

        // Сбрасываем Spinner для типа
        typeSpinner.setSelection(0);

        // Обнуляем выбранные значения
        selectedType = null;
    }
    public void toTransactionList(View v) {
        Intent intent = new Intent(this, ActivityItem.class);
        startActivity(intent);
    }
    public void toMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

