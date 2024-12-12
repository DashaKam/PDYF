package com.example.pdyf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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
        List<Integer> colors = new ArrayList<>(); // Список для цветов

        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getTotalSpent() > 0) {
                categories.add(new PieEntry((float) categoryList.get(i).getTotalSpent(), "")); // Название оставлено пустым
                colors.add(ColorTemplate.MATERIAL_COLORS[i % ColorTemplate.MATERIAL_COLORS.length]); // Сохранить цвет
            }
        }

// Создание отфильтрованного списка категорий
        List<Category> filteredCategories = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getTotalSpent() > 0) {
                filteredCategories.add(category);
            }
        }

// Создание адаптера и передача списка цветов
        categoryAdapter = new CategoryAdapter(filteredCategories, colors);

        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создание PieDataSet
        PieDataSet pieDataSet = new PieDataSet(categories, null); // Имя набора данных установлено в null
        pieDataSet.setColors(colors); // Используем список цветов

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Отключение описания и анимация
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
                    double totalSpent = entry.getValue();
                    // Обновить текстовое поле или панель внизу, чтобы отобразить сумму
                }
            }

            @Override
            public void onNothingSelected() {
                // Убрать или очистить текст, когда ничего не выбрано
            }
        });

// Обновляем диаграмму
        pieChart.invalidate();

    }

    public void goBack(View v) {
        Intent intent = new Intent(this, ActivityReport.class);
        startActivity(intent);
    }
}