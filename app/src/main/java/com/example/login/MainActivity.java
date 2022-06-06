package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.Model.Users;
import com.example.login.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private EditText mphonenumber, mpassword;
    private TextView mregistration,text1;
    private Button mLoginButton;
    private ProgressDialog loadingbar;
    private String DBname = "Users";
    private TextView mnotadmin,madmin;
    private RelativeLayout main_relative_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mphonenumber = findViewById(R.id.phonenumber);
        mLoginButton = findViewById(R.id.button1);
        mpassword = findViewById(R.id.password);
        mregistration = findViewById(R.id.registration);
        loadingbar = new ProgressDialog(MainActivity.this);
        madmin = findViewById(R.id.admin);
        mnotadmin = findViewById(R.id.notadmin);
        main_relative_layout = findViewById(R.id.main_relative_layout);
        text1 = findViewById(R.id.text1);

        Paper.init(MainActivity.this);

        mregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginuser();
            }
        });

        madmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginButton.setText("Admin Login");
                main_relative_layout.setBackgroundResource(R.drawable.admin_bacground);
                mLoginButton.setBackgroundColor(getResources().getColor(R.color.pershant_blue));
                text1.setTextColor(Color.parseColor("#091B7A"));
                madmin.setVisibility(View.INVISIBLE);
                mnotadmin.setVisibility(View.VISIBLE);
                DBname = "Admins";

            }
        });

        mnotadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginButton.setText("Login");
                main_relative_layout.setBackgroundResource(R.drawable.login);
                mLoginButton.setBackgroundColor(Color.parseColor("#E91E63"));
                text1.setTextColor(Color.parseColor("#E91E63"));
                madmin.setVisibility(View.VISIBLE);
                mnotadmin.setVisibility(View.INVISIBLE);
                DBname= "Users";
            }
        });
    }

            private void loginuser() {

                String phone = mphonenumber.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {

                    loadingbar.setTitle("Login Account");
                    loadingbar.setMessage("Please wait, while we are checking the credentials.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    loadingbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    logintoaccount(phone,password);
                }

            }

            private void logintoaccount(final String phone , final String password) {

                 Paper.book().write(prevalent.UserPhonekey,phone);


                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference();


                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(DBname).child(phone).exists()) {

                            Users usersdata = dataSnapshot.child(DBname).child(phone).getValue(Users.class);
                            if (usersdata.getPhoneNumber().equals(phone)) {
                                if (usersdata.getPassword().equals(password)) {
                                    if (DBname.equals("Admins")) {
                                        Toast.makeText(MainActivity.this, "Admin logged in successfully...", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        mpassword.setText("");
                                        startActivity(new Intent(MainActivity.this, AdmincategoryActivity.class));
                                    } else if (DBname.equals("Users")) {
                                        Toast.makeText(MainActivity.this, "User logged in successfully...", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();

                                        Intent intent = (new Intent(MainActivity.this, Homepage.class));
                                        prevalent.currentOnlineUser=usersdata;
                                        mpassword.setText("");
                                        startActivity(intent);
                                    }
                                }else {
                                    loadingbar.dismiss();
                                    Toast.makeText(MainActivity.this, "Please enter the correct password.", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                loadingbar.dismiss();
                                Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();

                            }
                        }

                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }
}