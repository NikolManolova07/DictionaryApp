package com.nikolmanolova.dictionary.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HistoryRepository {
    private SQLiteDatabase db;

    public HistoryRepository(Context context) {
        DatabaseContext dbHelper = new DatabaseContext(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HistoryEntity entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryEntity.WORD_COLUMN, entity.getWord());
        return db.insert(HistoryEntity.TABLE_NAME, null, contentValues);
    }

    public void delete(int id) {
        db.delete(HistoryEntity.TABLE_NAME, HistoryEntity.ID_COLUMN + " = " + id, null);
    }

    public HistoryEntity getLast() {
        Cursor cursor = this.getSelectQueryCursor();

        if (cursor.moveToFirst()) {
            return this.parse(cursor);
        }
        return null;
    }

    public ArrayList<HistoryEntity> getAll() {
        ArrayList<HistoryEntity> data = new ArrayList<HistoryEntity>();
        Cursor cursor = this.getSelectQueryCursor();

        while (cursor.moveToNext()) {
            data.add(this.parse(cursor));
        }
        return data;
    }

    private Cursor getSelectQueryCursor() {
        String[] columns =  { HistoryEntity.ID_COLUMN, HistoryEntity.WORD_COLUMN };
        Cursor cursor = db.query(HistoryEntity.TABLE_NAME, columns, null, null,null, null, "ID DESC");
        return cursor;
    }

    private HistoryEntity parse(Cursor cursor) {
        HistoryEntity historyEntity = new HistoryEntity();
        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(HistoryEntity.ID_COLUMN));
        historyEntity.setId(id);
        @SuppressLint("Range") String word = cursor.getString(cursor.getColumnIndex(HistoryEntity.WORD_COLUMN));
        historyEntity.setWord(word);
        return historyEntity;
    }
}
