package com.example.SamuraiByte.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "NameAndPassDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "name_and_passwords";
    private static final String NAME_COL = "Name";
    private static final String EMAIL_COL = "Email";
    private static final String PASSWORD_COL = "Password";
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addNewCourse(String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(PASSWORD_COL, password);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    private int executeQuery(String query, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public boolean loginCheckInDB(String name, String password){
//        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + NAME_COL + ", "+ PASSWORD_COL + " FROM " + TABLE_NAME + " WHERE " + NAME_COL + "=? AND " + PASSWORD_COL + "=?";
//        Cursor cursor = db.rawQuery(query, new String[]{name, password});

//        int count = cursor.getCount();
//        cursor.close();

        return this.executeQuery(query, new String[]{name, password}) >= 1;
    }
    public boolean registerCheckInDB(String name, String email){
        String query = "SELECT " + NAME_COL + ", " + EMAIL_COL + " FROM " + TABLE_NAME + " WHERE " + NAME_COL + "=? OR " + EMAIL_COL + "=?";

        return executeQuery(query, new String[]{name, email}) >= 1;
    }
}
