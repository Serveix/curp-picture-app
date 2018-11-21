package com.eliapp.curpform2.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.eliapp.curpform2.database.CurpDbHelper;
import static com.eliapp.curpform2.database.CurpContract.*;

import java.util.GregorianCalendar;
import java.util.ArrayList;

public class Curp  implements Parcelable{

    private long id;
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String gender;
    private int birthdayYear;
    private int birthdayMonth;
    private int birthdayDay;
    private String state;

    public Curp(){
        this.id = 0;
        this.name = "";
        this.fatherLastName = "";
        this.motherLastName = "";
        this.gender = "";
        this.birthdayYear = 1900;
        this.birthdayMonth = 1;
        this.birthdayDay = 1;
        this.state = "";
    }

    private Curp(Parcel in){
        this.id = 0;
        this.name = in.readString();
        this.fatherLastName = in.readString();
        this.motherLastName = in.readString();
        this.gender = in.readString();
        this.birthdayYear = in.readInt();
        this.birthdayMonth = in.readInt();
        this.birthdayDay = in.readInt();
        this.state = in.readString();
    }

    public Curp(String name, String fatherLastName, String motherLastName, String gender, int day, int month, int year, String state){
        this.name = name;
        this.fatherLastName = fatherLastName;
        this.motherLastName = motherLastName;
        this.gender = gender;
        this.birthdayYear = year;
        this.birthdayMonth = month;
        this.birthdayDay = day;
        this.state = state;
    }

    public Curp(String name, String fatherLastName, String motherLastName, String gender, int day, int month, int year, String state, long id){
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
    }

    public static final Parcelable.Creator<Curp> CREATOR
            = new Parcelable.Creator<Curp>(){
        public Curp createFromParcel(Parcel in) {
            return new Curp(in);
        }

        @Override
        public Curp[] newArray(int i) {
            return new Curp[i];
        }
    };

    public String getCurp(){
        String c = "";
        c += (this.fatherLastName.toUpperCase().toCharArray())[0];
        c += (this.fatherLastName.toUpperCase().toCharArray())[1];
        c += (this.motherLastName.toUpperCase().toCharArray())[0];
        c += (this.name.toUpperCase().toCharArray())[0];
        c += String.format("%1$02d%2$02d%3$02d", this.birthdayYear, this.birthdayMonth, this.birthdayDay);

        return c;
    }

    public String getBirthdayString(){
        return String.format("%1$02d/%2$02d/%3$02d", this.birthdayDay, this.birthdayMonth, this.birthdayYear);
    }

    public void save(Context context){
        CurpDbHelper curpDbHelper = new CurpDbHelper(context);
        SQLiteDatabase db = curpDbHelper.getWritableDatabase();

        //Create new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CurpEntry.COLUMN_NAME_NAME, name);
        values.put(CurpEntry.COLUMN_NAME_FATHER_LASTNAME, fatherLastName);
        values.put(CurpEntry.COLUMN_NAME_MOTHER_LASTNAME, motherLastName);
        values.put(CurpEntry.COLUMN_NAME_GENDER, gender);
        values.put(CurpEntry.COLUMN_NAME_STATE, state);
        values.put(CurpEntry.COLUMN_NAME_BDAY_YEAR, birthdayYear);
        values.put(CurpEntry.COLUMN_NAME_BDAY_MONTH, birthdayMonth);
        values.put(CurpEntry.COLUMN_NAME_BDAY_DAY, birthdayDay);

        if (this.id == 0){
            Long id = db.insert(CurpEntry.TABLE_NAME, CurpEntry._ID, values);
            this.id = id;
        } else {
            String[] selectionArgs = { String.valueOf(this.id) };
            db.update(CurpEntry.TABLE_NAME, values, CurpEntry._ID + " = ?", selectionArgs);
        }
    }

    public static ArrayList<Curp> getCurps(Context context){
        String[] args = {};
        return getCurps(context, "", args, CurpEntry._ID + " ASC");
    }

    public static ArrayList<Curp> getCurps(Context context, String selection, String[] selectionArgs){
        return getCurps(context, selection, selectionArgs, CurpEntry._ID + " ASC");
    }

    public static ArrayList<Curp> getCurps(Context context, String selection, String[] selectionArgs, String sortOrder){
        CurpDbHelper curpDbHelper = new CurpDbHelper(context);
        SQLiteDatabase db = curpDbHelper.getWritableDatabase();

        String[] projection = {
                CurpEntry._ID,
                CurpEntry.COLUMN_NAME_NAME,
                CurpEntry.COLUMN_NAME_FATHER_LASTNAME,
                CurpEntry.COLUMN_NAME_MOTHER_LASTNAME,
                CurpEntry.COLUMN_NAME_GENDER,
                CurpEntry.COLUMN_NAME_STATE,
                CurpEntry.COLUMN_NAME_BDAY_YEAR,
                CurpEntry.COLUMN_NAME_BDAY_MONTH,
                CurpEntry.COLUMN_NAME_BDAY_DAY
        };

        Cursor cursor = db.query(
                CurpEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<Curp> items = new ArrayList<Curp>();
        while (cursor.moveToNext()) {
            long c_Id = cursor.getLong(cursor.getColumnIndexOrThrow(CurpEntry._ID));
            String c_Name = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_NAME));
            String c_FatherLastName = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_FATHER_LASTNAME));
            String c_MotherLastName = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_MOTHER_LASTNAME));
            String c_Gender = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_GENDER));
            String c_State = cursor.getString(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_STATE));
            int c_year = cursor.getInt(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_BDAY_YEAR));
            int c_month = cursor.getInt(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_BDAY_MONTH));
            int c_day = cursor.getInt(cursor.getColumnIndexOrThrow(CurpEntry.COLUMN_NAME_BDAY_DAY));

            Curp c = new Curp(c_Name, c_FatherLastName, c_MotherLastName, c_Gender, c_year, c_month, c_day, c_State, c_Id);

            items.add(c);
        }

        cursor.close();

        return items;
    }
}



