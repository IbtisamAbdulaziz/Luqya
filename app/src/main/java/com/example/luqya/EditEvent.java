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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditEvent extends AppCompatActivity {

    private ImageView eventImageView;
    private EditText overviewEditText ,dateEditText, DurationEditText, timeEditText, locationEditText;
    private TextView eventNameTextView;
    private Spinner categorySpinner, languageSpinner;
    private RadioGroup radioGroupAttendingMethod;
    private RadioButton radioButtonAttendingMethodSelected;

    private Button updateButton;
    private String eventName, eventDescription, eventDate, eventDuration, eventTime, eventLocation,
            eventCategory, eventAttendingMethod, eventLanguage, imageURL, initiative, originalEventName;

    private Uri uri;
    private RadioButton onlineRadioButton, inPersonRadioButton;
    private DatePickerDialog picker;
    private FirebaseAuth authProfile;
    private DataClass dataClass;
    private ArrayAdapter<CharSequence> adapter;
    private  ArrayAdapter<CharSequence> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        getSupportActionBar().setTitle("Update Event");

        Bundle bundle = getIntent().getExtras();
        eventName = bundle.getString("eventName");
        eventDescription = bundle.getString("eventOverview");
        eventDate = bundle.getString("eventDate");
        eventDuration = bundle.getString("eventDuration");
        eventTime = bundle.getString("eventTime");
        eventLocation = bundle.getString("eventLocation");
        eventCategory = bundle.getString("eventCategory");
        eventLanguage = bundle.getString("eventLanguage");
        eventAttendingMethod = bundle.getString("eventAttendingMethod");
        initiative = bundle.getString("eventInitiative");



        dataClass = new DataClass();

        eventImageView = findViewById(R.id.imageView);
        eventNameTextView = findViewById(R.id.eventName);
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

        eventName = eventNameTextView.getText().toString().trim();
        eventDescription = overviewEditText.getText().toString().trim();
        eventCategory = categorySpinner.getSelectedItem().toString();
        eventDate = dateEditText.getText().toString().trim();
        eventDuration = DurationEditText.getText().toString().trim();
        eventLanguage = languageSpinner.getSelectedItem().toString();
        eventTime = timeEditText.getText().toString().trim();
        eventLocation = locationEditText.getText().toString().trim();


        if (TextUtils.isEmpty(eventDescription)) {
            Toast.makeText(EditEvent.this, "Please enter event overview", Toast.LENGTH_LONG).show();
            overviewEditText.setError("Event Overview is required");
            overviewEditText.requestFocus();

        } else if (TextUtils.isEmpty(eventDate)) {
            Toast.makeText(EditEvent.this, "Please enter event date", Toast.LENGTH_LONG).show();
            dateEditText.setError("Event Date is required");
            dateEditText.requestFocus();

        }  else if (TextUtils.isEmpty(eventDuration)) {
            Toast.makeText(EditEvent.this, "Please enter event duration", Toast.LENGTH_LONG).show();
            DurationEditText.setError("Event Duration is required");
            DurationEditText.requestFocus();


        }  else if (TextUtils.isEmpty(eventTime)) {
            Toast.makeText(EditEvent.this, "Please enter the age", Toast.LENGTH_LONG).show();
            timeEditText.setError("Age is required");
            timeEditText.requestFocus();

        } else if (TextUtils.isEmpty(eventLocation)) {
            Toast.makeText(EditEvent.this, "Please enter the location", Toast.LENGTH_LONG).show();
            locationEditText.setError("location is required");
            locationEditText.requestFocus();

        } else if (radioGroupAttendingMethod.getCheckedRadioButtonId()== -1) {
            Toast.makeText(EditEvent.this, "Please select attending method", Toast.LENGTH_LONG).show();
            radioButtonAttendingMethodSelected.setError("location is required");
            radioButtonAttendingMethodSelected.requestFocus();

        } else {

            eventAttendingMethod = radioButtonAttendingMethodSelected.getText().toString();

            uploadData();
        }
    }

    public void uploadData(){

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Add Event");
        DataClass data = new DataClass(eventName, eventDescription, eventDate, eventDuration, eventLanguage,
                eventTime, eventLocation, eventAttendingMethod ,eventCategory, initiative , imageURL);

        databaseRef.child(eventName).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditEvent.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditEvent.this, FounderMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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

       eventNameTextView.setText(eventName);
       overviewEditText.setText(eventDescription);
       dateEditText.setText(eventDate);
       DurationEditText.setText(eventDuration);
       timeEditText.setText(eventTime);
       locationEditText.setText(eventLocation);

       if(eventAttendingMethod!=null) {
           if (eventAttendingMethod.equals("Online")) {
               onlineRadioButton.setChecked(true);
           } else {
               inPersonRadioButton.setChecked(true);
           }
       }
       int position = adapter.getPosition(eventCategory);
       categorySpinner.setSelection(position);

       int position2 = adapter2.getPosition(eventLanguage);
       languageSpinner.setSelection(position2);

   }

}
