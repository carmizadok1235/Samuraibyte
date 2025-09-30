package com.example.SamuraiByte.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.SamuraiByte.utils.NameAndScore;

import java.util.ArrayList;
import java.util.Arrays;

public class LeaderboardHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "LeaderboardDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "leaderboard";
    private static final String NAME_COL = "Name";
    private static final String SCORE_COL = "Score";
    public LeaderboardHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + NAME_COL + " TEXT,"
                + SCORE_COL + " REAL)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addNewCourse(String name, double score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(SCORE_COL, score);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void updateCourse(String name, double score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SCORE_COL, score);

        db.update(TABLE_NAME, values, "name=?", new String[]{name});
    }
    public boolean checkInDB(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + NAME_COL + " FROM " + TABLE_NAME + " WHERE " + NAME_COL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        int count = cursor.getCount();
        cursor.close();

        return count >= 1;
    }
    public double readScore(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + SCORE_COL + " FROM " + TABLE_NAME + " WHERE " + NAME_COL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        double score = -1;
        if (cursor.getCount() > 0 && cursor.moveToFirst()){
            score = cursor.getDouble(0);
        }
        cursor.close();

        return score;
    }
    public NameAndScore[] getLeaderboardArray(){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryNames = "SELECT " + NAME_COL + " FROM " + TABLE_NAME;
        String queryScores = "SELECT " + SCORE_COL + " FROM " + TABLE_NAME;
        Cursor cursorNames = db.rawQuery(queryNames, null);
        Cursor cursorScores = db.rawQuery(queryScores, null);

        NameAndScore[] result = new NameAndScore[cursorNames.getCount()];
        cursorNames.moveToLast();
        cursorScores.moveToLast();

//        cursorNames.moveToPrevious();


        int i = cursorNames.getCount()-1;
        if (i == -1)
            return null;
        do {
            String name = cursorNames.getString(0);
            double score = cursorScores.getDouble(0);
            result[i] = new NameAndScore(name, score);
            i--;
        }while (cursorNames.moveToPrevious() && cursorScores.moveToPrevious());

        Arrays.sort(result);
        return result;
    }
}
