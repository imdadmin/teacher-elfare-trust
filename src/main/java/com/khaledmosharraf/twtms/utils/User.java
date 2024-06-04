package com.khaledmosharraf.twtms.utils;

import org.springframework.stereotype.Component;

@Component
public  class User {
    public  String BASE = "/user";
    public  String LIST = "/users";
    public  String CREATE = BASE + "/create";
    public  String UPDATE = BASE + "/update";
    public  String DELETE = BASE + "/delete";
    public  String VIEW = BASE + "/view";
    public User(){

    }

    public String getBASE() {
        return BASE;
    }

    public String getLIST() {
        return LIST;
    }

    public String getCREATE() {
        return CREATE;
    }

    public String getUPDATE() {
        return UPDATE;
    }

    public String getDELETE() {
        return DELETE;
    }

    public String getVIEW() {
        return VIEW;
    }
}
