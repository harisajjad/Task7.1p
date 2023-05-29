package com.example.Task71P;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdvertActivity extends AppCompatActivity {

    private RadioGroup radioGroupContainer;
    private RadioButton radioButtonLost;
    private RadioButton radioButtonFound;
    private EditText etName;
    private EditText etPhone;
    private EditText etDescription;
    private EditText etDate;
    private EditText etLocation;
    private Button btnSave;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        dbHelper = new DatabaseHelper(this);

        // Initialize views
        radioGroupContainer = findViewById(R.id.radioGroupLostAndFound);
        radioButtonLost = findViewById(R.id.rbLost);
        radioButtonFound = findViewById(R.id.rbFound);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        btnSave = findViewById(R.id.btnSave);

        // Set click listener for the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdvert();
            }
        });
    }

    private void saveAdvert() {
        // Get the values entered by the user
        String postType = radioButtonLost.isChecked() ? "Lost" : "Found";
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        // Get a writable database instance
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new ContentValues object and put the values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.DatabaseContract.AdvertEntry.COLUMN_POST_TYPE, postType);
        values.put(DatabaseHelper.DatabaseContract.AdvertEntry.COLUMN_NAME, name);
        values.put(DatabaseHelper.DatabaseContract.AdvertEntry.COLUMN_PHONE, phone);
        values.put(DatabaseHelper.DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION, description);
        values.put(DatabaseHelper.DatabaseContract.AdvertEntry.COLUMN_DATE, date);
        values.put(DatabaseHelper.DatabaseContract.AdvertEntry.COLUMN_LOCATION, location);

        // Insert the values into the database table
        long newRowId = db.insert(DatabaseHelper.DatabaseContract.AdvertEntry.TABLE_NAME, null, values);

        // Check if the insertion was successful
        if (newRowId != -1) {
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
        }
    }
}
