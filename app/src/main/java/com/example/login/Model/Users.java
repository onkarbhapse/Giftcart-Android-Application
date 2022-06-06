package com.example.login.Model;

public class Users {

    private String Name, Password, PhoneNumber;

    public Users(){


    }

    public Users(String name, String password, String phoneNumber) {
        Name = name;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
