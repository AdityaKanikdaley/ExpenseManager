package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText user_name;
    ImageView icon;
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_name = findViewById(R.id.username_in);
        icon = findViewById(R.id.icon_head);
        sign_in = findViewById(R.id.sign_in);


        // icon toast
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"  Expense Manager \nBy Aditya Kanikdaley",Toast.LENGTH_LONG).show();
            }
        });


        //sign_in button & intent
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter User Name !",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra("USER", user_name.getText().toString());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome  " + user_name.getText().toString() + " !",Toast.LENGTH_LONG).show();


                }
            }
        });

    }//on create ends
}