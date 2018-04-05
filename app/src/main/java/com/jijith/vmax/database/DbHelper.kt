package com.jijith.vmax.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jijith.vmax.utils.Constants

/**
 * Created by jijith on 1/23/18.
 */
class DbHelper : SQLiteOpenHelper {

    companion object {
        val DATABASE_NAME = "OnlineProfesor.db"
        val TABLE_USERS = "favorite"
        val TABLE_STUDENTS = "students"
    }

    constructor(context: Context) : super(context, DATABASE_NAME, null, 1)

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("create table favorite " + "(id text primary key, completed text, count text, question text)")
        sqLiteDatabase.execSQL("create table students " + "(id text primary key, currentstudent text)")
        //        sqLiteDatabase.execSQL("create table interview " + "(id text primary key, interviews text, interview_time INTEGER)");
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users")
        onCreate(sqLiteDatabase)
    }


    public fun insertUserId(id : String) : Boolean {
        val db = this.getWritableDatabase();

        val contentValues = ContentValues();
//        contentValues.put(Constants.ID, id);
//        contentValues.put(Constants.COMPLETE, "0");
//        contentValues.put(Constants.COUNT, "0");
//        contentValues.put(Constants.QUESTION, "0");

        db.insert(TABLE_USERS, null, contentValues);

        return true;
    }
//
//    public boolean insertCurrentStudent(String id, String currentStudent) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Constants.ID, id);
//        contentValues.put(Constants.CURRENT_STUDENT, currentStudent);
//
//        db.insert(TABLE_STUDENTS, null, contentValues);
//
//        return true;
//    }
//
//
//    public ArrayList<String> getAllId() {
//        ArrayList<String> array_list = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from users", null);
//        res.moveToFirst();
//
//        while (res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex(Constants.ID)));
//            Log.e("IDS", res.getString(res.getColumnIndex(Constants.ID)));
//            res.moveToNext();
//        }
//
//        return array_list;
//    }
//
//    //
////
////
////    public void deleteAllInterviews(){
////        SQLiteDatabase db = this.getReadableDatabase();
////        int i = db.delete(TABLE_INTERVIEW, "1", null);
////        Log.e("DATABASE", i +" deleted");
////    }
////
//    public void deleteAllUsers() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        int i = db.delete(TABLE_USERS, "1", null);
//        Log.e("DATABASE", i + " deleted");
//    }
//
////    public void checkId() {
////
////    }
//
//    public void updateUser(String id, String com, String count, String ques) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Constants.COMPLETE, com);
//        contentValues.put(Constants.COUNT, count);
//        contentValues.put(Constants.QUESTION, ques);
//
//        db.updateAdapter(TABLE_USERS, contentValues, "id= ?", new String[]{id});
//    }
//
//    public void updateUser(String id, String com, String count) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Constants.COMPLETE, com);
//        contentValues.put(Constants.COUNT, count);
//
//        db.updateAdapter(TABLE_USERS, contentValues, "id= ?", new String[]{id});
//    }
//
//    public void updateCurrentUser(String id, String current) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Constants.ID, id);
//        contentValues.put(Constants.CURRENT_STUDENT, current);
//
//        db.updateAdapter(TABLE_STUDENTS, contentValues, "id= ?", new String[]{id});
//    }
//
//    public String getCompletePool(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res = db.rawQuery("select * from users where id='" + id + "'", null);
//        Cursor res = db.query(TABLE_USERS, new String[] {Constants.COMPLETE } , "id=?", new String[] { id }, null, null, null);
////        res.moveToFirst();
//
////        while (res.isAfterLast() == false) {
////           return res.getString(res.getColumnIndex(Constants.COMPLETE));
////        }
//
//        if (res != null)
//            res.moveToFirst();
//
//        return res.getString(0);
//    }
//
//    public String getCountPool(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res = db.rawQuery("select * from users where id='" + id + "'", null);
//        Cursor res = db.query(TABLE_USERS, new String[] {Constants.COUNT } , "id=?", new String[] { id }, null, null, null);
////        res.moveToFirst();
//
//        if (res != null) {
//            res.moveToFirst();
//            return res.getString(0);
//        } else
//            return "0";
//    }
//
//    public String getQuestionPool(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res = db.rawQuery("select * from users where id='" + id + "'", null);
//        Cursor res = db.query(TABLE_USERS, new String[] {Constants.QUESTION } , "id=?", new String[] { id }, null, null, null);
////        res.moveToFirst();
//
//        if (res != null) {
//            res.moveToFirst();
//            return res.getString(0);
//        } else {
//            return "0";
//        }
//    }
//
//    public String getId(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res = db.rawQuery("select * from users where id='" + id + "'", null);
//        Cursor res = db.query(TABLE_USERS, new String[] {Constants.ID } , "id=?", new String[] { id }, null, null, null);
////        res.moveToFirst();
//
//        if (res != null) {
//            if (res.getCount() > 0) {
//                res.moveToFirst();
//                return res.getString(0);
//            }
//            else
//                return "0";
//        } else {
//            return "0";
//        }
//    }
//
//    public String getCurrentStudent(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res = db.rawQuery("select * from users where id='" + id + "'", null);
//        Cursor res = db.query(TABLE_STUDENTS, new String[] {Constants.CURRENT_STUDENT } , "id=?", new String[] { id }, null, null, null);
////        res.moveToFirst();
//
//        if (res != null) {
//            if (res.getCount() > 0) {
//                res.moveToFirst();
//                return res.getString(0);
//            }
//            else
//                return "0";
//        } else {
//            return "0";
//        }
//    }
}