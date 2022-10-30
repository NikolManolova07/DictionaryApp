package com.nikolmanolova.dictionary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseContext extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DictionaryDB";

    private static final String CREATE_HISTORY_TABLE =
            "CREATE TABLE " + HistoryEntity.TABLE_NAME +
            " (" + HistoryEntity.ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + HistoryEntity.WORD_COLUMN + " VARCHAR(100));";

    private static final String DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS " + HistoryEntity.TABLE_NAME;

    public DatabaseContext(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_HISTORY_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_HISTORY_TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
