package com.khaledmosharraf.twtms.utils;

public final class UrlConstants {
    public static final String USER_BASE = "/user";
    public static final String ADMIN_BASE = "/admin";
    public static final String INDEX = "/";
    public static final String DASHBOARD = "/dashboard";
    public static final class User {
        public static final String BASE = "/user";
        public static final String LIST = "/users";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class SubscriptionPayment {
        public static final String BASE = "/payment";
        public static final String LIST = "/payments";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class Grant {
        public static final String BASE = "/grant";
        public static final String LIST = "/grants";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class Deposit {
        public static final String BASE = "/deposit";
        public static final String LIST = "/deposits";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class Expense {
        public static final String BASE = "/expense";
        public static final String LIST = "/expenses";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class Bank {
        public static final String BASE = "/bank";
        public static final String LIST = "/banks";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class District {
        public static final String BASE = "/district";
        public static final String LIST = "/districts";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }
    public static final class Upazila {
        public static final String BASE = "/upazila";
        public static final String LIST = "/upazilas";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
    }



    // Create pages
    public static final String ABOUT = "/about";


    private UrlConstants() {
        // Private constructor to prevent instantiation
    }
}
