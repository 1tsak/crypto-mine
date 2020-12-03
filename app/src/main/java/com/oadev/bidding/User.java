package com.oadev.bidding;

public class User {

    private int id;
    private String username, email, amount,phone;

    public User(int id, String username, String email, String amount,String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.amount = amount;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public String getAmount() {
        return amount;
    }

    public String getPhone() {
        return phone;
    }
}