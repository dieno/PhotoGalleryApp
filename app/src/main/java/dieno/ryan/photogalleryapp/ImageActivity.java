package dieno.ryan.photogalleryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    DatabaseController dbController;
    int currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        dbController = new DatabaseController(this);
        currentID = getIntent().getIntExtra("IMAGE_ID", 1);
        Bitmap photo = dbController.getPhotoFromID(currentID);

        String date = dbController.getDateFromID(currentID);
        String city = dbController.getCityFromID(currentID);
        String country = dbController.getCountryFromID(currentID);
        String keyword = dbController.getKeywordFromID(currentID);

        TextView myTextView = (TextView) findViewById(R.id.image_date);
        myTextView.setText(date);

        myTextView = (TextView) findViewById(R.id.image_location);
        String cityCountry = city + ", " + country;
        myTextView.setText(cityCountry);

        myTextView = (TextView) findViewById(R.id.image_keywords);
        myTextView.setText(keyword);

        ImageView imgView = (ImageView) findViewById(R.id.image_view);
        imgView.setImageBitmap(photo);
    }

    public void openGallery(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }


    public void openEdit(View view) {
        Intent intent = new Intent(this, ImageEditActivity.class);
        intent.putExtra("IMAGE_ID", currentID);
        startActivity(intent);
    }
}
