package com.example.pdyf;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
     private List<Category> categories;
        private List<Integer> colors;

        public CategoryAdapter(List<Category> categories, List<Integer> colors) {
            this.categories = categories;
            this.colors = colors;
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

        // Проверяем, что индекс не превышает размер списка colors
        if (position < colors.size()) {
            holder.colorView.setBackgroundColor(colors.get(position));
        } else {
            // Может быть, назначить цвет по умолчанию или скрыть цветовое представление
            holder.colorView.setBackgroundColor(Color.TRANSPARENT); // или любой другой цвет по умолчанию
        }
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