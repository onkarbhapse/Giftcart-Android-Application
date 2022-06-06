package com.example.login.Model;

public class products {

  private String Category, Date, Time, Description, Image, Price, Product_Name, Product_ID;

  public products(){


  }

    public products(String category, String date, String time, String description, String image, String price, String product_Name, String product_ID) {
        Category = category;
        Date = date;
        Time = time;
        Description = description;
        Image = image;
        Price = price;
        Product_Name = product_Name;
        Product_ID = product_ID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }
}
