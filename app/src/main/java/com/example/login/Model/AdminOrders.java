package com.example.login.Model;

public class AdminOrders {

    private String Name, PhoneNumber, Address, City, Date ,Time , State,ProductID ,AccountId,Category;

    public AdminOrders() {
    }

    public AdminOrders(String name, String phoneNumber, String address, String city, String date, String time, String state, String productID, String accountId, String category) {
        Name = name;
        PhoneNumber = phoneNumber;
        Address = address;
        City = city;
        Date = date;
        Time = time;
        State = state;
        ProductID = productID;
        AccountId = accountId;
        Category = category;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
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

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
