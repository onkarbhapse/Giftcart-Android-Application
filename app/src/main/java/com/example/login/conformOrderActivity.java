package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Model.AdminOrders;
import com.example.login.Model.Users;
import com.example.login.Model.products;
import com.example.login.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class conformOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, homeaddessEditText, cityEditText;
    private Button confirmButton;
    private DatabaseReference orderRef;
    private String name,phone,address,city;
    private String mcurrentDate,mcurrentTime;
    private String orderID;
    private String Product_ID, account_id,Category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_order);

        setup_Ui_Id();

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        Product_ID = getIntent().getStringExtra("product_Id");
        Category = getIntent().getStringExtra("Cat_Name");
        account_id = prevalent.currentOnlineUser.getPhoneNumber();

       confirmButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             collecttheOrderInformation();
           }
       });


    }

    private void collecttheOrderInformation()
    {
         name = nameEditText.getText().toString().trim();
         phone =phoneEditText.getText().toString().trim();
         address=homeaddessEditText.getText().toString().trim();
         city=cityEditText.getText().toString().trim();

        if(name.isEmpty() || phone.isEmpty() || address.isEmpty()|| city.isEmpty())
        {
            Toast.makeText(conformOrderActivity.this,"Please fill all the details",Toast.LENGTH_SHORT).show();
        }else
        {
            storeProductInformation();
        }
    }

    private void storeProductInformation()
    {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM-dd-yyyy");
        mcurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        mcurrentTime = currentTime.format(calendar.getTime());




        HashMap<String, Object>orderMap = new HashMap<>();
        orderMap.put("Name",name);
        orderMap.put("PhoneNumber",phone);
        orderMap.put("Address",address);
        orderMap.put("City",city);
        orderMap.put("Date",mcurrentDate);
        orderMap.put("Time",mcurrentTime);
        orderMap.put("ProductID",Product_ID);
        orderMap.put("AccountId",account_id);
        orderMap.put("Category",Category);
        orderMap.put("State","Not shipped");

        orderRef.child(prevalent.currentOnlineUser.getPhoneNumber()+ Product_ID).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful())
                {
                    Toast.makeText(conformOrderActivity.this,"Your order has been confirmed..",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(conformOrderActivity.this,ThankYouActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(conformOrderActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void setup_Ui_Id() {

        confirmButton = findViewById(R.id.confirm_order_button);
        nameEditText = findViewById(R.id.shipment_name);
        phoneEditText = findViewById(R.id.shipment_phone);
        homeaddessEditText = findViewById(R.id.shipment_address);
        cityEditText = findViewById(R.id.shipment_city);


    }
}
