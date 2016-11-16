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
    public static final String URL_BANTUAN_TERDEKAT_POLISI = "http://110.50.84.171/sigap/sigap_android/kuncoro_crud/";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_SELECT = URL_HOST + "/sigap/sigap_android/show_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_INSERT = URL_HOST + "/sigap/sigap_android/insert_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_EDIT = URL_HOST + "/sigap/sigap_android/edit_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_UPDATE = URL_HOST + "/sigap/sigap_android/update_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_POLISI_DELETE = URL_HOST + "/sigap/sigap_android/delete_help_police.php";
    public static final String URL_BANTUAN_TERDEKAT_RS = "http://110.50.84.171/sigap/sigap_android/kuncoro_crud/";
    public static final String URL_BANTUAN_TERDEKAT_DAMKAR = "http://110.50.84.171/sigap/sigap_android/kuncoro_crud/";
    public static final String URL_BANTUAN_TERDEKAT_SPBU = "http://110.50.84.171/sigap/sigap_android/kuncoro_crud/";

    /**
     * URL Berita Polres
     * */
    public static final String URL_BERITA_POLRES = "http://tribratanewskalteng.com/index.php/editions-a-pricing/polres-barut";

    /**
     * URL Pelayanan Polres
     * */
    public static final String URL_PELAYANAN_POLRES_SKCK = "http://e-skck.info/e_skck/request/token/gAyd443";
    public static final String URL_PELAYANAN_POLRES_SP2HP = "http://e-sp2hp.info/e_sp2hp/";

    /**
     * URL select data kapolres
     * */
    public static final String URL_POLRES_SELECT_KAPOLRES = "http://110.50.84.171/sigap/sigap_android/kuncoro_crud/select_kapolres.php";

    /**
     * URL Registrasi SIM
     * */
    public static final String URL_REG_SIM = URL_HOST + "/sigap/sigap_android/insert_sim.php";
    public static final String REG_SIM_SUCCESS = "success";
    public static final String KEY_SIM_NOMORKTP = "nomorktp";
    public static final String KEY_SIM_JENISPERMOHONAN = "jenispermohonan";
    public static final String KEY_SIM_GOLONGANSIM = "golongansim";
    public static final String KEY_SIM_NAMADEPAN = "namadepan";
    public static final String KEY_SIM_NAMABELAKANG = "namabelakang";
    public static final String KEY_SIM_JENISKELAMIN = "jeniskelamin";
    public static final String KEY_SIM_KEWARGANEGARAAN = "kewarganegaraan";
    public static final String KEY_SIM_TINGGIBADAN = "tinggibadan";
    public static final String KEY_SIM_TEMPATLAHIR = "tempatlahir";
    public static final String KEY_SIM_TANGGALLAHIR = "tanggallahir";
    public static final String KEY_SIM_PEKERJAAN = "pekerjaan";
    public static final String KEY_SIM_ALAMATLENGKAP = "alamatlengkap";
    public static final String KEY_SIM_RTRW = "rtrw";
    public static final String KEY_SIM_KOTA = "kota";
    public static final String KEY_SIM_KODEPOS = "kodepos";
    public static final String KEY_SIM_NOMORTELEPON = "nomortelepon";
    public static final String KEY_SIM_NAMAAYAH = "namaayah";
    public static final String KEY_SIM_NAMAIBU = "namaibu";
    public static final String KEY_SIM_PENDIDIKAN = "pendidikan";
    public static final String KEY_SIM_CACATFISIKLAIN = "cacatfisiklain";

    /**
     * URL Login
     * */
    public static final String URL_LOGIN = URL_HOST + "/sigap/sigap_android/new_login.php";

    /**
     * URL Send Mail
     * */
    public static final String URL_MAIL_SEND_FORGET_PASSWORD = URL_HOST + "/sigap/sigap_android/send_mail.php";

    /**
     * URL Signup
     * */
    public static final String URL_SIGNUP_MEMBER = URL_HOST + "/sigap/sigap_android/signup_member.php";
    public static final String URL_SIGNUP_USER = URL_HOST + "/sigap/sigap_android/signup_user.php";

    /**
     * URL Pengaturan
     * */
    public static final String URL_PENGATURAN_CHANGEPASSWORD = URL_HOST + "/sigap/sigap_android/user_changepassword.php";

    /**
     * If login success
     * */
    public static final String LOGIN_FAILED = "username dan password salah.";
    public static final String LOGIN_SUCCESS = "success";

    /**
     * If signup success
     * */
    public static final String SIGNUP_KTP_FOUND = "nomor ktp sudah ada.";
    public static final String SIGNUP_FAILED = "gagal registrasi user.";
    public static final String SIGNUP_SUCCESS = "success";

    /**
     * If pengaturan success
     * */
    public static final String PENGATURAN_SUCCESS = "success";

    /**
     * Key of shared preferences
     * */
    public static final String SHARED_PREFERENCE_ID_SIGNUP = "SignupActivity";

    public static final String SHARED_PREFERENCE_ID_LOGIN = "LoginActivity";

    public static final String SHARED_PREFERENCE_ID_PENGATURAN = "PengaturanActivity";

    /**
     * Check key loged in or not, signup or not
     * */
    public static final String SHARED_PREFERENCE_LOGIN = "login";
    public static final String SHARED_PREFERENCE_SIGNUP = "signup";
    public static final String SHARED_PREFERENCE_PENGATURAN = "pengaturan";

    /**
     * Store username login detected, signup detected
     * */
    public static final String SHARED_PREFERENCE_USERNAME = "username";
    public static final String SHARED_PREFERENCE_PASSWORD =  "password";
    public static final String SHARED_PREFERENCE_NO_KTP = "identity_number";
    public static final String SHARED_PREFERENCE_EMAIL = "email";

}
