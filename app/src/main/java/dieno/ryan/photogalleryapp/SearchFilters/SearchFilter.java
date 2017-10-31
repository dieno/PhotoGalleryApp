package dieno.ryan.photogalleryapp.SearchFilters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dieno.ryan.photogalleryapp.DbBitmapUtility;

/**
 * Created by Ryan on 10/3/2017.
 */

public class SearchFilter {

    Context mContext;

    public SearchFilter(Context context) {mContext = context;}

    public ArrayList<String> SearchByKeyword(String keyword) {
        SQLiteDatabase db = mContext.openOrCreateDatabase("PhotoGalleryApp.db", 0, null);
        ArrayList<String> idArray = new ArrayList<String>();

        try {
            //dbCursor = db.query("PhoneBook", null, null, null, null, null, null);

            //Cursor dbCursor = db.query("Photos", null, "date='" + date + "'", null, null, null, null);

            Cursor dbCursor = db.query("Photos2", null, null, null, null, null, null);

            int idCol = dbCursor.getColumnIndex("id");
            int keywordCol = dbCursor.getColumnIndex("keyword");

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0;
                    do {
                        i++;

                        String id = dbCursor.getString(idCol);
                        String keywordVal = dbCursor.getString(keywordCol);

                        if(keywordVal.equals(keyword)) {
                            idArray.add(id);
                        }
                    } while (dbCursor.moveToNext());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idArray;
    }

    public ArrayList<String> SearchByLocation(String city, String country) {
        SQLiteDatabase db = mContext.openOrCreateDatabase("PhotoGalleryApp.db", 0, null);
        ArrayList<String> idArray = new ArrayList<String>();

        try {
            //dbCursor = db.query("PhoneBook", null, null, null, null, null, null);

            //Cursor dbCursor = db.query("Photos", null, "date='" + date + "'", null, null, null, null);

            Cursor dbCursor = db.query("Photos2", null, null, null, null, null, null);

            int idCol = dbCursor.getColumnIndex("id");
            int cityCol = dbCursor.getColumnIndex("city");
            int countryCol = dbCursor.getColumnIndex("country");

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0;
                    do {
                        i++;

                        String id = dbCursor.getString(idCol);
                        String cityVal = dbCursor.getString(cityCol);
                        String countryVal = dbCursor.getString(countryCol);

                        if(cityVal.equals(city) && countryVal.equals(country)) {
                            idArray.add(id);
                        }
                    } while (dbCursor.moveToNext());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idArray;
    }

    public ArrayList<String> SearchByDate(String startDate, String endDate) {
        SQLiteDatabase db = mContext.openOrCreateDatabase("PhotoGalleryApp.db", 0, null);
        ArrayList<String> idArray = new ArrayList<String>();
        Date mStartDate = null;
        Date mEndDate = null;
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);

        try {
            mStartDate = dateFormat.parse(startDate);
            mEndDate = dateFormat.parse(endDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        try {
            //dbCursor = db.query("PhoneBook", null, null, null, null, null, null);

           //Cursor dbCursor = db.query("Photos", null, "date='" + date + "'", null, null, null, null);

            Cursor dbCursor = db.query("Photos2", null, null, null, null, null, null);

            int idCol = dbCursor.getColumnIndex("id");
            int dateCol = dbCursor.getColumnIndex("date");
            int imageCol = dbCursor.getColumnIndex("image");

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0;
                    do {
                        i++;

                        String id = dbCursor.getString(idCol);
                        String date = dbCursor.getString(dateCol);
                        Date parsedDate = null;

                        try {
                            parsedDate = dateFormat.parse(date);
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        if((parsedDate.equals(mStartDate) || parsedDate.after(mStartDate)) &&
                           (parsedDate.equals(mEndDate) || parsedDate.before(mEndDate))) {
                            idArray.add(id);
                        }
                    } while (dbCursor.moveToNext());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idArray;
    }


    public ArrayList<Bitmap> SearchByDateTwo(String date) {
        SQLiteDatabase db = mContext.openOrCreateDatabase("PhotoGalleryApp.db", 0, null);
        ArrayList<Bitmap> bmArray = new ArrayList<Bitmap>();

        try {
            //dbCursor = db.query("PhoneBook", null, null, null, null, null, null);

            Cursor dbCursor = db.query("Photos", null, "date='" + date + "'", null, null, null, null);

            int idCol = dbCursor.getColumnIndex("id");
            int dateCol = dbCursor.getColumnIndex("date");
            int imageCol = dbCursor.getColumnIndex("image");

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0;
                    do {
                        i++;

                        String name = dbCursor.getString(idCol);
                        String number = dbCursor.getString(dateCol);
                        String image = dbCursor.getString(imageCol);

                        Bitmap bm = DbBitmapUtility.decodeBase64(image);
                        bmArray.add(bm);
                    } while (dbCursor.moveToNext());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmArray;
    }

}
