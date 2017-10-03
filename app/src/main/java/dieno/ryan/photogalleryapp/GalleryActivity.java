package dieno.ryan.photogalleryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;

public class GalleryActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    ImageAdapter imgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dbHelper = new DatabaseHelper(this);
        AddDummyImagesToDB();

        GridView gridview = (GridView) findViewById(R.id.gallery);

        imgAdapter = new ImageAdapter(this);
        imgAdapter.photos = dbHelper.getAllPhotos();

        gridview.setAdapter(imgAdapter);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                //Class targetActivity = getTargetActivityForPosition(position);
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, ImageActivity.class));
            }
        });


    }


    public void AddDummyImagesToDB() {


        // get image from drawable
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.cow);
        //convert to byte array
        String id = "0";
        //String date = "MM/dd/yy";
        String date = "01/05/17";

        //add to db
        dbHelper.addEntry(id, date, DbBitmapUtility.getBytes(image));


        // get image from drawable
        image = BitmapFactory.decodeResource(getResources(), R.drawable.eagle);
        //convert to byte array
        id = "1";
        //String date = "MM/dd/yy";
        date = "03/10/17";

        //add to db
        dbHelper.addEntry(id, date, DbBitmapUtility.getBytes(image));

        // get image from drawable
        image = BitmapFactory.decodeResource(getResources(), R.drawable.tiger);
        //convert to byte array
        id = "2";
        //String date = "MM/dd/yy";
        date = "06/22/17";

        //add to db
        dbHelper.addEntry(id, date, DbBitmapUtility.getBytes(image));

    }



    public void openFilter(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }

    public void openCamera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }
}
