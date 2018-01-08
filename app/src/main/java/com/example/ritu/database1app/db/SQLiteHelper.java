package com.example.ritu.database1app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ritu.database1app.activity.ContactModel;
import com.example.ritu.database1app.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by ritu on 12/20/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_NAME = "PEOPLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PHONE = "PHONE";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "create table "
                + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PHONE + " TEXT );";
        Log.e("SQLiteHelper", " Q : " + s);

        db.execSQL(s);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    private SQLiteDatabase database;

    public void insertRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_EMAIL, contact.getEmail());
        contentValues.put(COLUMN_PHONE, contact.getPhone());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void updateRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_EMAIL, contact.getEmail());
        contentValues.put(COLUMN_PHONE, contact.getPhone());
        database.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{contact.getID()});
        database.close();
    }

    public void deleteRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public ArrayList<ContactModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
        ContactModel contactModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contactModel = new ContactModel();
                contactModel.setID(cursor.getString(0));
                contactModel.setName(cursor.getString(1));
                contactModel.setEmail(cursor.getString(2));
                contactModel.setPhone(cursor.getString(3));
                contacts.add(contactModel);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }


    public ContactModel getContactFromId(int id) {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        ContactModel contactModel = null;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contactModel = new ContactModel();
                contactModel.setID(cursor.getString(0));
                contactModel.setName(cursor.getString(1));
                contactModel.setEmail(cursor.getString(2));
                contactModel.setPhone(cursor.getString(3));
            }
        }
        cursor.close();
        database.close();
        return contactModel;
    }


}
