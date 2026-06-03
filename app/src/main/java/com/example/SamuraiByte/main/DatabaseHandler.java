package com.example.SamuraiByte.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.SamuraiByte.utils.NameAndScore;

import java.util.Arrays;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "SamuraiByteDatabase";
    private static final int DB_VERSION = 2;

    // USER'S TABLE
    private static final String USERS_TABLE_NAME = "users";
    private static final String USERS_NAME_COL = "Name";
    private static final String USERS_EMAIL_COL = "Email";
    private static final String USERS_PASSWORD_COL = "Password";
    private static final String USERS_SCORE_COL = "Score";

    // LEADERBOARD TABLE
//    private static final String SCOREBOARD_TABLE_NAME = "leaderboard";
//    private static final String SCOREBOARD_NAME_COL = "Name";
//    private static final String SCOREBOARD_SCORE_COL = "Score";
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usersQuery = "CREATE TABLE " + USERS_TABLE_NAME + " ("
                + USERS_NAME_COL + " TEXT,"
                + USERS_EMAIL_COL + " TEXT,"
                + USERS_PASSWORD_COL + " TEXT,"
                + USERS_SCORE_COL + " REAL)";

        db.execSQL(usersQuery);

//        String leaderboardQuery = "CREATE TABLE " + SCOREBOARD_TABLE_NAME + " ("
//                + SCOREBOARD_NAME_COL + " TEXT,"
//                + SCOREBOARD_SCORE_COL + " REAL)";
//
//        db.execSQL(leaderboardQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i < 2){
            sqLiteDatabase.execSQL("ALTER TABLE name_and_passwords RENAME TO " + USERS_TABLE_NAME);
            sqLiteDatabase.execSQL("ALTER TABLE " + USERS_TABLE_NAME + " ADD COLUMN " + USERS_SCORE_COL + " REAL DEFAULT -1");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS leaderboard");
        }
    }
    // USERS TABLE FUNCTIONS
    public void addNewUsersCourse(String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERS_NAME_COL, name);
        values.put(USERS_EMAIL_COL, email);
        values.put(USERS_PASSWORD_COL, password);
        values.put(USERS_SCORE_COL, -1.0);

        db.insert(USERS_TABLE_NAME, null, values);
        db.close();
    }
    private int executeQuery(String query, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }
    public boolean loginCheckInDB(String name, String password){
        String query = "SELECT " + USERS_NAME_COL + ", "+ USERS_PASSWORD_COL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_NAME_COL + "=? AND " + USERS_PASSWORD_COL + "=?";

        return this.executeQuery(query, new String[]{name, password}) >= 1;
    }
    public boolean registerCheckInDB(String name, String email){
        String query = "SELECT " + USERS_NAME_COL + ", " + USERS_EMAIL_COL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_NAME_COL + "=? OR " + USERS_EMAIL_COL + "=?";

        return executeQuery(query, new String[]{name, email}) >= 1;
    }

    // LEADERBOARD FUNCTIONS
//    public void addNewLeaderboardCourse(String name, double score){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(SCOREBOARD_NAME_COL, name);
//        values.put(SCOREBOARD_SCORE_COL, score);
//
//        db.insert(SCOREBOARD_TABLE_NAME, null, values);
//        db.close();
//    }
    public void updateLeaderboardCourse(String name, double score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERS_SCORE_COL, score);

        db.update(USERS_TABLE_NAME, values, USERS_NAME_COL+"=?", new String[]{name});
        db.close();
    }
//    public boolean checkInLeaderboardDB(String name){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT " + USERS_NAME_COL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_NAME_COL + "=?";
//        Cursor cursor = db.rawQuery(query, new String[]{name});
//
//        int count = cursor.getCount();
//        cursor.close();
//
//        return count >= 1;
//    }
    public double readLeaderboardScore(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + USERS_SCORE_COL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_NAME_COL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        double score = -1;
        if (cursor.getCount() > 0 && cursor.moveToFirst()){
            score = cursor.getDouble(0);
        }
        cursor.close();
        db.close();

        return score;
    }
    public NameAndScore[] getLeaderboardArray(){
        SQLiteDatabase db = this.getReadableDatabase();
//        String queryNames = "SELECT " + USERS_NAME_COL + " FROM " + USERS_TABLE_NAME;
//        String queryScores = "SELECT " + USERS_SCORE_COL + " FROM " + USERS_TABLE_NAME;
        String query = "SELECT " + USERS_NAME_COL + ", " + USERS_SCORE_COL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_SCORE_COL + " != -1";
//        Cursor cursorNames = db.rawQuery(queryNames, null);
//        Cursor cursorScores = db.rawQuery(queryScores, null);
        Cursor cursor = db.rawQuery(query, null);

        NameAndScore[] result = new NameAndScore[cursor.getCount()];
        int len = cursor.getCount();
        if (len == 0)
            return null;
        int i = 0;
        if (cursor.moveToFirst()){
            do {
                result[i] = new NameAndScore(cursor.getString(0), cursor.getDouble(1));
                i++;
            } while (cursor.moveToNext() && i < len);
        }
        cursor.close();
        db.close();

        Arrays.sort(result);
        return result;
    }
}
