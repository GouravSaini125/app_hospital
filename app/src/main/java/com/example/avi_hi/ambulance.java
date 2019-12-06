package com.example.avi_hi;

public class ambulance {

    public String email,name,hos_name,aadhar,ambulance_number,ambulance_reg_number,mobile,location,state;

    public ambulance(){

    }

    public ambulance(String string, String toString, String s, String email, String name, String hos_name, String aadhar, String ambulance_number, String ambulance_reg_number) {
        this.email = email;
        this.name = name;
        this.hos_name = hos_name;
        this.aadhar = aadhar;
        this.ambulance_number = ambulance_number;
        this.ambulance_reg_number = ambulance_reg_number;
        this.mobile = mobile;
        this.location = location;
        this.state = state;
    }
}
