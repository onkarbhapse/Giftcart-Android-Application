package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.Model.products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Product_descriptinActivity extends AppCompatActivity {

    private Button proceed_to_buy;
    private TextView product_name, product_description, product_price,mproduct_id;
    private ImageView product_image;
    private String product_ID,pID,Category_Name;
    private String Cat_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_descriptin);

        setupId();
         product_ID = getIntent().getStringExtra("product_id");
         Category_Name = getIntent().getStringExtra("categoryName");



         get_product_descrription(product_ID);


         proceed_to_buy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent =new Intent(Product_descriptinActivity.this,conformOrderActivity.class);
                 intent.putExtra("product_Id",pID);
                 intent.putExtra("Cat_Name",Cat_Name);

                 startActivity(intent);
             }
         });

    }

    private void get_product_descrription(final String product_id)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(Category_Name);

        productRef.child(product_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                  if(dataSnapshot.exists());
                {

                    products products = dataSnapshot.getValue(com.example.login.Model.products.class);

                    pID = products.getProduct_ID();
                    Cat_Name = products.getCategory();

                    product_name.setText(products.getProduct_Name());
                    product_price.setText("Price :- Rs." + products.getPrice());
                    product_description.setText(products.getDescription());
                    mproduct_id.setText( products.getProduct_ID());
                    Picasso.get().load(products.getImage()).into(product_image);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupId() {

        proceed_to_buy = findViewById(R.id.proceed_to_buy);
        product_image=findViewById(R.id.product_image_description);
        product_name=findViewById(R.id.product_name_description);
        product_description=findViewById(R.id.product_description_description);
        product_price=findViewById(R.id.product_price_description);
        mproduct_id = findViewById(R.id.product_ID_description);

    }
}
