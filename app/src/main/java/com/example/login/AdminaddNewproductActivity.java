package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminaddNewproductActivity extends AppCompatActivity {

    private String categoryname , description , price , productname , saveCurrentDate, saveCurrentTime ;
    private ImageView add_product_images;
    private Button button_add_product;
    private EditText product_name,product_description,product_price;
    private static final int GalleryPic = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference productRef;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminadd_newproduct);
        setupui();

        categoryname= getIntent().getExtras().get("Category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("ProductImages");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);


        add_product_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opengallery();
            }
        });

        button_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateProductData();

            }
        });


    }

    private void Opengallery() {

        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPic);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPic && resultCode == RESULT_OK && data != null){

            ImageUri= data.getData();
            add_product_images.setImageURI(ImageUri);

        }

    }

    private void  ValidateProductData(){

        description = product_description.getText().toString().trim();
        productname = product_name.getText().toString().trim();
        price = product_price.getText().toString().trim();

        if(ImageUri == null){

            Toast.makeText(AdminaddNewproductActivity.this,"Product image is mandatory.",Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(description)){

            Toast.makeText(AdminaddNewproductActivity.this,"Please fill the product description.",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(productname)){

            Toast.makeText(AdminaddNewproductActivity.this,"Please fill the product name.",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(price)){

            Toast.makeText(AdminaddNewproductActivity.this,"Please fill the product price.",Toast.LENGTH_SHORT).show();
        }else{

            storeProductInformation();
        }

    }

    private void storeProductInformation() {

        loadingbar.setTitle("Adding New Product");
        loadingbar.setMessage("Please wait, while we are adding the new product.");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM-dd-yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate +"_"+ saveCurrentTime+"_"+categoryname;

        final StorageReference filepath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AdminaddNewproductActivity.this,"Error: " + message,Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminaddNewproductActivity.this,"Product Image uploaded successfully",Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(task.isSuccessful()){

                            downloadImageUrl = filepath.getDownloadUrl().toString();

                            //throw task.getException();
                        }

                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                      if(task.isSuccessful()){

                          downloadImageUrl = task.getResult().toString();

                          Toast.makeText(AdminaddNewproductActivity.this,"Product image save to Database Successfully.",Toast.LENGTH_SHORT).show();

                          SaveProductInfoToDatabase();

                      }

                    }
                });

            }
        });


    }

    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productmap = new HashMap<>();
        productmap.put("Product_ID",productRandomKey);
        productmap.put("Date",saveCurrentDate);
        productmap.put("Time",saveCurrentTime);
        productmap.put("Description",description);
        productmap.put("Image",downloadImageUrl);
        productmap.put("Category",categoryname);
        productmap.put("Price",price);
        productmap.put("Product_Name",productname);

        productRef.child(productRandomKey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    loadingbar.dismiss();
                    Toast.makeText(AdminaddNewproductActivity.this,"Product is added successfully...",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminaddNewproductActivity.this,AdmincategoryActivity.class));

                }else {
                    loadingbar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminaddNewproductActivity.this,"Error: " + message,Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void setupui() {

        add_product_images = findViewById(R.id.add_product_images);
        button_add_product = findViewById(R.id.button_add_product);
        product_name = findViewById(R.id.product_name);
        product_description = findViewById(R.id.product_description);
        product_price= findViewById(R.id.product_price);
        loadingbar = new ProgressDialog(AdminaddNewproductActivity.this);
    }
}
