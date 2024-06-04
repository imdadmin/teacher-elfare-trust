package com.khaledmosharraf.twtms.utils;

public final class UrlConstants {


    public static final String USER_BASE = "/user";
    public static final String ADMIN_BASE = "/admin";
    public static final class Common {
        public static final String INDEX = "/";
        public static final String USER_DASHBOARD = USER_BASE+"/dashboard";
        public static final String ADMIN_DASHBOARD = ADMIN_BASE+"/dashboard";
    }

    public static final class User {
        public static final String BASE = ADMIN_BASE+"/user";
        public static final String LIST = ADMIN_BASE+"/users";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";

    }
    public static final class SubscriptionPayment {
        public static final String BASE = ADMIN_BASE+"/payment";
        public static final String LIST = ADMIN_BASE+"/payments";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class Grant {
        public static final String BASE = ADMIN_BASE+"/grant";
        public static final String LIST = ADMIN_BASE+"/grants";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class Deposit {
        public static final String BASE = ADMIN_BASE+"/deposit";
        public static final String LIST = ADMIN_BASE+"/deposits";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class Expense {
        public static final String BASE = ADMIN_BASE+"/expense";
        public static final String LIST = ADMIN_BASE+"/expenses";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class Bank {
        public static final String BASE = ADMIN_BASE+"/bank";
        public static final String LIST = ADMIN_BASE+"/banks";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class District {
        public static final String BASE = ADMIN_BASE+"/district";
        public static final String LIST = ADMIN_BASE+"/districts";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class Upazila {
        public static final String BASE = ADMIN_BASE+"/upazila";
        public static final String LIST = ADMIN_BASE+"/upazilas";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    // Create pages
    public static final String ABOUT = "/about";

}
