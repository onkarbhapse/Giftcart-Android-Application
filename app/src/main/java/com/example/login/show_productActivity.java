package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.login.Model.products;
import com.example.login.ViewHolder.productViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class show_productActivity extends AppCompatActivity {


    private RecyclerView recycler_view;
    private DatabaseReference productRef;
    private String category_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);



        category_name = getIntent().getExtras().get("category").toString();


        productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(category_name);

        recycler_view = findViewById(R.id.recycler_view);

        recycler_view.setLayoutManager(new LinearLayoutManager(show_productActivity.this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>().setQuery(productRef,products.class).build();
        //configure the adapter by building FirebaseRecyclerOptions

        FirebaseRecyclerAdapter<products, productViewHolder> adapter = new FirebaseRecyclerAdapter<products, productViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull productViewHolder holder, int position, @NonNull final products model)
            {
                holder.txtProduct_name.setText(model.getProduct_Name());
                holder.txtProduct_price.setText("Price :- Rs." + model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(show_productActivity.this,Product_descriptinActivity.class);
                        intent.putExtra("product_id",model.getProduct_ID());
                        intent.putExtra("categoryName",model.getCategory());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                return new productViewHolder(view);
            }
        };
        recycler_view.setAdapter(adapter);
        adapter.startListening();

    }


}
