package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    private Button regbutton;
    private EditText username ,userphonenumber ,userpassword;
    private TextView  twelcome,tlogin;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupUI();

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createaccount();
            }

        });


        tlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, MainActivity.class));
            }
        });
    }

    private void  setupUI(){

        username= findViewById(R.id.etname);
        userphonenumber= findViewById(R.id.etphonenumber);
        userpassword= findViewById(R.id.etpassword);

        regbutton = findViewById(R.id.button);
        twelcome= findViewById(R.id.tregistration);

        tlogin= findViewById(R.id.backlogin);
        loadingbar= new ProgressDialog(this);
    }

    private void createaccount() {

        String name = username.getText().toString().trim();
        String phone = userphonenumber.getText().toString().trim();
        String password = userpassword.getText().toString().trim();


        //conditions
        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Create Account");
            loadingbar.setMessage("Please wait, while we are checking the credentials.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
            loadingbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            validatephonenumber(name,phone,password);

        }
    }

    private void validatephonenumber(final String name, final String phone, final String password) {

         final DatabaseReference Rootref;
         Rootref = FirebaseDatabase.getInstance().getReference();

         //Gets a FirebaseDatabase instance for the specified URL, using the specified FirebaseApp
        // Gets a DatabaseReference for the database root node.

         Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 if (!(dataSnapshot.child("Users").child(phone).exists()))
                 {
                     //HashMap is a part of Java’s collection
                     //It stores the data in (Key, Value) pairs. To access a value one must know its key.

                     HashMap<String,Object> userdataMap = new HashMap<>();
                     userdataMap.put("PhoneNumber",phone);
                     userdataMap.put("Name",name);
                     userdataMap.put("Password",password);

                     Rootref.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){

                                 Toast.makeText(Registration.this,"Congratulations, your account has been created",Toast.LENGTH_SHORT).show();
                                 loadingbar.dismiss();
                                 startActivity(new Intent(Registration.this,MainActivity.class));
                             }
                             else {
                                 loadingbar.dismiss();
                                 Toast.makeText(Registration.this,"Network Error: Please try again after some time",Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }
                 else{

                     Toast.makeText(Registration.this,"This" + phone +" already exists.",Toast.LENGTH_SHORT).show();
                     loadingbar.dismiss();
                     Toast.makeText(Registration.this,"Please try again by using another phone number", Toast.LENGTH_SHORT).show();

                     startActivity(new Intent(Registration.this,Registration.class));
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

    }
}

