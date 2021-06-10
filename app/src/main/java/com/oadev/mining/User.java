package com.oadev.mining;

public class User {

    private final int id;
    private final String username;
    private final String email;
    private final String amount;
    private final String phone;
    private final String refercode;
    private final String referedby;

    public User(int id, String username, String email, String amount, String phone, String refercode, String referedby) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.amount = amount;
        this.phone = phone;
        this.refercode = refercode;
        this.referedby = referedby;
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

    public String getRefercode() {
        return refercode;
    }

    public String getReferedby() {
        return referedby;
    }
}
