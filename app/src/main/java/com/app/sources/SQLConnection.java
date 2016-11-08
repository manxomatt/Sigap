package com.app.sources;

/**
 * Created by blue on 24/10/16.
 */

public class SQLConnection {

    public static final String KEY_NO_KTP = "no_ktp";

    public static final String KEY_NAMA_LENGKAP = "nama_lengkap";

    public static final String KEY_NO_HP = "no_hp";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_ALAMAT_RUMAH = "alamat_rumah";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_KODE_VERIFIKASI = "kode_verifikasi";

    public static final String KEY_MAIL_NAME = "name";

    public static final String KEY_MAIL_EMAIL_FROM = "email_from";

    public static final String KEY_MAIL_EMAIL_TO = "email_to";

    public static final String KEY_MAIL_SUBJECT = "subject";

    public static final String KEY_MAIL_MESSAGE = "message";

    /**
     * IP Public
     * */
    private static final String URL_HOST = "http://110.50.84.171";

    /**
     * URL Bantuan Terdekat (Polisi)
     * */
    public static final String URL_BANTUAN_TERDEKAT_POLISI_SELECT = URL_HOST + "/sigap_android/show_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_INSERT = URL_HOST + "/sigap_android/insert_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_EDIT = URL_HOST + "/sigap_android/edit_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_UPDATE = URL_HOST + "/sigap_android/update_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_DELETE = URL_HOST + "/sigap_android/delete_help_police.php";

    /**
     * URL Login
     * */
    public static final String URL_LOGIN = URL_HOST + "/sigap_android/new_login.php";

    /**
     * URL Send Mail
     * */
    public static final String URL_MAIL_SEND_FORGET_PASSWORD = URL_HOST + "/sigap_android/send_mail.php";

    /**
     * URL Signup
     * */
    public static final String URL_SIGNUP_MEMBER = URL_HOST + "/sigap_android/signup_member.php";

    public static final String URL_SIGNUP_USER = URL_HOST + "/sigap_android/signup_user.php";

    /**
     * If login success
     * */
    public static final String LOGIN_SUCCESS = "success";

    /**
     * If signup success
     * */
    public static final String SIGNUP_SUCCESS = "success";

    /**
     * Key of shared preferences
     * */
    public static final String SHARED_PREFERENCE_ID_SIGNUP = "SignupActivity";

    public static final String SHARED_PREFERENCE_ID_LOGIN = "LoginActivity";

    /**
     * Check key loged in or not, signup or not
     * */
    public static final String SHARED_PREFERENCE_LOGIN = "login";
    public static final String SHARED_PREFERENCE_SIGNUP = "signup";

    /**
     * Store username login detected, signup detected
     * */
    public static final String SHARED_PREFERENCE_USERNAME = "username";
    public static final String SHARED_PREFERENCE_NO_KTP = "identity_number";
    public static final String SHARED_PREFERENCE_EMAIL = "email";

}
