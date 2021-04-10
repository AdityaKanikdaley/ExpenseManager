package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button camera, trans, notes, expense;
    TextView calender, balance, show_balance;
    EditText in, ex;
    String dataMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.camera);
        trans = findViewById(R.id.allTransaction);
        notes = findViewById(R.id.notes);
        expense = findViewById(R.id.addExpense);
        calender = findViewById(R.id.calender);
        balance = findViewById(R.id.balance);
        in = findViewById(R.id.income);
        ex = findViewById(R.id.expense);
        show_balance = findViewById(R.id.show_balance);


        //receive user
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            dataMain = bundle.getString("USER");
        }


        //calender set value
        {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String date = "\nCurrent Date:\n" + df.format(c);
            calender.setText(date);
        }

        //setting balance
        show_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(in.getText().toString().isEmpty() || ex.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill both Income & Expense above !",Toast.LENGTH_LONG).show();
                }
                else {
                    float i = Float.parseFloat(in.getText().toString());
                    float e = Float.parseFloat(ex.getText().toString());
                    float bal = i - e;
                    String s = String.valueOf(bal);
                    balance.setText(s);
                }
            }
        });


        // camera button
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cam = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(cam);
            }
        });

        //all transactions button
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tr = new Intent(MainActivity.this,allTransactions.class);
                tr.putExtra("USERTrans", dataMain);
                startActivity(tr);
            }
        });

        //notes button
       notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent note = new Intent(MainActivity.this,notes.class);
                startActivity(note);
            }
        });

        //expense button
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ex = new Intent(MainActivity.this,add_expense.class);
                ex.putExtra("USERExp", dataMain);
                startActivity(ex);
            }
        });

    }
}