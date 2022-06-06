package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.login.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminUserProductsActivity extends AppCompatActivity {

    private TextView product_name_order, product_discription_order,Product_price_order;
    private ImageView product_image_order;
    private Button dispatched;
    private String product_Id,account_ID,order_State,Category_name;
    private DatabaseReference productRef;
    private DatabaseReference orederstateRef;
    private RelativeLayout orders_relative_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        setUpID();

        product_Id = getIntent().getStringExtra("product_id");
        account_ID = getIntent().getStringExtra("account_id");
        order_State = getIntent().getStringExtra("order_state");
        Category_name = getIntent().getStringExtra("category_product");


        productRef =FirebaseDatabase.getInstance().getReference().child("Products").child(Category_name);
        orederstateRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(account_ID+product_Id).child("State");

        get_ordered_product_descrription(product_Id);

        dispatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orederstateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orederstateRef.setValue("Order Dispatched..");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(AdminUserProductsActivity.this,AdminNewOrderActivity.class);
                startActivity(intent);
                finish();
            }

    });
    }

    private void get_ordered_product_descrription(String product_id) {


        productRef.child(product_Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists());
                {
                    products products = dataSnapshot.getValue(com.example.login.Model.products.class);

                    product_name_order.setText(products.getProduct_Name());
                    product_discription_order.setText(products.getDescription());
                    Product_price_order.setText("Price:- Rs." + products.getPrice());
                    Picasso.get().load(products.getImage()).into(product_image_order);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpID() {

        product_image_order = findViewById(R.id.product_image_order);
        product_name_order = findViewById(R.id.product_name_order);
        product_discription_order = findViewById(R.id.product_description_order);
        Product_price_order= findViewById(R.id.product_price_order);
        dispatched = findViewById(R.id.dispatched);
        orders_relative_layout = findViewById(R.id.orders_relative_layout);

    }
}
