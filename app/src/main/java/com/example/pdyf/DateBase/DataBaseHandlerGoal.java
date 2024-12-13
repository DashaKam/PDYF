package com.example.pdyf.DateBase;

 import android.content.ContentValues;
import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 import android.util.Log;
 import android.widget.Toast;

 import com.example.pdyf.Goals.Goal;
 import com.example.pdyf.Util;

 import java.util.ArrayList;
 import java.util.List;

public class DataBaseHandlerGoal extends SQLiteOpenHelper {

    public DataBaseHandlerGoal(Context context) {
        super(context, Util.DATABASE_GOAL, null, Util.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + Util.TABLE_NAME_GOAL + " (" + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_NAME + " TEXT, " + Util.KEY_PAYMENT + " REAL, " + Util.KEY_PERIOD + " TEXT, " + Util.KEY_SUM + " REAL)";
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME_GOAL);
        onCreate(db);
    }

    public int addGoal(Goal goal, Context context) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            // Проверяем, существует ли цель с таким именем
            if (isGoalExists(goal.getName(), db)) {
                Log.d("DataBaseHandlerGoal", "Цель с таким именем уже существует: " + goal.getName());
                Toast.makeText(context, "Цель с таким именем уже существует!", Toast.LENGTH_SHORT).show();
                return 1;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.KEY_NAME, goal.getName());
            contentValues.put(Util.KEY_PAYMENT, goal.getMonthlyPayment());
            contentValues.put(Util.KEY_PERIOD, goal.getPeriod());
            contentValues.put(Util.KEY_SUM, goal.getSum());
            db.insert(Util.TABLE_NAME_GOAL, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace(); // Обработка исключений
        }
        return 0;
    }

    private boolean isGoalExists(String goalName, SQLiteDatabase db) {
        String query = "SELECT * FROM " + Util.TABLE_NAME_GOAL + " WHERE " + Util.KEY_NAME + " = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{goalName})) {
            return cursor.getCount() > 0; // Если количество найденных записей больше 0, цель существует
        }
    }


    public Goal getGaol(int id) {
        Goal goal = null;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(Util.TABLE_NAME_CATEGORY,
                     new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PAYMENT, Util.KEY_PERIOD, Util.KEY_SUM},
                     Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                goal = new Goal(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Double.parseDouble(cursor.getString(2)),
                        cursor.getString(3),
                        Double.parseDouble(cursor.getString(4))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goal;
    }

    public List<Goal> getAllGoals() {
        List<Goal> goalsList = new ArrayList<>();
        String selectAllGoals = "SELECT * FROM " + Util.TABLE_NAME_GOAL;
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectAllGoals, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Goal goal = new Goal();
                    goal.setId(Integer.parseInt(cursor.getString(0)));
                    goal.setName(cursor.getString(1));
                    goal.setMonthlyPayment(Double.parseDouble(cursor.getString(2)));
                    goal.setPeriod(cursor.getString(3));
                    goal.setSum(Double.parseDouble(cursor.getString(4)));

                    goalsList.add(goal);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goalsList;
    }
}