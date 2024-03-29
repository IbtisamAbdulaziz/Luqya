package com.example.luqya;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Objects;

public class AddEvent extends AppCompatActivity {

    DataClass dataClass;
    private ImageView imageView;
    private EditText name, overview,date, Duration, time, location;
    private Spinner categorySpinner, languageSpinner;
    private RadioGroup radioGroupAttendingMethod;
    private RadioButton radioButtonAttendingMethodSelected;

    private RadioButton Online , InPerson;

    private Button submit;
    private String imageURL, initiative, initiativeId;
    private Uri uri;
    private DatePickerDialog picker;
    private FirebaseAuth authProfile;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);

        getSupportActionBar().setTitle("Add Event");

        dataClass = new DataClass();

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.eventName);
        overview = findViewById(R.id.eventOverview);
        date = findViewById(R.id.eventDate);
        Duration = findViewById(R.id.eventDuration);
        time = findViewById(R.id.editEventTime);
        location = findViewById(R.id.eventLocation);
        //uploadPic = findViewById(R.id.button_upload_event_pic);
        Online = findViewById(R.id.onlineRB);
        InPerson = findViewById(R.id.inPersonRB);

        //Ibtisam's changes

        radioGroupAttendingMethod = findViewById(R.id.radioGroup);
        radioGroupAttendingMethod.clearCheck();


        //To implement categories spinner (list) with an array in strings.xml file

        categorySpinner = findViewById(R.id.eventCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(adapter);


        //To implement languages spinner (list) with an array in strings.xml file

        languageSpinner = findViewById(R.id.eventLanguage);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.languages,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        languageSpinner.setAdapter(adapter2);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEvent.this, UploadEventPicture.class);
                startActivity(intent);
            }
        });

        //To choose a date from a calender
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(AddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        time.setText(i + " : " + i1);
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Time of Event");
                timePickerDialog.show();
            }
        });

        Duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Duration.setText(i + " hr " + i1 + " min");
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Duration of Event");
                timePickerDialog.show();
            }
        });


        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered initiatives");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    initiative = readUserDetails.initiativeName;
                    initiativeId = firebaseUser.getUid();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddEvent.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


        submit = findViewById(R.id.submitEvent_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Ibtisam changes - 1-Finding which radio button has been selected.
                int selectedAttendingMethodId = radioGroupAttendingMethod.getCheckedRadioButtonId();
                radioButtonAttendingMethodSelected = findViewById(selectedAttendingMethodId);


                String textEventName = name.getText().toString().trim();
                String textOverview = overview.getText().toString().trim();
                String textCategory = categorySpinner.getSelectedItem().toString();
                String textDate = date.getText().toString().trim();
                String textDuration = Duration.getText().toString().trim();
                String textLanguage = languageSpinner.getSelectedItem().toString();
                String textAge = time.getText().toString().trim();
                String textLocation = location.getText().toString().trim();



                /*Ibtisam changes - 2-Extracting text from the selected radio button
                cannot be done before checking if any of the radio buttons has been selected*/

                String textAttendingMethod;


                if (TextUtils.isEmpty(textEventName)) {
                    Toast.makeText(AddEvent.this, "Please enter event name", Toast.LENGTH_LONG).show();
                    name.setError("Event Name is required");
                    name.requestFocus();

                } else if (TextUtils.isEmpty(textOverview)) {
                    Toast.makeText(AddEvent.this, "Please enter event overview", Toast.LENGTH_LONG).show();
                    overview.setError("Event Overview is required");
                    overview.requestFocus();

                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddEvent.this, "Please enter event date", Toast.LENGTH_LONG).show();
                    date.setError("Event Date is required");
                    date.requestFocus();

                } else if (TextUtils.isEmpty(textDuration)) {
                    Toast.makeText(AddEvent.this, "Please enter event duration", Toast.LENGTH_LONG).show();
                    Duration.setError("Event Duration is required");
                    Duration.requestFocus();


                } else if (TextUtils.isEmpty(textAge)) {
                    Toast.makeText(AddEvent.this, "Please enter the age", Toast.LENGTH_LONG).show();
                    time.setError("Age is required");
                    time.requestFocus();

                } else if (TextUtils.isEmpty(textLocation)) {
                    Toast.makeText(AddEvent.this, "Please enter the location", Toast.LENGTH_LONG).show();
                    location.setError("location is required");
                    location.requestFocus();

                } else if (radioGroupAttendingMethod.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddEvent.this, "Please select attending method", Toast.LENGTH_LONG).show();
                    radioButtonAttendingMethodSelected.setError("location is required");
                    radioButtonAttendingMethodSelected.requestFocus();

                } else {

                    //Ibtisam changes - 3-Extracting text from the selected radio button

                    textAttendingMethod = radioButtonAttendingMethodSelected.getText().toString();

                    uploadData();

                }

            }

        });


    }




    public void uploadData(){

        String EventName = name.getText().toString().trim();
        String Overview = overview.getText().toString().trim();
        String Category = categorySpinner.getSelectedItem().toString();
        String Date = date.getText().toString().trim();
        String duration = Duration.getText().toString().trim();
        String language = languageSpinner.getSelectedItem().toString();
        String Age = time.getText().toString().trim();
        String Location = location.getText().toString().trim();
        String AttendingMethod = radioButtonAttendingMethodSelected.getText().toString();


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Add Event");
        DataClass data = new DataClass(EventName, Overview, Date, duration, language, Age, Location, AttendingMethod ,Category, initiative , imageURL, initiativeId);

        databaseRef.child(EventName).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddEvent.this, "Saved", Toast.LENGTH_SHORT).show();
                    authProfile = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
                    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                    assert firebaseUser != null;
                    String userId = firebaseUser.getUid();
                    DocumentReference df  = fStore.collection("Users").document(userId);

                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (Objects.equals(documentSnapshot.getString("UserType"), "2")) {
                                Intent intent = new Intent(AddEvent.this, FounderMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(AddEvent.this, ViewEvents.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        // Store new event flag in shared preferences
        SharedPreferences preferences = getSharedPreferences("NewEvent", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isNewEventAdded", true);
        editor.putString("newEventName", EventName);
        editor.putString("newEventDate", Date);
        // ... (store other details as needed)
        editor.apply();

        Toast.makeText(AddEvent.this, "Saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddEvent.this, FounderMainActivity.class);
        startActivity(intent);
    }
}