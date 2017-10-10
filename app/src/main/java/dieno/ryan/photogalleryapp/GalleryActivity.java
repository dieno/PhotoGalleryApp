package dieno.ryan.photogalleryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dieno.ryan.photogalleryapp.SearchFilters.SearchFilter;

public class GalleryActivity extends AppCompatActivity {

    DatabaseController dbController;

    ImageAdapter imgAdapter;

    SearchFilter sFilter;

    public ArrayList<String> currentIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

       // AddDummyImagesToDB();

        GridView gridview = (GridView) findViewById(R.id.gallery);

        dbController = new DatabaseController(this);
        //AddDummyImagesToDB();
        currentIdList = new ArrayList<String>();
        imgAdapter = new ImageAdapter(this);
        sFilter = new SearchFilter(this);

        currentIdList = getPhotoIDs();

        //imgAdapter.photos = dbController.getAllPhotos();

        imgAdapter.photos = dbController.getPhotos(currentIdList);


        //imgAdapter.photos = sFilter.SearchByDate("01/05/17");


        gridview.setAdapter(imgAdapter);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                //Class targetActivity = getTargetActivityForPosition(position);
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, ImageActivity.class));
            }
        });


    }

    public ArrayList<String> getPhotoIDs() {
        ArrayList<String> idList = new ArrayList<String>();

        if(getIntent().hasExtra("START_DATE") && getIntent().hasExtra("END_DATE")) {
            String startDate = getIntent().getStringExtra("START_DATE_STRING");
            String endDate = getIntent().getStringExtra("END_DATE_STRING");
            idList = sFilter.SearchByDate(startDate,endDate);
        }
        else
        {
            idList = dbController.getAllPhotos();
        }


        return idList;
    }


    public void AddDummyImagesToDB() {


        // get image from drawable
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.cow);
        //convert to byte array
        String id = "0";
        //String date = "MM/dd/yy";
        String date = "01/05/17";

        //add to db
        dbController.addEntry(id, date, image);


        // get image from drawable
        image = BitmapFactory.decodeResource(getResources(), R.drawable.eagle);
        //convert to byte array
        id = "1";
        //String date = "MM/dd/yy";
        date = "03/10/17";

        //add to db
        dbController.addEntry(id, date, image);

        // get image from drawable
        image = BitmapFactory.decodeResource(getResources(), R.drawable.tiger);
        //convert to byte array
        id = "2";
        //String date = "MM/dd/yy";
        date = "06/22/17";

        //add to db
        dbController.addEntry(id, date, image);

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
