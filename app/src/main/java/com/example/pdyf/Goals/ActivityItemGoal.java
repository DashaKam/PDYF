package com.example.pdyf.Goals;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.DateBase.DataBaseHandlerGoal;
import com.example.pdyf.MainActivity;
import com.example.pdyf.R;
import com.example.pdyf.TransactionManager.Transactions.ActivityAddTransaction;

import java.util.List;

public class ActivityItemGoal extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GoalAdapter adapter;
    private DataBaseHandlerGoal databaseHandlerGoal;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_goal);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        databaseHandlerGoal = new DataBaseHandlerGoal(this);
        loadGoals();
    }

    private void loadGoals() {
        List<Goal> goalList = databaseHandlerGoal.getAllGoals();
        Log.d("ActivityItemGoal", "Goals size: " + (goalList != null ? goalList.size() : 0));

        if (goalList != null && !goalList.isEmpty()) {
            adapter = new GoalAdapter(goalList);
            recyclerView.setAdapter(adapter);
            Log.d("ActivityItemGoal", "Adapter set successfully.");
        } else {
            Log.e("ActivityItemGoal", "No goals found or goal list is null");
        }
    }

    public void toAddGoal(View v){
        Intent intent = new Intent(this, ActivityGoal.class);
        startActivity(intent);
    }
    public void toMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


