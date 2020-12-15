package com.example.mytodolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ToDoTaskDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ToDoList";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "ToDo";
    public static final String ID_COL = "_id";
    public static final String TEXT_COL = "text";
    public static final String DATE_COL = "date";
    public static final String COMPLETE_COL = "complete";


    public ToDoTaskDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TEXT_COL + " TEXT," +
                DATE_COL + " DATE," +
                COMPLETE_COL + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void setComplete(int id, boolean complete) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPLETE_COL, complete);
        getWritableDatabase().update(TABLE_NAME, contentValues, "_id=?", new String[]{Integer.toString(id)});
    }

    public int insertTask(String text, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEXT_COL, text);
        contentValues.put(DATE_COL, date);
        contentValues.put(COMPLETE_COL, false);
        return(int) getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<ToDoTask> getAllTasks() {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{ID_COL, TEXT_COL, DATE_COL, COMPLETE_COL},
                null,
                null,
                null,
                null,
                null);
        ArrayList<ToDoTask> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ToDoTask task = new ToDoTask(
                        cursor.getInt(cursor.getColumnIndex(ID_COL)),
                        cursor.getString(cursor.getColumnIndex(TEXT_COL)),
                        cursor.getString(cursor.getColumnIndex(DATE_COL)),
                        cursor.getInt(cursor.getColumnIndex(COMPLETE_COL)) == 1
                );
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public boolean deleteTask(int id) {
        return getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{Integer.toString(id)}) > 0;
    }

}
