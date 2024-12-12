//package com.example.piechartdemo
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.anychart.AnyChart
//import com.anychart.AnyChartView
//import com.anychart.chart.common.dataentry.DataEntry
//import com.anychart.chart.common.dataentry.ValueDataEntry
//import com.anychart.charts.Pie
//
//import com.example.pdyf.R
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var anyChartView: AnyChartView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        anyChartView = findViewById(R.id.any_chart_view)
//
//        // Настройка круговой диаграммы
//        setupPieChart()
//    }
//
//    private fun setupPieChart() {
//        // Создание объекта диаграммы
//        val pie: Pie = AnyChart.pie()
//
//        // Создание списка данных для диаграммы
//        val data = arrayListOf<ValueDataEntry>(
//            ValueDataEntry("First", 200),
//            ValueDataEntry("Second", 300),
//            ValueDataEntry("Third", 400)
//        )
//
//        // Добавление данных в диаграмму
//        pie.data(data as List<DataEntry>?)
//
//        // Установка диаграммы в AnyChartView
//        anyChartView.setChart(pie)
//
//        // Дополнительные настройки (например, заголовок)
//        pie.title("Пример круговой диаграммы")
//    }
//}