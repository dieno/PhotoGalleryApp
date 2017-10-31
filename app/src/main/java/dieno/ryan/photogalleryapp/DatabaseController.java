package dieno.ryan.photogalleryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ryan on 10/3/2017.
 */

public class DatabaseController {

    public Context mContext;
    String dbName = "PhotoGalleryApp.db";
    SQLiteDatabase db;

    public DatabaseController(Context context) {
        mContext = context;
        db = context.openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            return;
        }

        db.beginTransaction();
        try{

            //db.execSQL("DROP TABLE IF EXISTS " + "Photos2");

            //db.execSQL("CREATE TABLE IF NOT EXISTS "
            //        + "Photos"
            //        + " (id VARCHAR, date VARCHAR, image VARCHAR);");


            //db.execSQL("CREATE TABLE IF NOT EXISTS "
            //        + "Photos2"
            //        + " (id VARCHAR, date VARCHAR, location VARCHAR, keyword VARCHAR, image VARCHAR);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "Photos2"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR, city VARCHAR, country VARCHAR, keyword VARCHAR, image VARCHAR);");

            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            db.endTransaction();
        }

    }

    public void addEntry(String date, String city, String country, String keyword, Bitmap image) {
        if(!db.isOpen()) {
            return;
        }

        db.beginTransaction();
        try{
            String encodedImage = DbBitmapUtility.encodeToBase64(image,Bitmap.CompressFormat.JPEG, 100);
            String sqlq = "INSERT INTO Photos2 (date, city, country, keyword, image) VALUES ('" + date + "', '" + city + "', '" + country + "', '" + keyword + "', '" + encodedImage + "'" + ");";

            //String sqlq = "INSERT INTO Photos (id, date, image_data) VALUES ('" + id + "', '" + date + "', 'test');";

            //String sqlq = "INSERT INTO Photos (id, date, image) VALUES ('test', 'test', 'test');";
            db.execSQL(sqlq);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            db.endTransaction();
        }
    }


    public void addEntryOLD( String id, String date, Bitmap image) {
        if(!db.isOpen()) {
            return;
        }

        db.beginTransaction();
        try{
            String encodedImage = DbBitmapUtility.encodeToBase64(image,Bitmap.CompressFormat.JPEG, 100);
            String sqlq = "INSERT INTO Photos (id, date, image) VALUES ('" + id + "', '" + date + "', '" + encodedImage + "'" + ");";

            //String sqlq = "INSERT INTO Photos (id, date, image_data) VALUES ('" + id + "', '" + date + "', 'test');";

            //String sqlq = "INSERT INTO Photos (id, date, image) VALUES ('test', 'test', 'test');";
            db.execSQL(sqlq);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            db.endTransaction();
        }
    }


    public ArrayList<Bitmap> getPhotos(ArrayList<String> ids) {
        //String query = "SELECT * FROM " + DB_TABLE;

        ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
        //Cursor c = database.rawQuery(query, null);

        Cursor c = db.query("Photos2", null, null, null, null, null, null);


        if (c != null) {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {
                //String id = c.getString(c.getColumnIndex(KEY_ID));
                //String date = c.getString(c.getColumnIndex(KEY_DATE));
                //String encodedImage = c.getString(c.getColumnIndex(KEY_IMAGE));


                int idColumn = c.getColumnIndex("id");
                int imageColumn = c.getColumnIndex("image");

                String id = c.getString(idColumn);


                for (int j = 0; j < ids.size(); j++) {
                    String currentId = ids.get(j);
                    if(currentId.equals(id)) {
                        String image = c.getString(imageColumn);
                        Bitmap bm = DbBitmapUtility.decodeBase64(image);
                        photos.add(bm);
                    }
                }

                c.moveToNext();
            }
            c.close();

        }

        return photos;

    }

    public ArrayList<String> getAllPhotos() {
        //String query = "SELECT * FROM " + DB_TABLE;

        ArrayList<String> ids = new ArrayList<String>();

        //Cursor c = database.rawQuery(query, null);

        Cursor c = db.query("Photos2", null, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {

                int idColumn = c.getColumnIndex("id");

                String id = c.getString(idColumn);

                ids.add(id);
                c.moveToNext();
            }
            c.close();

        }

        return ids;

    }

    public Bitmap getPhotoFromID(int id) {
        Cursor c = db.rawQuery("SELECT * FROM Photos2 WHERE id = "+ id, null);
        c.moveToFirst();
        int imageColumn = c.getColumnIndex("image");
        String image = c.getString(imageColumn);
        Bitmap bm = DbBitmapUtility.decodeBase64(image);
        c.close();
        return bm;
    }

    public String getDateFromID(int id) {
        Cursor c = db.rawQuery("SELECT * FROM Photos2 WHERE id = "+ id, null);
        c.moveToFirst();
        int dateColumn = c.getColumnIndex("date");
        String date = c.getString(dateColumn);
        c.close();
        return date;
    }

    public String getCityFromID(int id) {
        Cursor c = db.rawQuery("SELECT * FROM Photos2 WHERE id = "+ id, null);
        c.moveToFirst();
        int cityColumn = c.getColumnIndex("city");
        String city = c.getString(cityColumn);
        c.close();
        return city;
    }

    public String getCountryFromID(int id) {
        Cursor c = db.rawQuery("SELECT * FROM Photos2 WHERE id = "+ id, null);
        c.moveToFirst();
        int countryColumn = c.getColumnIndex("country");
        String country = c.getString(countryColumn);
        c.close();
        return country;
    }

    public String getKeywordFromID(int id) {
        Cursor c = db.rawQuery("SELECT * FROM Photos2 WHERE id = "+ id, null);
        c.moveToFirst();
        int keywordColumn = c.getColumnIndex("keyword");
        String keyword = c.getString(keywordColumn);
        c.close();
        return keyword;
    }

    public void setDateFromID(int id, String value) {
        ContentValues cv = new ContentValues();
        cv.put("date", value);
        db.update("Photos2", cv, "id" + "= " + id, null);
    }

    public void setKeywordFromID(int id, String value) {
        ContentValues cv = new ContentValues();
        cv.put("keyword", value);
        db.update("Photos2", cv, "id" + "= " + id, null);
    }

    public void setLocationFromID(int id, String city, String country) {
        ContentValues cv = new ContentValues();
        cv.put("city", city);
        cv.put("country", country);
        db.update("Photos2", cv, "id" + "= " + id, null);
    }

    public ArrayList<Bitmap> getAllPhotosTWO() {
        //String query = "SELECT * FROM " + DB_TABLE;



        ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
        //Cursor c = database.rawQuery(query, null);

        Cursor c = db.query("Photos", null, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {
                //String id = c.getString(c.getColumnIndex(KEY_ID));
                //String date = c.getString(c.getColumnIndex(KEY_DATE));
                //String encodedImage = c.getString(c.getColumnIndex(KEY_IMAGE));


                int idColumn = c.getColumnIndex("id");
                int dateColumn = c.getColumnIndex("date");
                int imageColumn = c.getColumnIndex("image");

                String id = c.getString(idColumn);
                String date = c.getString(dateColumn);
                String image = c.getString(imageColumn);
                Bitmap bm = DbBitmapUtility.decodeBase64(image);

                photos.add(bm);
                c.moveToNext();
            }
            c.close();

        }

        return photos;

    }
}
