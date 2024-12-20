package com.example.pdyf.TransactionManager.Transactions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pdyf.TransactionManager.Categories.Category;
import com.example.pdyf.DateBase.DataBaseHandlerCategory;
import com.example.pdyf.DateBase.DataBaseHandlerTransaction;
import com.example.pdyf.MainActivity;
import com.example.pdyf.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
        //dataBaseHandlerTransaction.onUpgrade(dataBaseHandlerTransaction.getWritableDatabase(), 0, 1);

        loadCategories();
        loadType();

        dateEditText.setOnClickListener(v -> showDatePickerDialog());

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем значение
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    }

    private void loadCategories() {
        List<Category> categories = databaseHandler.getAllCategories();
        List<String> categoriesNameList = new ArrayList<>();
        categoriesNameList.add("Категории");
        for (int i = 0; i < categories.size(); i++) {
            categoriesNameList.add(categories.get(i).getName());
        }
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, categoriesNameList);

        categorySpinner.setAdapter(adapter);
    }

    private void loadType() {
        List<String> typeList = new ArrayList<>();
        typeList.add("Тип транзакции");
        typeList.add("Получили");
        typeList.add("Потратили");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, typeList);

        typeSpinner.setAdapter(adapter);

    }

    public void toMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showDatePickerDialog() {
        // Получаем текущую дату
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Создаем и показываем DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Форматируем выбранную дату
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    dateEditText.setText(formatter.format(selectedDate.getTime()));
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
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

        // Создаем временную транзакцию
        Transaction tmpTransaction = new Transaction(categoryId, Double.parseDouble(amount), date, selectedType);
        dataBaseHandlerTransaction.addTransaction(tmpTransaction);

        Log.d("Transaction info: ", "Id " + tmpTransaction.getId() + ", Category: " + tmpTransaction.getCategoryId() + ", Amount: " + tmpTransaction.getAmount() + " Date: " + tmpTransaction.getDate() + " Type: " + tmpTransaction.getType());

        // Если транзакция является тратой, обновляем сумму totalSpent в категории
        if (Objects.equals(selectedType, "Потратили")) {
            databaseHandler.addAmountToCategory(selectedCategory, Double.parseDouble(amount));
            Log.d("Categories: ", selectedCategory + "amount: " + amount);
        }

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