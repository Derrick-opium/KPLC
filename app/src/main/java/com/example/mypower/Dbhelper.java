package com.example.mypower;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {


    private static final String DBNAME="Plc";
    private static final String TABLE_USERS="users";
    private static final String COL_ID="id";
    private static final String COL_METERNUMBER="meter";
    private static final String COL_TOKENUNIT="unit";
    private static final String COL_DELIVERYMTETER="deliver";

    public Dbhelper(@Nullable Context context) {

        super(context, DBNAME, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS +"(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_METERNUMBER + " INTEGER UNIQUE, "+
                COL_TOKENUNIT + " INTEGER,"+
                COL_DELIVERYMTETER + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        onCreate(db);

    }

    public boolean insertdata(String meter ,String unit){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_METERNUMBER,meter);
        values.put(COL_TOKENUNIT,unit);

        long result=db.insert(TABLE_USERS,null,values);
        return result !=-1;
    }
    public boolean updateDeliver(String meter, String delivery){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DELIVERYMTETER, delivery);

        int rows = db.update(TABLE_USERS, values, COL_METERNUMBER + " = ?", new String[]{meter});
        return rows > 0;
    }

    public boolean checkuser(String meter){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE "+COL_METERNUMBER + " = ?",
                new String[]{meter});
        boolean exists=cursor.getCount()>0;
        cursor.close();
        return exists;
    }


    public boolean checkUserPass(String meter){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_USERS+
                        " WHERE " + COL_METERNUMBER+" = ?",
                new String[]{meter});
        boolean valid=cursor.getCount() >0;
        cursor.close();
        return valid;
    }
    public Cursor getAllusrers(){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " +TABLE_USERS,null);
    }



    public int getBalance(String meter){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_TOKENUNIT + " FROM " + TABLE_USERS + " WHERE " + COL_METERNUMBER + "=?",
                new String[]{meter});

        int balance = 0;

        if(cursor.moveToFirst()){
            balance = Integer.parseInt(cursor.getString(0));
        }

        cursor.close();
        return balance;
    }
    public boolean updateMeterBalance(String token, String meter) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_USERS +
                        " SET " + COL_TOKENUNIT + " = " + COL_TOKENUNIT + " + ?" +
                        " WHERE " + COL_METERNUMBER + " = ?",
                new Object[]{token, meter});

        return true;
    }
}
