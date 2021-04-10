package com.example.expensemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class notes extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    ImageView i1;
    Button b1;
    EditText e1;
    ListView l1;
    String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        i1 = (ImageView) findViewById(R.id.image1);
        b1 = (Button)findViewById(R.id.button1);
        l1=(ListView)findViewById(R.id.list1);
        e1=(EditText)findViewById(R.id.edit1);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        final ArrayList arrayList=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(notes.this, R.layout.list_layout,arrayList);
        l1.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() { //save button from add income
            @Override
            public void onClick(View view) {

                arrayList.add(txt);//date and time
                e1.setText("");
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if(resultCode == RESULT_OK && null!=data) {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    e1.setText(result.get(0));
                    txt=result.get(0);
                }
                break;
            }
        }
    }
}