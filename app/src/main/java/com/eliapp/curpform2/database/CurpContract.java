package com.eliapp.curpform2.database;

import android.provider.BaseColumns;

import static java.security.AccessController.getContext;

public final class CurpContract {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CurpEntry.TABLE_NAME + " (" +
            CurpEntry._ID + " INTEGER PRIMARY KEY, " +
            CurpEntry.COLUMN_NAME_NAME + " TEXT, " +
            CurpEntry.COLUMN_NAME_FATHER_LASTNAME + " TEXT, " +
            CurpEntry.COLUMN_NAME_MOTHER_LASTNAME + " TEXT, " +
            CurpEntry.COLUMN_NAME_GENDER + " TEXT, " +
            CurpEntry.COLUMN_NAME_STATE + " TEXT, " +
            CurpEntry.COLUMN_NAME_BDAY_YEAR + " INTEGER, " +
            CurpEntry.COLUMN_NAME_BDAY_MONTH + " INTEGER, " +
            CurpEntry.COLUMN_NAME_BDAY_DAY + " INTEGER) ";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CurpEntry.TABLE_NAME;

    private CurpContract(){

    }

    public static class CurpEntry implements BaseColumns {
        public static final String TABLE_NAME = "curps";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_FATHER_LASTNAME = "father_lastname";
        public static final String COLUMN_NAME_MOTHER_LASTNAME = "mother_lastname";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_BDAY_YEAR = "birthday_year";
        public static final String COLUMN_NAME_BDAY_MONTH = "birthday_month";
        public static final String COLUMN_NAME_BDAY_DAY = "birthday_day";
    }
}
