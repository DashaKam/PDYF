package com.example.pdyf;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;

public class Util {
    public   static final int VERSION = 12;
    public  static final String DATABASE_NAME = "transactionsBD";
    public  static final String DATABASE_CATEGORY = "categoriesBD";
    public  static final String DATABASE_GOAL = "goalsBD";
    public  static final String TABLE_NAME = "transactions";
    public  static final String TABLE_NAME_CATEGORY = "categories";
    public  static final String TABLE_NAME_GOAL = "goals";

    public  static final String KEY_ID = "id";
    public  static final String KEY_TYPE = "type";
    public  static final String KEY_AMOUNT = "amount";
    public  static final String KEY_DATE = "date";
    public  static final String KEY_CATEGORY = "category";
    public  static final String KEY_CATEGORY_NAME = "name";
    public  static final String KEY_CATEGORY_SPEND = "amount";
    public  static final String KEY_PERIOD = "date";
    public  static final String KEY_PAYMENT = "payment";
    public  static final String KEY_NAME = "name";
    public  static final String KEY_SUM = "sum";


    public static final int[] UNIQUE_COLORS = new int[] {
            Color.RED,         // 1. Красный
            Color.GREEN,       // 2. Зеленый
            Color.BLUE,        // 3. Синий
            Color.YELLOW,      // 4. Желтый
            Color.CYAN,        // 5. Циановый
            Color.MAGENTA,     // 6. Пурпурный
            Color.DKGRAY,      // 7. Темно-серый
            Color.LTGRAY,      // 8. Светло-серый
            Color.parseColor("#FFA500"), // 9. Оранжевый
            Color.parseColor("#800080"), // 10. Фиолетовый
            Color.parseColor("#FFC0CB"), // 11. Розовый
            Color.parseColor("#8B4513"),  // 12. Коричневый
            Color.parseColor("#8B4513")  // 13. Коричневый

    };
    static Log Log;

}
