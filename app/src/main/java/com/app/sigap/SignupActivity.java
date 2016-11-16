package com.app.sigap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.master.MainMenuActivity;
import com.app.sources.MemberLog;
import com.app.sources.SQLConnection;
import com.app.sources.UserIDE;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    /**
     * UI Reference
     */
    private TextView label_nomor_ktp;
    private TextView label_nama_lengkap;
    private TextView label_no_hp;
    private TextView label_email;
    private TextView label_alamat_rumah;
    private EditText text_nomor_ktp;
    private EditText text_nama_lengkap;
    private EditText text_no_hp;
    private EditText text_email;
    private EditText text_alamat_rumah;
    private Button button_back_login;
    private Button button_save_signup;
    /**
     * End of UI Reference
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_signup);

        setFont();

        /**
         * Set method for signup activity
         * */
        text_nomor_ktp = (EditText) findViewById(R.id.text_nomor_ktp);
        text_nama_lengkap = (EditText) findViewById(R.id.text_nama_lengkap);
        text_no_hp = (EditText) findViewById(R.id.text_no_hp);
        text_email = (EditText) findViewById(R.id.text_email);
        text_alamat_rumah = (EditText) findViewById(R.id.text_alamat_rumah);

        Button button_save_signup = (Button) findViewById(R.id.button_save_signup);
        button_save_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateForm();
            }
        });

        Button button_back_login = (Button) findViewById(R.id.button_back_login);
        button_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * End of signup activity
                 * */
                finishAffinity();

                /**
                 * Back to login activity
                 * */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        /**
         * End of Set method for signup activity
         * */
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    private boolean isSpacing(String spacing)
    {
        return spacing.contains(" ");
    }

    private void ValidateForm()
    {
        /**
         * Variables
         * */
        final String nomor_ktp = text_nomor_ktp.getText().toString();
        final String nama_lengkap = text_nama_lengkap.getText().toString();
        final String nomor_hp = text_no_hp.getText().toString();
        final String email = text_email.getText().toString();
        final String alamat_rumah = text_alamat_rumah.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(nomor_ktp))
        {
            text_nomor_ktp.setError("* harus diisi");
            focusView = text_nomor_ktp;
            cancel = true;
        }
        else if (nomor_ktp.length() < UserIDE.length_nomorktp)
        {
            text_nomor_ktp.setError("* panjang nomor ktp harus " + UserIDE.length_nomorktp + " digit");
            focusView = text_nomor_ktp;
            cancel = true;
        }
        else if (TextUtils.isEmpty(nama_lengkap))
        {
            text_nama_lengkap.setError("* harus diisi");
            focusView = text_nama_lengkap;
            cancel = true;
        }
        else if (TextUtils.isEmpty(nomor_hp))
        {
            text_no_hp.setError("* harus diisi");
            focusView = text_no_hp;
            cancel = true;
        }
        else if (TextUtils.isEmpty(email))
        {
            text_email.setError("* harus diisi");
            focusView = text_email;
            cancel = true;
        }
        else if (!isEmailValid(email))
        {
            text_email.setError("* email salah");
            focusView = text_email;
            cancel = true;
        }
        else if (isSpacing(email))
        {
            text_email.setError("* tidak boleh ada spasi");
            focusView = text_email;
            cancel = true;
        }
        else if (TextUtils.isEmpty(alamat_rumah))
        {
            text_alamat_rumah.setError("* harus diisi");
            focusView = text_alamat_rumah;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            /**
             * Save new member
             * */
            SaveNewMember(nomor_ktp, nama_lengkap, nomor_hp, email, alamat_rumah);
        }
    }

    private void SaveNewMember(
        final String nomor_ktp, final String nama_lengkap,
        final String nomor_hp, final String email, final String alamat_rumah
    )
    {
        /**
         * Set request method
         * */
        StringRequest stringRequest;
        stringRequest = new StringRequest
        (
            Request.Method.POST,
            SQLConnection.URL_SIGNUP_MEMBER,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    boolean cancel = false;
                    View focusView = null;

                    /**
                     * Jika respon gagal
                     * */
                    if (response.equalsIgnoreCase(SQLConnection.SIGNUP_KTP_FOUND))
                    {
                        text_nomor_ktp.setError("* " + SQLConnection.SIGNUP_KTP_FOUND);
                        focusView = text_nomor_ktp;
                        cancel = true;
                    }
                    else if(response.equalsIgnoreCase(SQLConnection.SIGNUP_FAILED))
                    {
                        text_nomor_ktp.setError("* " + SQLConnection.SIGNUP_FAILED);
                        focusView = text_nomor_ktp;
                        cancel = true;
                    }

                    if (cancel)
                    {
                        focusView.requestFocus();
                    }
                    else
                    {
                        /**
                         * Buatkan sebuah shared preference
                         * */
                        SharedPreferences sharedPreferences;
                        sharedPreferences = SignupActivity.this.getSharedPreferences(
                            SQLConnection.SHARED_PREFERENCE_ID_LOGIN, Context.MODE_PRIVATE
                        );

                        /**
                         * Buatkan Sebuah variabel Editor Untuk penyimpanan Nilai shared preferences
                         * */
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        /**
                         * Simpan Nilai ke Variabel editor
                         * */
                        editor.commit();

                        /**
                         * Tambahkan Nilai ke Editor
                         * */
                        editor.putString(SQLConnection.SHARED_PREFERENCE_NO_KTP, nomor_ktp);

                        /**
                         * Set variables into memory options
                         * */
                        MemberLog.setNoKTP(nomor_ktp);
                        MemberLog.setNamaLengkap(nama_lengkap);
                        MemberLog.setEmail(email);

                        /**
                         * Start sign verification
                         * */
                        Intent intent = new Intent(
                            SignupActivity.this, SignVerificationActivity.class
                        );
                        startActivity(intent);

                        /**
                         * End of signup activity
                         * */
                        finishAffinity();
                    }
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    /**
                     * Tambahkan apa yang terjadi setelah Pesan Error muncul, alternatif
                     * */
                    error.printStackTrace();
                }
            }
        ){
            protected Map<String, String> getParams()
            {
                /**
                 * Set parameter to send database
                 * */
                Map<String,String> params = new HashMap<String, String>();
                params.put(SQLConnection.KEY_NO_KTP, nomor_ktp);
                params.put(SQLConnection.KEY_NAMA_LENGKAP, nama_lengkap);
                params.put(SQLConnection.KEY_NO_HP, nomor_hp);
                params.put(SQLConnection.KEY_EMAIL, email);
                params.put(SQLConnection.KEY_ALAMAT_RUMAH, alamat_rumah);
                return params;
            }
        };

        /**
         * Set volley request
         * */
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @SuppressWarnings("")
    private void setFont()
    {
        label_nomor_ktp = (TextView) findViewById(R.id.label_nomor_ktp);
        label_nama_lengkap = (TextView) findViewById(R.id.label_nama_lengkap);
        label_no_hp = (TextView) findViewById(R.id.label_no_hp);
        label_email = (TextView) findViewById(R.id.label_email);
        label_alamat_rumah = (TextView) findViewById(R.id.label_alamat_rumah);
        text_nomor_ktp = (EditText) findViewById(R.id.text_nomor_ktp);
        text_nama_lengkap = (EditText) findViewById(R.id.text_nama_lengkap);
        text_no_hp = (EditText) findViewById(R.id.text_no_hp);
        text_email = (EditText) findViewById(R.id.text_email);
        text_alamat_rumah = (EditText) findViewById(R.id.text_alamat_rumah);
        button_back_login = (Button) findViewById(R.id.button_back_login);
        button_save_signup = (Button) findViewById(R.id.button_save_signup);

        /**
         * Set typeface
         * */
        Typeface typeface = Typeface.createFromAsset(
            getApplicationContext().getAssets(),
            "fonts/titillium_regular_webfont.ttf"
        );

        /**
         * Set cusom fonts
         * */
        label_nomor_ktp.setTypeface(typeface);
        label_nama_lengkap.setTypeface(typeface);
        label_no_hp.setTypeface(typeface);
        label_email.setTypeface(typeface);
        label_alamat_rumah.setTypeface(typeface);
        text_nomor_ktp.setTypeface(typeface);
        text_nama_lengkap.setTypeface(typeface);
        text_no_hp.setTypeface(typeface);
        text_email.setTypeface(typeface);
        text_alamat_rumah.setTypeface(typeface);
        button_back_login.setTypeface(typeface);
        button_save_signup.setTypeface(typeface);
    }

}
