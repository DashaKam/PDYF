package com.example.pdyf.Report;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.DateBase.DataBaseHandlerCategory;
import com.example.pdyf.DateBase.DataBaseHandlerTransaction;
import com.example.pdyf.MainActivity;
import com.example.pdyf.R;
import com.example.pdyf.TransactionManager.Calculator.ActivityCalculator;
import com.example.pdyf.TransactionManager.Categories.Category;
import com.example.pdyf.TransactionManager.Categories.CategoryAdapter;
import com.example.pdyf.TransactionManager.Transactions.ActivityAddTransaction;
import com.example.pdyf.TransactionManager.Transactions.ActivityItem;
import com.example.pdyf.TransactionManager.Transactions.Transaction;
import com.example.pdyf.Util;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityChart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        recyclerView = findViewById(R.id.recyclerView);
        DataBaseHandlerTransaction dataBaseHandler = new DataBaseHandlerTransaction(this);

        // БД транзакций
        try (dataBaseHandler) {
            List<Transaction> transactionList = dataBaseHandler.getAllTransactions();
            // вывод бд в консоль
            for (Transaction transaction : transactionList) {
                Log.d("Transactions info: ", "Id " + transaction.getId() + ", Type: " + transaction.getType() + ", Amount: " + transaction.getAmount() + " Date: " + transaction.getDate() + " CategoryId: " + transaction.getCategoryId());
            }
        } catch (Exception e) {
            Log.d("Exception", "Transaction");
        }

        DataBaseHandlerCategory dataBaseHandlerCategory = new DataBaseHandlerCategory(this);
        // БД категорий
        try (dataBaseHandlerCategory) {

            List<Category> categoryList = dataBaseHandlerCategory.getAllCategories();

            // вывод бд в консоль
            for (Category category : categoryList) {
                Log.d("Categories info: ", "Id " + category.getId() + ", Name: " + category.getName() + ", Amount: " + category.getTotalSpent());
            }
        } catch (Exception e) {
            Log.d("Exception", "Category exception"); // Обработка исключений
        }


        PieChart pieChart = findViewById(R.id.chart);

        // Получение списка категорий
        List<Category> categoryList = dataBaseHandlerCategory.getAllCategories();

// Создание списка для данных круговой диаграммы
        List<PieEntry> categories = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        List<Category> filteredCategories = new ArrayList<>();

        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);

            // Добавление всех категорий, даже если потраченная сумма 0
            filteredCategories.add(category);


            categories.add(new PieEntry((float) category.getTotalSpent(), ""));

            // Назначение уникального цвета
            int colorIndex = i % Util.UNIQUE_COLORS.length; // Используем остаток для избежания выхода за пределы
            colors.add(Util.UNIQUE_COLORS[colorIndex]);

        }

// Создание адаптера и передача отфильтрованных категорий и их цветов
        CategoryAdapter categoryAdapter = new CategoryAdapter(filteredCategories, colors);

// Инициализация RecyclerView
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

// Создание PieDataSet
        PieDataSet pieDataSet = new PieDataSet(categories, null);
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);

// Установка кастомного отображения значений
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getPieLabel(float value, PieEntry entry) {
                return ""; // Возвращаем пустую строку для отображения названий
            }
        });

// Обработчик прокрутки, чтобы отображать сумму
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e instanceof PieEntry) {
                    PieEntry entry = (PieEntry) e;
                    // Здесь вы можете обновить текстовое поле или панель внизу, чтобы отобразить сумму
                }
            }

            @Override
            public void onNothingSelected() {
                // Убираем или очищаем текст, когда ничего не выбрано
            }
        });

// Обновляем диаграмму
        pieChart.invalidate();
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
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
    public void to_transaction(View v) {
        Intent intent = new Intent(this, ActivityItem.class);
        startActivity(intent);
    }
    public void to_report_with_period(View v) {
        Intent intent = new Intent(this, ActivityItem.class);
        startActivity(intent);
    }
}