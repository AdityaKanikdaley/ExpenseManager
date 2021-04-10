package com.example.expensemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class allTransactions extends AppCompatActivity{

    Button b3;
    EditText e2;
    ListView l1;
    TextView tv;
    message msg;
    ArrayList<String> msglist;
    ArrayAdapter<String> adapter;
    String data;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);

        b3=(Button)findViewById(R.id.commit);
        e2=(EditText)findViewById(R.id.edit2);
        tv=(TextView)findViewById(R.id.tv_user);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
           data = bundle.getString("USERTrans");
           tv.setText(data);
        }


        msg = new message();
        l1=(ListView)findViewById(R.id.list1);
        msglist = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,R.layout.list_layout,R.id.text1,msglist);

        // retrieving data
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(data);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {
                msglist.clear();
                for(DataSnapshot ds:Snapshot.getChildren()){
                    msg= ds.getValue(message.class);
                    msglist.add(msg.getTrans());

                }l1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // realtime DB
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://expensemanager-7a0b6.firebaseio.com/" + data);


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(e2.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill amount !",Toast.LENGTH_LONG).show();
                }
                else{
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("(dd/MM/yyyy) (HH:mm:ss)");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now);
                String amount=e2.getText().toString();
                final String output_final = date + " -> ₹" + amount;

                message message = new message(output_final);
                ref.push().setValue(message);
                Toast.makeText(getApplicationContext(),"Saved!",Toast.LENGTH_LONG).show();
                e2.setText("");
                }
            }
        });
    } //on create ends
}







//        final ArrayList arrayList=new ArrayList<String>();
//        final ArrayAdapter adapter=new ArrayAdapter<String>(allTransactions.this, R.layout.list_layout,arrayList);

//        l1.setAdapter(adapter);

//        b3.setOnClickListener(new View.OnClickListener() { //save button from add income
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onClick(View view) {
//                if(e2.getText().toString().isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Fill amount !",Toast.LENGTH_LONG).show();
//                }
//                else{
//                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("(dd/MM/yyyy) (HH:mm:ss)");
//                    LocalDateTime now = LocalDateTime.now();
//                    String date = dtf.format(now);
//
//                    String amount=e2.getText().toString();
//                    String output_final = date+" ->  ₹"+amount;
//
//                    arrayList.add(output_final);  //date, time & amount
//                    adapter.notifyDataSetChanged();
//
//                }
//            }
//        });
