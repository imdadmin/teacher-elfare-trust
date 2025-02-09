package com.khaledmosharraf.twtms.utils;

public final class UrlConstants {


    public static final String USER_BASE = "/user";
    public static final String ADMIN_BASE = "/admin";
    public static final String TO_BASE = "/to";
    public static final class Common {
        public static final String INDEX = "/";
        public static final String USER_DASHBOARD = USER_BASE+"/dashboard";
        public static final String ADMIN_DASHBOARD = ADMIN_BASE+"/dashboard";
        public static final String TO_DASHBOARD = TO_BASE+"/dashboard";
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
    public static final class Yearlyfee {

        //TODO IMPORTANT: you must update config/UrlConstantsConfig.java class when adding new method.
        public static final String BASE = ADMIN_BASE+"/yearlyfee";
        public static final String LIST = ADMIN_BASE+"/yearlyfees";
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
    public static final class Report {
        public static final String BASE = ADMIN_BASE+"/report";
        public static final String GRANT = BASE + "/grant";
        public static final String PAYMENT = BASE + "/payment";
        public static final String MEMBER = BASE + "/member";
    }
    public static final class TOSubscriptionPayment {
        public static final String BASE = TO_BASE+"/payment";
        public static final String LIST = TO_BASE+"/payments";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class TOGrant {
        public static final String BASE = TO_BASE+"/grant";
        public static final String LIST = TO_BASE+"/grants";
        public static final String CREATE = BASE + "/create";
        public static final String UPDATE = BASE + "/update";
        public static final String DELETE = BASE + "/delete";
        public static final String VIEW = BASE + "/view";
    }
    public static final class TOReport {
        public static final String BASE = TO_BASE+"/report";
        public static final String GRANT = BASE + "/grant";
        public static final String PAYMENT = BASE + "/payment";
        public static final String MEMBER = BASE + "/member";
    }
    // Create pages
    public static final String ABOUT = "/about";

}
