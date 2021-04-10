package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_expense extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker, save;
    ImageView notes;
    EditText txtDate, txtTime, amount, desc;
    String date,time;

    int mYear, mMonth, mDay, mHour, mMinute;
    String data_exp;

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            txtDate.setText(date);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            time = hourOfDay + ":" + minute;
                            txtTime.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        notes = (ImageView)findViewById(R.id.pic_notes);
        save =(Button)findViewById(R.id.btn_save);
        amount = (EditText)findViewById(R.id.in_amount);
        desc = (EditText)findViewById(R.id.in_description);


        //receiving data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            data_exp = bundle.getString("USERExp");
        }


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent note = new Intent(add_expense.this,notes.class);
                startActivity(note);
            }
        });


        // realtime DB
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://expensemanager-7a0b6.firebaseio.com/" + data_exp);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtDate.getText().toString().isEmpty() || txtTime.getText().toString().isEmpty() || amount.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill Credentials",Toast.LENGTH_LONG).show();
                }
                else {
                    final String output = "(" + txtDate.getText().toString() + ") " + "(" + txtTime.getText().toString() + ") -> ₹" + amount.getText().toString()
                            + "\n" + desc.getText().toString();

                    message message = new message(output);
                    ref.push().setValue(message);
                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
                    txtTime.setText("");
                    txtDate.setText("");
                    amount.setText("");
                    desc.setText("");
                }
            }
        });

    } // ends On create
}