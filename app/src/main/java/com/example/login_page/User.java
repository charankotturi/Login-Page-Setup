package com.example.login_page;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int user_id;

    private String first_name;

    private String last_name;

    @ColumnInfo(name = "email_id")
    private String email_id;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "number")
    private int mobile_number;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "Online")
    private Boolean online;

    public User(String first_name, String last_name, String email_id, String password, int mobile_number, String address, boolean online) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.password = password;
        this.mobile_number = mobile_number;
        this.address = address;
        this.online = online;
    }

    @Ignore
    public User(int user_id, String first_name, String last_name, String email_id, String password, int mobile_number, String address, boolean online) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.password = password;
        this.mobile_number = mobile_number;
        this.address = address;
        this.online = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email_id='" + email_id + '\'' +
                ", password='" + password + '\'' +
                ", mobile_number=" + mobile_number +
                ", address='" + address + '\'' +
                ", online=" + online +
                '}';
    }

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(int mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
