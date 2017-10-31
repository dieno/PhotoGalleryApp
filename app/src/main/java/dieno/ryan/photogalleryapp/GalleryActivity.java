package dieno.ryan.photogalleryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.util.ArrayList;

import dieno.ryan.photogalleryapp.SearchFilters.SearchFilter;

public class GalleryActivity extends AppCompatActivity {

    DatabaseController dbController;

    ImageAdapter imgAdapter;

    SearchFilter sFilter;

    public ArrayList<String> currentIdList;

    private static final int CAMERA_REQUEST = 1;

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

                Intent intent = new Intent(GalleryActivity.this, ImageActivity.class);
                intent.putExtra("IMAGE_ID", position + 1);
                GalleryActivity.this.startActivity(new Intent(intent));
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
        else if(getIntent().hasExtra("LOCATION_CITY") && getIntent().hasExtra("LOCATION_COUNTRY"))
        {
            String city = getIntent().getStringExtra("CITY_STRING");
            String country = getIntent().getStringExtra("COUNTRY_STRING");
            idList = sFilter.SearchByLocation(city, country);
        }
        else if(getIntent().hasExtra("KEYWORD"))
        {
            String keyword = getIntent().getStringExtra("KEYWORD_STRING");
            idList = sFilter.SearchByKeyword(keyword);
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
        String city = "Vancouver";
        String country = "Canada";
        String keyword = "cow";

        //add to db
        dbController.addEntry(date, city, country, keyword, image);


        // get image from drawable
        image = BitmapFactory.decodeResource(getResources(), R.drawable.eagle);
        //convert to byte array
        id = "1";
        //String date = "MM/dd/yy";
        date = "03/10/17";
        city = "Seattle";
        country = "USA";
        keyword = "eagle";

        //add to db
        dbController.addEntry(date, city, country, keyword, image);

        // get image from drawable
        image = BitmapFactory.decodeResource(getResources(), R.drawable.tiger);
        //convert to byte array
        id = "2";
        //String date = "MM/dd/yy";
        date = "06/22/17";
        city = "Vancouver";
        country = "USA";
        keyword = "tiger";

        //add to db
        dbController.addEntry(date, city, country, keyword, image);

    }



    public void openFilter(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }

    public void openCamera(View view) {
        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //startActivity(intent);

        Intent cameraIntent = new  Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap picture = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this
                //imageView.setImageBitmap(picture); //for example I put bmp in an ImageView

                Calendar c = Calendar.getInstance();
                //System.out.println("Current time =&gt; "+c.getTime());

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                String formattedDate = sdf.format(c.getTime());

                dbController.addEntry(formattedDate, "Vancouver", "Canada", "photo", picture);

                currentIdList = getPhotoIDs();
                imgAdapter.photos = dbController.getPhotos(currentIdList);

            }
    }

}
