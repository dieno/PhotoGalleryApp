package dieno.ryan.photogalleryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ImageEditActivity extends AppCompatActivity {

    DatabaseController dbController;
    int currentID = 0;

    EditText editTextDate;
    EditText editTextCity;
    EditText editTextCountry;
    EditText editTextKeyword;

    String Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        dbController = new DatabaseController(this);
        currentID = getIntent().getIntExtra("IMAGE_ID", 1);
        Bitmap photo = dbController.getPhotoFromID(currentID);

        String date = dbController.getDateFromID(currentID);
        String city = dbController.getCityFromID(currentID);
        String country = dbController.getCountryFromID(currentID);
        String keyword = dbController.getKeywordFromID(currentID);

        editTextDate = (EditText) findViewById(R.id.image_edit_date);
        editTextCity = (EditText) findViewById(R.id.image_edit_city);
        editTextCountry = (EditText) findViewById(R.id.image_edit_country);
        editTextKeyword = (EditText) findViewById(R.id.image_edit_keyword);

        editTextDate.setText(date);
        editTextCity.setText(city);
        editTextCountry.setText(country);
        editTextKeyword.setText(keyword);
    }

    public void openImage(View view) {
        dbController.setDateFromID(currentID, editTextDate.getText().toString());
        dbController.setLocationFromID(currentID, editTextCity.getText().toString(), editTextCountry.getText().toString());
        dbController.setKeywordFromID(currentID, editTextKeyword.getText().toString());

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("IMAGE_ID", currentID);
        startActivity(intent);
    }
}
