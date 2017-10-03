package dieno.ryan.photogalleryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Ryan on 10/2/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PhotoGalleryAppDb";

    // Table Names
    private static final String DB_TABLE = "Photos";

    // column names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_ID + " TEXT," +
            KEY_DATE + " TEXT," +
            KEY_IMAGE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }

    public void addEntry( String id, String date, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,    id);
        cv.put(KEY_DATE,    date);
        cv.put(KEY_IMAGE,   image);
        database.insert( DB_TABLE, null, cv );
    }

    public ArrayList<Bitmap> getAllPhotos() {
        String query = "SELECT * FROM " + DB_TABLE;
        ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                //String id = c.getString(c.getColumnIndex(KEY_ID));
                //String date = c.getString(c.getColumnIndex(KEY_DATE));
                //byte[] image = c.getBlob(c.getColumnIndex(KEY_IMAGE));
                byte[] image = c.getBlob(2);


                Bitmap bm = DbBitmapUtility.getImage(image);

                photos.add(bm);
            }
        }

        return photos;

    }
}