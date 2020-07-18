package com.webbrowser.wruapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wruapp1";
    private static final String TABLE_FOLDERINFO = "contacts";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    //private static final String KEY_LIMIT = "lim";

    public DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s  = "create table "+TABLE_FOLDERINFO+"(name text,phone text);";
        db.execSQL( s );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "drop table if exists "+TABLE_FOLDERINFO );
        onCreate( db );
    }

    void addContact(Contact record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, record.getName()); // URL OF FOLDER
        values.put(KEY_PHONE, record.getPhone()); // SIZE ON DISK
        //values.put(KEY_LIMIT, record.getLimit()); // LIMIT TO NOTIFY

        // Inserting Row
        db.insert(TABLE_FOLDERINFO, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    void deleteAll()
    {
        String s = "DELETE FROM "+TABLE_FOLDERINFO;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( s );
        db.close();
    }


/*    // Getting contacts Count
    public int getRecordCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOLDERINFO+" WHERE URL = 'Ashok '";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }*/

    // Getting contacts Count
    public int getRecordCount(Contact record) {
        String countQuery = "SELECT  * FROM " + TABLE_FOLDERINFO+" WHERE phone = '"+record.getPhone()+"'";
        System.out.println( countQuery );
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOLDERINFO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setName((cursor.getString(0)));
                contact.setPhone((cursor.getString(1)));
                //contact.setLimit(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

/*    // code to update the single contact
    public int updateContact(FolderRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SIZE, record.getSize()); // SIZE ON DISK
        values.put(KEY_LIMIT, record.getLimit()); // LIMIT TO NOTIFY

        // updating row
        return db.update(TABLE_FOLDERINFO, values, KEY_URL + " = ?",
                new String[] { String.valueOf(record.getUrl()) });
    }


    public void checkThenAdd(FolderRecord record)
    {
        if(getRecordCount( record )!=0)
        {
            updateContact( record );
        }
        else
        {
            addContact( record );
        }
    }*/


}