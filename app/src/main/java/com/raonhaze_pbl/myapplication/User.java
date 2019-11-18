package com.raonhaze_pbl.myapplication;

public class User {
    String userBarcode;
    String userTime;

    public String getUserBarcode() {
        return userBarcode;
    }

    public void setUserBarcode(String userBarcode) {
        this.userBarcode = userBarcode;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public User(String userBarcode, String userTime) {
        this.userBarcode = userBarcode;
        this.userTime = userTime;
    }
}
