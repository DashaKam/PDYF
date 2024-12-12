package com.example.pdyf.Goals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.R;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {
    private List<Goal> goals;


    public GoalAdapter(List<Goal> goals) {
        this.goals = goals;
    }

    @NonNull
    @Override
    public GoalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_goal_list, parent, false);

        return new GoalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalAdapter.ViewHolder holder, int position) {
        Goal goal = goals.get(position);

        holder.textViewGoal.setText(String.valueOf(goal.getName()));
        holder.textViewPeriod.setText(goal.getPeriod());
        holder.textViewSum.setText(String.valueOf(goal.getSum()));
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewGoal, textViewPeriod, textViewSum;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewGoal = itemView.findViewById(R.id.textViewGoal);
            textViewPeriod = itemView.findViewById(R.id.textViewPeriod);
            textViewSum = itemView.findViewById(R.id.textViewSum);
        }
    }
}