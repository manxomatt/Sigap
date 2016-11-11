package com.config;

import android.Manifest;

/**
 * Created by akhmey on 05/10/2016.
 */

public class Config {

    public static String BASE_URL = "http://ellmu.com/akhbra.panicbutton/rest";//"http://192.168.43.57/panicbutton/rest";//

    public static  String API_KEY = "999888666555444333222";
    public static  String REGISTER_URL = BASE_URL + "/register";
    public static  String INSERT_ALERT_URL = BASE_URL + "/alert/insert";
    public static  String INSERT_PIC_ALERT_URL = BASE_URL + "/alert/upload_pic_alert";
    public static  String VERIFY_PIN_URL = BASE_URL + "/register/pin_verify";
    public static  String INSERT_CONTACT_US = BASE_URL + "/contact_us/insert";
    public static  String UPDATE_PROFILE = BASE_URL + "/register/update_profile";
    public static  String GET_PROFILE = BASE_URL + "/register/get_profile";
    public static  String GET_PLACES_URL = BASE_URL + "/get_places";

    public static  String API_ID_SENDBIRD = "49E78664-03C1-444C-94F1-31D9A41C0388";

    public static  int MAX_CLICK_COUNT = 3;
    public static  String NOTE_MESSAGE = "Membutuhkan bantuan darurat";

    public static  int MY_PERMISSION_REQUEST_STORAGE = 100;
    // DO NOT EDIT THIS
    public static final int TAG_CAMERA = 9911;

    // DO NOT EDIT THIS
    public static final int TAG_PHOTO_PIC = 9912;

    // DO NOT EDIT THIS
    public static String SD_CARD_PATH = "sigap_photos/";

    public static int MAX_ITEM_UPLOAD_PHOTO = 5;

    // for map zoom
        public static final double city_lat = -6.222083861871745;
    public static final double city_lon = 106.81060552597046;
    //Lat:,Lng:

    // for permission android M (6.0)
    public static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static String[] ALL_REQUIRED_PERMISSION = {
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
}
