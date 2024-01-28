package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class EditEvent extends AppCompatActivity {

    private ImageView eventImageView;
    private EditText nameEditText , overviewEditText ,dateEditText, DurationEditText, timeEditText, locationEditText;
    private Spinner categorySpinner, languageSpinner;
    private RadioGroup radioGroupAttendingMethod;
    private RadioButton radioButtonAttendingMethodSelected;

    private Button updateButton;
    private String imageURL, initiative;
    private Uri uri;

    private RadioButton onlineRadioButton, inPersonRadioButton;
    private DatePickerDialog picker;
    private FirebaseAuth authProfile;
    private DataClass dataClass;

    private ArrayAdapter<CharSequence> adapter;
    private  ArrayAdapter<CharSequence> adapter2;

    private String name, overview, date, duration, time, location, category, language, attendingMethod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        getSupportActionBar().setTitle("Update Event");

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("eventName");
        overview = bundle.getString("eventOverview");
        date = bundle.getString("eventDate");
        duration = bundle.getString("eventDuration");
        time = bundle.getString("eventTime");
        location = bundle.getString("eventLocation");
        category = bundle.getString("eventCategory");
        language = bundle.getString("eventLanguage");
        attendingMethod = bundle.getString("eventAttendingMethod");


        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        dataClass = new DataClass();

        eventImageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.eventName);
        overviewEditText = findViewById(R.id.eventOverview);
        dateEditText = findViewById(R.id.eventDate);
        DurationEditText = findViewById(R.id.eventDuration);
        timeEditText = findViewById(R.id.editEventTime);
        locationEditText = findViewById(R.id.eventLocation);

        updateButton = findViewById(R.id.update_event_button);

        onlineRadioButton = findViewById(R.id.onlineRB);
        inPersonRadioButton = findViewById(R.id.inPersonRB);

        radioGroupAttendingMethod = findViewById(R.id.radioGroup);

        categorySpinner =findViewById(R.id.eventCategory);
        adapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(adapter);

        languageSpinner =findViewById(R.id.eventLanguage);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.languages,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        languageSpinner.setAdapter(adapter2);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(EditEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        dateEditText.setText(dayOfMonth+ "/" + (month+1) + "/" + year);
                    }
                } , year, month, day);
                picker.show();
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeEditText.setText(i + " : " + i1);
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Time of Event");
                timePickerDialog.show();
            }
        });

        DurationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        DurationEditText.setText(i + " hr " + i1 + " min");
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Duration of Event");
                timePickerDialog.show();
            }
        });

        eventImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditEvent.this, UploadEventPicture.class);
                startActivity(i);
            }
        });

        showEventDetails();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEvent();
            }
        });

    }

    private void updateEvent() {

        int selectedAttendingMethodId = radioGroupAttendingMethod.getCheckedRadioButtonId();
        radioButtonAttendingMethodSelected = findViewById(selectedAttendingMethodId);

        String textEventName = nameEditText.getText().toString().trim();
        String textOverview = overviewEditText.getText().toString().trim();
        String textCategory = categorySpinner.getSelectedItem().toString();
        String textDate = dateEditText.getText().toString().trim();
        String textDuration = DurationEditText.getText().toString().trim();
        String textLanguage = languageSpinner.getSelectedItem().toString();
        String textAge = timeEditText.getText().toString().trim();
        String textLocation = locationEditText.getText().toString().trim();

                /*Ibtisam changes - 2-Extracting text from the selected radio button
                cannot be done before checking if any of the radio buttons has been selected*/

        String textAttendingMethod;


        if (TextUtils.isEmpty(textEventName)) {
            Toast.makeText(EditEvent.this, "Please enter event name", Toast.LENGTH_LONG).show();
            nameEditText.setError("Event Name is required");
            nameEditText.requestFocus();

        } else if (TextUtils.isEmpty(textOverview)) {
            Toast.makeText(EditEvent.this, "Please enter event overview", Toast.LENGTH_LONG).show();
            overviewEditText.setError("Event Overview is required");
            overviewEditText.requestFocus();

        } else if (TextUtils.isEmpty(textDate)) {
            Toast.makeText(EditEvent.this, "Please enter event date", Toast.LENGTH_LONG).show();
            dateEditText.setError("Event Date is required");
            dateEditText.requestFocus();

        }  else if (TextUtils.isEmpty(textDuration)) {
            Toast.makeText(EditEvent.this, "Please enter event duration", Toast.LENGTH_LONG).show();
            DurationEditText.setError("Event Duration is required");
            DurationEditText.requestFocus();


        }  else if (TextUtils.isEmpty(textAge)) {
            Toast.makeText(EditEvent.this, "Please enter the age", Toast.LENGTH_LONG).show();
            timeEditText.setError("Age is required");
            timeEditText.requestFocus();

        } else if (TextUtils.isEmpty(textLocation)) {
            Toast.makeText(EditEvent.this, "Please enter the location", Toast.LENGTH_LONG).show();
            locationEditText.setError("location is required");
            locationEditText.requestFocus();

        } else if (radioGroupAttendingMethod.getCheckedRadioButtonId()== -1) {
            Toast.makeText(EditEvent.this, "Please select attending method", Toast.LENGTH_LONG).show();
            radioButtonAttendingMethodSelected.setError("location is required");
            radioButtonAttendingMethodSelected.requestFocus();

        } else {

            textAttendingMethod = radioButtonAttendingMethodSelected.getText().toString();

            uploadData();
        }
    }

    public void uploadData(){

        String EventName = nameEditText.getText().toString().trim();
        String Overview = overviewEditText.getText().toString().trim();
        String Category = categorySpinner.getSelectedItem().toString();
        String Date = dateEditText.getText().toString().trim();
        String duration = DurationEditText.getText().toString().trim();
        String language = languageSpinner.getSelectedItem().toString();
        String Age = timeEditText.getText().toString().trim();
        String Location = locationEditText.getText().toString().trim();
        String AttendingMethod = radioButtonAttendingMethodSelected.getText().toString();


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Add Event");
        DataClass data = new DataClass(EventName, Overview, Date, duration, language, Age, Location, AttendingMethod ,Category, initiative , imageURL);

        databaseRef.child(EventName).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditEvent.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditEvent.this, FounderMainActivity.class);
                    startActivity(intent);
                }
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
   private void showEventDetails() {

       nameEditText.setText(name);
       overviewEditText.setText(overview);
       dateEditText.setText(date);
       DurationEditText.setText(duration);
       timeEditText.setText(time);
       locationEditText.setText(location);

       if(attendingMethod!=null) {
           if (attendingMethod.equals("Online")) {
               onlineRadioButton.setChecked(true);
           } else {
               inPersonRadioButton.setChecked(true);
           }
       }
       int position = adapter.getPosition(category);
       categorySpinner.setSelection(position);

       int position2 = adapter2.getPosition(language);
       languageSpinner.setSelection(position2);

   }

}
