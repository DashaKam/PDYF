package com.example.pdyf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ActivityGoal extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_goal);
//        EditText goalName = findViewById(R.id.goal);
//        EditText month = findViewById(R.id.month);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = findViewById(R.id.add_goal_button);
//        String name;
//        String month2;
//        button.setOnClickListener (
//                name = goalName.getText().toString().trim(),
//                month2 = month.getText().toString().trim()
//        )
//    }



    private EditText nameEditText;
    private EditText periodEditText;
    private EditText sumEditText;
    private DataBaseHandlerGoal databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);


        nameEditText = findViewById(R.id.goal);
        periodEditText = findViewById(R.id.period);
        sumEditText = findViewById(R.id.sum);

        databaseHelper = new DataBaseHandlerGoal(this);
    }

    public void addGoal(View view) {
        String name = nameEditText.getText().toString().trim();
        String period = periodEditText.getText().toString().trim();
        String sum = sumEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Введите название цели", Toast.LENGTH_SHORT).show();
            return;
        }
        if (period.isEmpty()) {
            Toast.makeText(this, "Введите период цели", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sum.isEmpty()) {
            Toast.makeText(this, "Введите сумму цели", Toast.LENGTH_SHORT).show();
            return;
        }

        // Вставка данных в базу данных
            Goal tmpGoal = new Goal(name,period, Double.parseDouble(sum));
            tmpGoal.setMonthlyPayment(tmpGoal.countMonthPayment(Double.parseDouble(sum), period));
            databaseHelper.addGoal(tmpGoal, this);

        Log.d("Goals info: ", "Id " + tmpGoal.getId() + ", Name: " + tmpGoal.getName() + ", payment: " + tmpGoal.getMonthlyPayment() + " Date: " + tmpGoal.getPeriod() + " Sum: " + tmpGoal.getSum());

            List<Goal> goalList = databaseHelper.getAllGoals();
            // вывод бд в консоль
            for (Goal goal : goalList) {
                Log.d("Goals info: ", "Id " + goal.getId() + ", Name: " + goal.getName() + ", payment: " + goal.getMonthlyPayment() + " Date: " + goal.getPeriod() + " Sum: " + goal.getSum());
            }


    }

    public void toGoals(View v) {
        Intent intent = new Intent(this, ActivityItemGoal.class);
        startActivity(intent);
        Log.d("Activity", "Button clicked!");
    }
    public void toMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}