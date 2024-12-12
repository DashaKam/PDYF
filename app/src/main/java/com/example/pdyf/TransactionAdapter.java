package com.example.pdyf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdyf.R;
import com.example.pdyf.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;


    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_transaction, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        DataBaseHandlerCategory databaseHandler = new DataBaseHandlerCategory(holder.itemView.getContext());

        // Получаем имя категории по ID
        String categoryName = databaseHandler.getCategoryNameById(transaction.getCategoryId());

        holder.textViewCategory.setText(categoryName != null ? categoryName : "Unnamed Category");
        holder.textViewAmount.setText(String.valueOf(transaction.getAmount()));
        holder.textViewDate.setText(transaction.getDate());
        holder.textViewType.setText(transaction.getType());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCategory, textViewAmount, textViewDate, textViewType;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewType = itemView.findViewById(R.id.textViewType);
        }
    }
}