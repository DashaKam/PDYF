package com.example.pdyf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandlerTransaction extends SQLiteOpenHelper {
    public DataBaseHandlerTransaction(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_CATEGORY + " TEXT, " + Util.KEY_AMOUNT + " TEXT, " + Util.KEY_DATE + " TEXT, " + Util.KEY_TYPE + " TEXT)";
        //TODO: МБ ГДЕ ДАТА НАДО ТИП ПОМЕНЯТЬ
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);

    }

    public void addTransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_CATEGORY, transaction.getCategoryId());
        contentValues.put(Util.KEY_AMOUNT, transaction.getAmount());
        contentValues.put(Util.KEY_DATE, transaction.getDate());
        contentValues.put(Util.KEY_TYPE, transaction.getType());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Transaction getTransaction(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_CATEGORY, Util.KEY_AMOUNT, Util.KEY_DATE, Util.KEY_TYPE},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        assert cursor != null;
        return new Transaction(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                Double.parseDouble(cursor.getString(2)), cursor.getString(3), cursor.getString(4));
    }
    public List<Transaction> getAllTransactions(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionsList = new ArrayList<>();
        String selectAllTransaction = "Select * from " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllTransaction, null);
        if(cursor.moveToFirst()){
            do{
                Transaction transaction = new Transaction();
                transaction.setId(Integer.parseInt(cursor.getString(0)));
                transaction.setCategoryId(Integer.parseInt(cursor.getString(1)));
                transaction.setAmount(Double.parseDouble(cursor.getString(2)));
                transaction.setDate(cursor.getString(3));
                transaction.setType(cursor.getString(4));

                transactionsList.add(transaction);
            }while (cursor.moveToNext()); // Пока есть следующий элеент
        }
        return transactionsList;
    }
    public double calculateIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        double income = 0.0;
        double expenses = 0.0;

        // Подсчет суммы для "get" (доход)
        String incomeQuery = "SELECT SUM(" + Util.KEY_AMOUNT + ") FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_TYPE + " = ?";
        Cursor incomeCursor = db.rawQuery(incomeQuery, new String[]{"get"});
        if (incomeCursor.moveToFirst()) {
            income = incomeCursor.getDouble(0);
        }
        incomeCursor.close();

        // Подсчет суммы для "send" (расход)
        String expensesQuery = "SELECT SUM(" + Util.KEY_AMOUNT + ") FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_TYPE + " = ?";
        Cursor expensesCursor = db.rawQuery(expensesQuery, new String[]{"send"});
        if (expensesCursor.moveToFirst()) {
            expenses = expensesCursor.getDouble(0);
        }
        expensesCursor.close();

        // Возвращаем разницу между доходами и расходами
        return income - expenses;
    }
 }
