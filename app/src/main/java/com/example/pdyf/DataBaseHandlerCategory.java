package com.example.pdyf;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandlerCategory extends SQLiteOpenHelper {
    public DataBaseHandlerCategory(Context context) {
        super(context, Util.DATABASE_CATEGORY, null, Util.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_CATEGORY_NAME + " TEXT UNIQUE, " + Util.KEY_CATEGORY_SPEND + " REAL)";
        db.execSQL(CREATE_CATEGORY_TABLE);
        populateInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME_CATEGORY);
        onCreate(db);
    }

    public void addCategory(Category category) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            // Проверка на существование категории с таким именем
            String selectQuery = "SELECT * FROM " + Util.TABLE_NAME_CATEGORY + " WHERE " + Util.KEY_CATEGORY_NAME + "=?";
            try (Cursor cursor = db.rawQuery(selectQuery, new String[]{category.getName()})) {
                if (cursor.moveToFirst()) {
                    // Если категория уже существует, обновляем сумму
                    @SuppressLint("Range") double currentSpent = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Util.KEY_CATEGORY_SPEND)));
                    double newSpent = currentSpent + category.getTotalSpent(); // Обновляем сумму
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Util.KEY_CATEGORY_SPEND, newSpent);
                    db.update(Util.TABLE_NAME_CATEGORY, contentValues, Util.KEY_CATEGORY_NAME + "=?", new String[]{category.getName()});
                } else {
                    // Если категории не существует, добавляем новую
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Util.KEY_CATEGORY_NAME, category.getName());
                    contentValues.put(Util.KEY_CATEGORY_SPEND, category.getTotalSpent());
                    db.insert(Util.TABLE_NAME_CATEGORY, null, contentValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Обработка исключений
        }
    }


    public Category getCategory(int id) {
        Category category = null;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(Util.TABLE_NAME_CATEGORY,
                     new String[]{Util.KEY_ID, Util.KEY_CATEGORY_NAME, Util.KEY_CATEGORY_SPEND},
                     Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                category = new Category(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Double.parseDouble(cursor.getString(2))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categoriesList = new ArrayList<>();
        String selectAllCategories = "SELECT * FROM " + Util.TABLE_NAME_CATEGORY + " ORDER BY " + Util.KEY_CATEGORY_SPEND + " DESC";
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectAllCategories, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setId(Integer.parseInt(cursor.getString(0)));
                    category.setName(cursor.getString(1));
                    category.setTotalSpent(Double.parseDouble(cursor.getString(2)));
                    categoriesList.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoriesList;
    }
    public int getCategoryIdByName(String categoryName) {
        int categoryId = -1; // По умолчанию, если категория не найдена
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL-запрос для получения ID по имени категории
        String query = "SELECT " + Util.KEY_ID + " FROM " + Util.TABLE_NAME_CATEGORY + " WHERE " + Util.KEY_CATEGORY_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{categoryName});

        // Проверяем, есть ли результаты
        if (cursor != null && cursor.moveToFirst()) {
            categoryId = cursor.getInt(0); // Получаем ID
        }

        // Закрываем курсор и базу данных
        if (cursor != null) {
            cursor.close();
        }

        return categoryId; // Возвращаем ID или -1, если не найдено
    }
    public String getCategoryNameById(int categoryId) {
        // Здесь добавьте код для выполнения SQL-запроса, который вернет имя категории по ID
        // Пример:
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("categories", new String[]{"name"}, "id=?", new String[]{String.valueOf(categoryId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String categoryName = cursor.getString(cursor.getColumnIndex(Util.KEY_CATEGORY_NAME));
            cursor.close();
            return categoryName;
        }

        return null; // Если категория не найдена
    }
    public void populateInitialData(SQLiteDatabase db) {
        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util.TABLE_NAME_CATEGORY, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                if (count == 0) {
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Продукты', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Подарки', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Транспорт', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Рестораны', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Одежда', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Здоровье', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Красота', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Животные', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Развлечения', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Связь', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Творчество', 0.0);");
                    db.execSQL("INSERT INTO " + Util.TABLE_NAME_CATEGORY + " (" + Util.KEY_CATEGORY_NAME + ", " + Util.KEY_CATEGORY_SPEND + ") VALUES ('Прочее', 0.0);");




                    Log.d("Database", "Заполнение данными при первом запуске");

                }
            }
        } catch (Exception e) {
            Log.e("Database", "Error in populateInitialData: " + e.getMessage());
        }
    }

}