package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AdmincategoryActivity extends AppCompatActivity {

    private LinearLayout giftcards, birthdaygifts, anniversarygifts, teddygifts;
    private LinearLayout coffeemugsgifts, ringsgifts, earringsgifts, jewellerygifts;
    private LinearLayout hatsgifts, watchegifts, sunglassesgifts;
    private Button logout, checkorders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admincategory);
         setuupui();

        giftcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Giftcards");
                startActivity(intent);
            }
        });


        birthdaygifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Birthday Gifts");
                startActivity(intent);
            }
        });

        anniversarygifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Anniversary Gifts");
                startActivity(intent);
            }
        });

        teddygifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Teddy bears");
                startActivity(intent);
            }
        });

        coffeemugsgifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Coffee Mugs with photo");
                startActivity(intent);
            }
        });

        ringsgifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Rings");
                startActivity(intent);
            }
        });

        earringsgifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Earring");
                startActivity(intent);
            }
        });

        jewellerygifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Jewellery gifts");
                startActivity(intent);
            }
        });

        hatsgifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Hats");
                startActivity(intent);
            }
        });

        watchegifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Watches");
                startActivity(intent);
            }
        });

        sunglassesgifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdmincategoryActivity.this,AdminaddNewproductActivity.class);
                intent.putExtra("Category","Sunglasses");
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmincategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        checkorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmincategoryActivity.this,AdminNewOrderActivity.class));
            }
        });
    }

    private void setuupui() {
        giftcards = findViewById(R.id.giftcards);
        birthdaygifts = findViewById(R.id.birthdaygifts);
        anniversarygifts = findViewById(R.id.anniversarygifts);
        teddygifts = findViewById(R.id.teddygifts);

        ringsgifts = findViewById(R.id.ringsgifts);
        earringsgifts = findViewById(R.id.earringsgifts);
        coffeemugsgifts = findViewById(R.id.coffeemugsgifts);
        jewellerygifts = findViewById(R.id.jewellerygifts);

        hatsgifts = findViewById(R.id.hatsgifts);
        watchegifts = findViewById(R.id.watchegifts);
        sunglassesgifts = findViewById(R.id.sunglassesgifts);

        logout = findViewById(R.id.admin_logout_button);
        checkorders = findViewById(R.id.new_orders_button);



    }
}
