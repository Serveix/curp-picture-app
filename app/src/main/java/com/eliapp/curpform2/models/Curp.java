package com.eliapp.curpform2.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import com.eliapp.curpform2.database.CurpDbHelper;
import static com.eliapp.curpform2.database.CurpContract.*;

import java.util.GregorianCalendar;
import java.util.ArrayList;

public class Curp  implements Parcelable {

    private long id;
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String gender;
    private int birthdayYear;
    private int birthdayMonth;
    private int birthdayDay;
    private String state;
    private String imgpath;

    public Curp() {
        this.id = 0;
        this.name = "";
        this.fatherLastName = "";
        this.motherLastName = "";
        this.gender = "";
        this.birthdayYear = 1900;
        this.birthdayMonth = 1;
        this.birthdayDay = 1;
        this.state = "";
        this.imgpath = "";
    }

    private Curp(Parcel in) {
        this.id = 0;
        this.name = in.readString();
        this.fatherLastName = in.readString();
        this.motherLastName = in.readString();
        this.gender = in.readString();
        this.birthdayYear = in.readInt();
        this.birthdayMonth = in.readInt();
        this.birthdayDay = in.readInt();
        this.state = in.readString();
        this.imgpath = in.readString();
    }

    public Curp(String name, String fatherLastName, String motherLastName, String gender, int day, int month, int year, String state) {
        this.name = name;
        this.fatherLastName = fatherLastName;
        this.motherLastName = motherLastName;
        this.gender = gender;
        this.birthdayYear = year;
        this.birthdayMonth = month;
        this.birthdayDay = day;
        this.state = state;
    }

    public Curp(String name, String fatherLastName, String motherLastName, String gender, int day, int month, int year, String state, long id) {
        this.id = id;
        this.name = name;
        this.fatherLastName = fatherLastName;
        this.motherLastName = motherLastName;
        this.gender = gender;
        this.birthdayYear = year;
        this.birthdayMonth = month;
        this.birthdayDay = day;
        this.state = state;
    }

    public Curp(String name, String fatherLastName, String motherLastName, String gender, int day, int month, int year, String state, String imgpath) {
        this.name = name;
        this.fatherLastName = fatherLastName;
        this.motherLastName = motherLastName;
        this.gender = gender;
        this.birthdayYear = year;
        this.birthdayMonth = month;
        this.birthdayDay = day;
        this.state = state;
        this.imgpath = imgpath;
    }

    public Curp(String name, String fatherLastName, String motherLastName, String gender, int day, int month, int year, String state, long id, String imgpath) {
        this.id = id;
        this.name = name;
        this.fatherLastName = fatherLastName;
        this.motherLastName = motherLastName;
        this.gender = gender;
        this.birthdayYear = year;
        this.birthdayMonth = month;
        this.birthdayDay = day;
        this.state = state;
        this.imgpath = imgpath;
    }

    public String getName() {
        return name;
    }

    public GregorianCalendar getBirthday() {
        return new GregorianCalendar(birthdayYear, birthdayMonth, birthdayDay);
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public String getGender() {
        return gender;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public String getState() {
        return state;
    }

    public String getImgpath() {
        return imgpath;
    }

    public int getBirthdayDay() {
        return birthdayDay;
    }

    public int getBirthdayMonth() {
        return birthdayMonth;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(int year, int month, int day) {
        this.birthdayDay = day;
        this.birthdayMonth = month;
        this.birthdayYear = year;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(fatherLastName);
        parcel.writeString(motherLastName);
        parcel.writeString(gender);
        parcel.writeInt(birthdayYear);
        parcel.writeInt(birthdayMonth);
        parcel.writeInt(birthdayDay);
        parcel.writeString(state);
        parcel.writeString(imgpath);
    }

    public static final Parcelable.Creator<Curp> CREATOR
            = new Parcelable.Creator<Curp>() {
        public Curp createFromParcel(Parcel in) {
            return new Curp(in);
        }

        @Override
        public Curp[] newArray(int i) {
            return new Curp[i];
        }
    };

    public String getCurp() {
        String c = "";
        c += (this.fatherLastName.toUpperCase().toCharArray())[0];
        c += (this.fatherLastName.toUpperCase().toCharArray())[1];
        c += (this.motherLastName.toUpperCase().toCharArray())[0];
        c += (this.name.toUpperCase().toCharArray())[0];
        c += String.format("%1$02d%2$02d%3$02d", this.birthdayYear, this.birthdayMonth, this.birthdayDay);

        return c;
    }

    public String getBirthdayString() {
        return String.format("%1$02d/%2$02d/%3$02d", this.birthdayDay, this.birthdayMonth, this.birthdayYear);
    }

    public void save(Context context) {
        SaveInDatabaseArgs saveInDatabaseArgs = new SaveInDatabaseArgs(context);
        new SaveInDatabase().execute(saveInDatabaseArgs);
    }

    public static ArrayList<Curp> getCurps(Context context) {
        DatabaseSelectArgs dsa = new DatabaseSelectArgs(context);
        ArrayList<Curp> curps = new getCurpsInDatabase().doInBackground(dsa);
        return curps;
    }

    public static ArrayList<Curp> getCurps(Context context,
                                           String selection,
                                           String[] selectionArgs) {
        DatabaseSelectArgs dsa = new DatabaseSelectArgs(context, selection, selectionArgs);
        ArrayList<Curp> curps = new getCurpsInDatabase().doInBackground(dsa);
        return curps;
    }

    public static ArrayList<Curp> getCurps(Context context,
                                           String selection,
                                           String[] selectionArgs,
                                           String sortOrder) {
        DatabaseSelectArgs dsa = new DatabaseSelectArgs(context, selection, selectionArgs, sortOrder);
        ArrayList<Curp> curps = new getCurpsInDatabase().doInBackground(dsa);
        return curps;
    }

    //ASYNC TASK CLASSES
    private class SaveInDatabaseArgs {
        private Context context;

        public SaveInDatabaseArgs(Context c) {
            this.context = c;
        }

        public Context getContext() {
            return context;
        }
    }

    private static class DatabaseSelectArgs {
        private Context context;
        private String selection;
        private String[] selectionArgs;
        private String sortOrder;
        public final String ASC = CurpEntry.COLUMN_NAME_NAME + "ASC";
        public final String DESC = CurpEntry.COLUMN_NAME_NAME + "DESC";

        public DatabaseSelectArgs(Context c) {
            this.context = c;
            this.selection = "";
            String[] a = {};
            this.selectionArgs = a;
            this.sortOrder = this.ASC;
        }

        public DatabaseSelectArgs(Context c, String selection, String[] selectionArgs) {
            this.context = c;
            this.selection = selection;
            String[] a = selectionArgs;
            this.selectionArgs = a;
            this.sortOrder = this.ASC;
        }

        public DatabaseSelectArgs(Context c, String selection, String[] selectionArgs, String sortOrder) {
            this.context = c;
            this.selection = selection;
            String[] a = selectionArgs;
            this.selectionArgs = a;
            this.sortOrder = sortOrder;
        }

        public Context getContext() {
            return context;
        }

        public String getSelection() {
            return selection;
        }

        public String[] getSelectionArgs() {
            return selectionArgs;
        }

        public String getSortOrder() {
            return sortOrder;
        }
    }

    private class SaveInDatabase extends AsyncTask<SaveInDatabaseArgs, Void, Void> {
        @Override
        protected Void doInBackground(SaveInDatabaseArgs... saveInDatabaseArgs) {
            Context context = saveInDatabaseArgs[0].getContext();
            CurpDbHelper curpDbHelper = new CurpDbHelper(context);
            SQLiteDatabase db = curpDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CurpEntry.COLUMN_NAME_NAME, name);
            values.put(CurpEntry.COLUMN_NAME_FATHER_LASTNAME, fatherLastName);
            values.put(CurpEntry.COLUMN_NAME_MOTHER_LASTNAME, motherLastName);
            values.put(CurpEntry.COLUMN_NAME_GENDER, gender);
            values.put(CurpEntry.COLUMN_NAME_STATE, state);
            values.put(CurpEntry.COLUMN_NAME_BDAY_YEAR, birthdayYear);
            values.put(CurpEntry.COLUMN_NAME_BDAY_MONTH, birthdayMonth);
            values.put(CurpEntry.COLUMN_NAME_BDAY_DAY, birthdayDay);
            values.put(CurpEntry.COLUMN_NAME_IMGPATH, imgpath);

            if (id == 0) {
                Long ids = db.insert(CurpEntry.TABLE_NAME, CurpEntry._ID, values);
                id = ids;
            } else {
                String[] selectionArgs = {String.valueOf(id)};
                db.update(CurpEntry.TABLE_NAME, values, CurpEntry._ID + " = ?", selectionArgs);
            }
            return null;
        }
    }

    private static class getCurpsInDatabase extends AsyncTask<DatabaseSelectArgs, Void, ArrayList<Curp>> {
        @Override
        protected ArrayList<Curp> doInBackground(DatabaseSelectArgs... databaseSelectArgsArray) {
            DatabaseSelectArgs dsa = databaseSelectArgsArray[0];
            CurpDbHelper curpDbHelper = new CurpDbHelper(dsa.getContext());
            SQLiteDatabase db = curpDbHelper.getWritableDatabase();

            String[] projection = {
                    CurpEntry._ID,
                    CurpEntry.COLUMN_NAME_NAME,
                    CurpEntry.COLUMN_NAME_FATHER_LASTNAME,
                    CurpEntry.COLUMN_NAME_MOTHER_LASTNAME,
                    CurpEntry.COLUMN_NAME_GENDER,
                    CurpEntry.COLUMN_NAME_STATE,
                    CurpEntry.COLUMN_NAME_BDAY_DAY,
                    CurpEntry.COLUMN_NAME_BDAY_MONTH,
                    CurpEntry.COLUMN_NAME_BDAY_YEAR,
                    CurpEntry.COLUMN_NAME_IMGPATH
            };

            Cursor cursor = db.query(
                    CurpEntry.TABLE_NAME,
                    projection,
                    dsa.getSelection(),
                    dsa.getSelectionArgs(),
                    null,
                    null,
                    dsa.getSortOrder()
            );

            ArrayList<Curp> items = new ArrayList<Curp>();
            while (cursor.moveToNext()) {
                long cId = cursor.getLong(cursor.getColumnIndexOrThrow(CurpEntry._ID));
                String cName = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_NAME));
                String cFatherLastName = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_FATHER_LASTNAME));
                String cMotherLastName = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_MOTHER_LASTNAME));
                String cGender = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_GENDER));
                String cState = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_STATE));
                int cYear = cursor.getInt(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_BDAY_YEAR));
                int cMonth = cursor.getInt(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_BDAY_MONTH));
                int cDay = cursor.getInt(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_BDAY_DAY));
                String cImgpath = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_IMGPATH));

                Curp c = new Curp(cName, cFatherLastName, cMotherLastName, cGender, cYear, cMonth, cDay, cState, cId, cImgpath);

                items.add(c);
            }
            cursor.close();
            return items;
        }
    }
}



