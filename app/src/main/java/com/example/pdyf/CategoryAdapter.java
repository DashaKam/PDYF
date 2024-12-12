package com.example.pdyf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categories;
    private List<Integer> colors; // Список цветов

    public CategoryAdapter(List<Category> categories, List<Integer> colors) {
        this.categories = categories;
        this.colors = colors; // Сохраняем цвета
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        holder.categoryTotalSpent.setText(String.valueOf(category.getTotalSpent()));

        // Установка цвета для квадратика с использованием соответствующего цвета
        holder.colorView.setBackgroundColor(colors.get(position % colors.size()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View colorView;
        public TextView categoryName;
        public TextView categoryTotalSpent;

        public ViewHolder(View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.colorView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryTotalSpent = itemView.findViewById(R.id.categoryTotalSpent);
        }
    }
}