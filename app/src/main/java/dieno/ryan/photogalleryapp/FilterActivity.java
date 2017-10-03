package dieno.ryan.photogalleryapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;

import java.util.Locale;

public class FilterActivity extends AppCompatActivity {

    Calendar myCalendarStartDate;
    Calendar myCalendarEndDate;
    EditText startDate;
    EditText endDate;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        myCalendarStartDate = Calendar.getInstance();
        myCalendarEndDate = Calendar.getInstance();

        startDate = (EditText) findViewById(R.id.filter_start_date);
        endDate = (EditText) findViewById(R.id.filter_end_date);
        startDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStartDate.set(Calendar.YEAR, year);
                myCalendarStartDate.set(Calendar.MONTH, monthOfYear);
                myCalendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartDate();
            }

        };

        endDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEndDate.set(Calendar.YEAR, year);
                myCalendarEndDate.set(Calendar.MONTH, monthOfYear);
                myCalendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEndDate();
            }

        };

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FilterActivity.this, startDatePicker, myCalendarStartDate
                        .get(Calendar.YEAR), myCalendarStartDate.get(Calendar.MONTH),
                        myCalendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FilterActivity.this, endDatePicker, myCalendarEndDate
                        .get(Calendar.YEAR), myCalendarEndDate.get(Calendar.MONTH),
                        myCalendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void openGallery(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    public void openCamera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void updateLabelStartDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);

        startDate.setText(sdf.format(myCalendarStartDate.getTime()));
    }

    private void updateLabelEndDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);

        endDate.setText(sdf.format(myCalendarEndDate.getTime()));
    }
}
