package com.example.mohamed.legapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 03/07/2015.
 */
public class LieuDAO {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_MAJOR, MySQLiteHelper.COLUMN_MINOR, MySQLiteHelper.COLUMN_TYPE };

    public LieuDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Lieu createLieu(String name, String major, String minor, String type) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_MAJOR, major);
        values.put(MySQLiteHelper.COLUMN_MINOR, minor);
        values.put(MySQLiteHelper.COLUMN_TYPE, type);
        long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Lieu newLieu = cursorToLieu(cursor);
        cursor.close();
        return newLieu;
    }

    public void deleteLieu(Lieu lieu) {
        long id = lieu.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Lieu> getAllLieu() {
        List<Lieu> lieux = new ArrayList<Lieu>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Lieu lieu = cursorToLieu(cursor);
            lieux.add(lieu);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return lieux;
    }

    private Lieu cursorToLieu(Cursor cursor) {
        Lieu lieu = new Lieu();
        lieu.setId(cursor.getLong(0));
        lieu.setName(cursor.getString(1));
        lieu.setMajor(cursor.getString(2));
        lieu.setMinor(cursor.getString(3));
        lieu.setType(cursor.getString(4));
        return lieu;
    }

}
