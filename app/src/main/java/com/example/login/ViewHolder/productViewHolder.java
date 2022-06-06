package com.example.login.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.interfaces.itemClickListner;

public class productViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProduct_name, txtProduct_description, txtProduct_price;
    public ImageView imageView;
    public itemClickListner listner;

    public productViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image_layout);
        txtProduct_name = itemView.findViewById(R.id.product_name_layout);
        txtProduct_price = itemView.findViewById(R.id.product_price_layout);
    }

    public  void setitemCliclListner(itemClickListner listner)
    {
        this.listner=listner;

    }

    @Override
    public void onClick(View view)
    {
      listner.onClick(view, getAdapterPosition(),false);
    }
}
