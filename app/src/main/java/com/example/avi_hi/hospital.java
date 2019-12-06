package com.example.avi_hi;

public class hospital {
    public String email,name,hos_name,aadhar,mobile,location,state;

    public hospital(){

    }

    public hospital(String s, String email, String name, String hos_name, String aadhar, String mobile, String location, String state) {
        this.email = email;
        this.hos_name = hos_name;
        this.name = name;
        this.aadhar = aadhar;
        this.mobile = mobile;
        this.location = location;
        this.state = state;
    }
}
