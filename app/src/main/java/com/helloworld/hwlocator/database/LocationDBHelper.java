package com.helloworld.hwlocator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.helloworld.hwlocator.model.LocationObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snakelogan on 2/6/16.
 */
public class LocationDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hwlocator.locationDB";
    private static final int DB_VERSION = 1;

    public static final String LOCATION_TABLE_NAME = "location";
    public static final String LOCATION_NAME = "name";
    public static final String LOCATION_ADDRESS = "address";
    public static final String LOCATION_ADDRESS2 = "address2";
    public static final String LOCATION_CITY = "city";
    public static final String LOCATION_STATE = "state";
    public static final String LOCATION_ZIP_CODE = "zipCode";
    public static final String LOCATION_PHONE = "phone";
    public static final String LOCATION_FAX = "fax";
    public static final String LOCATION_LATITUDE = "latitude";
    public static final String LOCATION_LONGITUDE = "longitude";
    public static final String LOCATION_IMAGE = "image";

    private static final String STRING_CREATE =
            "CREATE TABLE " + LOCATION_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LOCATION_NAME + " TEXT, "
                    + LOCATION_ADDRESS + " TEXT, "
                    + LOCATION_ADDRESS2 + " TEXT, "
                    + LOCATION_CITY + " TEXT, "
                    + LOCATION_STATE + " TEXT, "
                    + LOCATION_ZIP_CODE + " TEXT, "
                    + LOCATION_PHONE + " TEXT, "
                    + LOCATION_FAX + " TEXT, "
                    + LOCATION_LATITUDE + " TEXT, "
                    + LOCATION_LONGITUDE + " TEXT, "
                    + LOCATION_IMAGE + " TEXT);";

    public LocationDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        onCreate(db);
    }

    public void insertLocation(LocationObject locationObject) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = setContentValues(locationObject);
        db.insert(LOCATION_TABLE_NAME, null, contentValues);
    }

    public List<LocationObject> getAllLocations() {
        List<LocationObject> locationObjectList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME, null);
        if(cursor.moveToFirst()) {
            do {
                LocationObject locationObject = new LocationObject();
                locationObject.setName(cursor.getString(cursor.getColumnIndex(LOCATION_NAME)));
                locationObject.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION_ADDRESS)));
                locationObject.setAddress2(cursor.getString(cursor.getColumnIndex(LOCATION_ADDRESS2)));
                locationObject.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_CITY)));
                locationObject.setState(cursor.getString(cursor.getColumnIndex(LOCATION_STATE)));
                locationObject.setZipPostalCode(cursor.getString(cursor.getColumnIndex(LOCATION_ZIP_CODE)));
                locationObject.setPhone(cursor.getString(cursor.getColumnIndex(LOCATION_PHONE)));
                locationObject.setFax(cursor.getString(cursor.getColumnIndex(LOCATION_FAX)));
                locationObject.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_LATITUDE)));
                locationObject.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_LONGITUDE)));
                locationObject.setOfficeImage(cursor.getString(cursor.getColumnIndex(LOCATION_IMAGE)));
                locationObjectList.add(locationObject);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return locationObjectList;
    }

    private ContentValues setContentValues(LocationObject locationObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_NAME, locationObject.getName());
        contentValues.put(LOCATION_ADDRESS, locationObject.getAddress());
        contentValues.put(LOCATION_ADDRESS2, locationObject.getAddress2());
        contentValues.put(LOCATION_CITY, locationObject.getCity());
        contentValues.put(LOCATION_STATE, locationObject.getState());
        contentValues.put(LOCATION_ZIP_CODE, locationObject.getZipPostalCode());
        contentValues.put(LOCATION_PHONE, locationObject.getPhone());
        contentValues.put(LOCATION_FAX, locationObject.getFax());
        contentValues.put(LOCATION_LATITUDE, locationObject.getLatitude());
        contentValues.put(LOCATION_LONGITUDE, locationObject.getLongitude());
        contentValues.put(LOCATION_IMAGE, locationObject.getOfficeImage());
        return contentValues;
    }

    public static boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

}
