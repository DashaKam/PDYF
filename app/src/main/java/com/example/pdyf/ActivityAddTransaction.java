package com.example.pdyf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddTransaction extends AppCompatActivity {

    private Spinner categorySpinner;
    private Spinner typeSpinner;
    private DataBaseHandlerCategory databaseHandler;
    private DataBaseHandlerTransaction dataBaseHandlerTransaction;
    private EditText categoryEditText;
    private EditText dateEditText;
    private EditText amountEditText;
    private String selectedCategory;
    private String selectedType;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        categorySpinner = findViewById(R.id.category_spinner);
        typeSpinner = findViewById(R.id.type_spinner);
        databaseHandler = new DataBaseHandlerCategory(this);

        dateEditText = findViewById(R.id.date);
        amountEditText = findViewById(R.id.amount);

        dataBaseHandlerTransaction = new DataBaseHandlerTransaction(this);
        loadCategories();
        loadType();
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получяем значение
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получяем значение
                selectedType = parent.getItemAtPosition(position).toString();
                Log.d("Type", selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadCategories() {
        List<Category> categories = databaseHandler.getAllCategories();
        List<String> categoriesNameList = new ArrayList<>();
        categoriesNameList.add("Categories");
        for (int i = 0; i < categories.size(); i++) {
            categoriesNameList.add(categories.get(i).getName());
        }
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, categoriesNameList);

        categorySpinner.setAdapter(adapter);
    }


    private void loadType() {
        List<String> typeList = new ArrayList<>();
        typeList.add("Transaction type");
        typeList.add("Get");
        typeList.add("Spend");


        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, typeList);


        typeSpinner.setAdapter(adapter);

    }

    public void toMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void addTransaction(View v) {
        String date = dateEditText.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();

        int categoryId = databaseHandler.getCategoryIdByName(selectedCategory);

        if (selectedCategory == null || selectedCategory.equals("Categories")) {
            Toast.makeText(this, "Выберите категорию", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedType == null || selectedType.equals("Transaction type")) {
            Toast.makeText(this, "Выберите тип транзакции", Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.isEmpty()) {
            Toast.makeText(this, "Введите дату транзакции", Toast.LENGTH_SHORT).show();
            return;
        }
        if (amount.isEmpty()) {
            Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show();
            return;
        }
        Transaction tmpTransaction = new Transaction(categoryId, Double.parseDouble(amount), date, selectedType);
        dataBaseHandlerTransaction.addTransaction(tmpTransaction);
        Log.d("Transaction info: ", "Id " + tmpTransaction.getId() + ", Category: " + tmpTransaction.getCategoryId() + ", amount: " + tmpTransaction.getAmount() + " Date: " + tmpTransaction.getDate() + " Type: " + tmpTransaction.getType());

        clearForm();
        Toast.makeText(this, "Транзакция добавлена", Toast.LENGTH_SHORT).show();

    }



    private void clearForm() {
        // Очищаем EditText
        dateEditText.setText("");
        amountEditText.setText("");

        // Сбрасываем Spinner для категории
        categorySpinner.setSelection(0);

        // Сбрасываем Spinner для типа
        typeSpinner.setSelection(0);

        // Обнуляем выбранные значения
        selectedCategory = null;
        selectedType = null;
    }
    public void to_transaction(View v) {
        Intent intent = new Intent(this, ActivityItem.class);
        startActivity(intent);
    }


}