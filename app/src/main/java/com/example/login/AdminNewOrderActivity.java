package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.login.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    private RelativeLayout orders_relative_layout;
    private TextView state_order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);


        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orders_relative_layout = findViewById(R.id.orders_relative_layout);
        ordersList= findViewById(R.id.recycle_view_new_orders);
        ordersList.setLayoutManager(new LinearLayoutManager(AdminNewOrderActivity.this));




    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>().setQuery(ordersRef,AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position, @NonNull final AdminOrders model) {

                holder.userName.setText("Name: " + model.getName());
                holder.userShippingAdress.setText("Shipping Address: " + model.getAddress());
                holder.userCity.setText("City: " + model.getCity());
                holder.userDateTime.setText("Order at: " + model.getDate()+ " " + model.getTime());
                holder.userPhonenumber.setText("Phone: " + model.getPhoneNumber());
                holder.userProductID.setText("Product ID: " + model.getProductID());
                holder.userAccountID.setText("Account ID: " + model.getAccountId());
                holder.userAccountState.setText("Order State: " + model.getState());
                holder.usercategory.setText("Category: " + model.getCategory());

                holder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(AdminNewOrderActivity.this,AdminUserProductsActivity.class);
                        intent.putExtra("product_id",model.getProductID());
                        intent.putExtra("account_id",model.getAccountId());
                        intent.putExtra("order_state",model.getState());
                        intent.putExtra("category_product",model.getCategory());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.orders_layout,parent,false);
                return new AdminOrdersViewHolder(view);
            }
        };

        ordersList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView userName, userShippingAdress, userCity, userPhonenumber, userDateTime,userProductID,userAccountID,userAccountState,usercategory;
        public Button ShowOrderBtn;


        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_order);
            userPhonenumber = itemView.findViewById(R.id.user_phoneNumber_order);
            userCity = itemView.findViewById(R.id.city_order);
            userDateTime = itemView.findViewById(R.id.date_time_order);
            userShippingAdress = itemView.findViewById(R.id.address_order);
            ShowOrderBtn = itemView.findViewById(R.id.show_all_products_button);
            userProductID = itemView.findViewById(R.id.Product_id_order);
            userAccountID = itemView.findViewById(R.id.account_id_order);
            userAccountState = itemView.findViewById(R.id.state_order);
            usercategory = itemView.findViewById(R.id.category_order);

        }
    }
}
