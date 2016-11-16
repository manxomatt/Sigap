package com.app.sigap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.sources.MainMenuIDE;
import com.app.sources.SQLConnection;
import com.app.sources.UserIDE;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {

    /**
     * UI Reference
     * */
    private TextView label_title, label_nomorktp, label_email;
    private EditText text_nomorktp, text_email;
    private Button button_back, button_submit_forget_password;
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

        setContentView(R.layout.activity_forget_password);

        setNomorKTP();

        setFont();

        /**
         * Set method of forget password activity
         * */
        text_email = (EditText) findViewById(R.id.text_email);

        Button button_back = (Button) findViewById(R.id.button_back);
        Button button_submit_forget_password = (Button) findViewById(R.id.button_submit_forget_password);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of forget password activity
                 * */
                finishAffinity();

                /**
                 * Back to login activity
                 * */
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        button_submit_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Send password API
                 * */
                SendRequestPassword();
            }
        });
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    private boolean isSpacing (String spacing)
    {
        return spacing.contains(" ");
    }

    private void RequestPassword (
        final String email
    )
    {
        /**
         * Buatkan Request Dalam bentuk String
         * */
        StringRequest stringRequest = new StringRequest
            (Request.Method.POST, SQLConnection.URL_MAIL_SEND_FORGET_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean cancel = false;
                        View focusView = null;

                        /**
                         * Jika Respon server gagal
                         * */
                        if(!response.equalsIgnoreCase(SQLConnection.LOGIN_SUCCESS))
                        {
                            text_email.setError("* email tidak valid");
                            focusView = text_email;
                            cancel = true;
                        }

                        if (cancel)
                        {
                            focusView.requestFocus();
                        }
                        else
                        {
                            /**
                             * Start login activity
                             * */
                            Intent intent = new Intent(
                                ForgetPasswordActivity.this, LoginActivity.class
                            );
                            startActivity(intent);

                            /**
                             * End of forget password activity
                             * */
                            finishAffinity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /**
                         * Tambahkan apa yang terjadi setelah Pesan Error muncul, alternatif
                         * */
                        error.printStackTrace();
                    }
                }
            ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                /**
                 * Tambahkan parameter email untuk password request
                 * */
                params.put(SQLConnection.KEY_MAIL_EMAIL_TO, email);

                /**
                 * Kembalikan Nilai parameter
                 * */
                return params;
            }
        };

        /**
         * Tambahkan Request String ke dalam Queue
         * */
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void SendRequestPassword ()
    {
        /**
         * Variables
         * */
        String nomorktp = text_nomorktp.getText().toString();
        String email = text_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(nomorktp))
        {
            text_nomorktp.setError("* harus diisi");
            focusView = text_nomorktp;
            cancel = true;
        }
        else if (TextUtils.isEmpty(email))
        {
            text_email.setError("* harus diisi");
            focusView = text_email;
            cancel = true;
        }
        else if (nomorktp.length() < UserIDE.length_nomorktp)
        {
            text_nomorktp.setError("* panjang nomor ktp harus " + UserIDE.length_nomorktp + " digit");
            focusView = text_nomorktp;
            cancel = true;
        }
        else if (isSpacing(nomorktp))
        {
            text_nomorktp.setError("* tidak boleh ada spasi");
            focusView = text_nomorktp;
            cancel = true;
        }
        else if (isSpacing(email))
        {
            text_email.setError("* tidak boleh ada spasi");
            focusView = text_email;
            cancel = true;
        }
        else if (!isEmailValid(email))
        {
            text_email.setError("* email salah");
            focusView = text_email;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            RequestPassword(email);
        }
    }

    @SuppressWarnings("")
    private void setFont()
    {
        label_title = (TextView) findViewById(R.id.label_title);
        label_nomorktp = (TextView) findViewById(R.id.label_nomorktp);
        label_email = (TextView) findViewById(R.id.label_email);

        text_nomorktp = (EditText) findViewById(R.id.text_nomorktp);
        text_email = (EditText) findViewById(R.id.text_email);

        button_back = (Button) findViewById(R.id.button_back);
        button_submit_forget_password = (Button) findViewById(R.id.button_submit_forget_password);

        /**
         * Set typeface
         * */
        Typeface typeface_regular = Typeface.createFromAsset(
            getApplicationContext().getAssets(),
            "fonts/titillium_regular_webfont.ttf"
        );
        Typeface typeface_semibold = Typeface.createFromAsset(
            getApplicationContext().getAssets(),
            "fonts/titillium-semibold-webfont.ttf"
        );

        /**
         * Set custom fonts
         * */
        label_title.setTypeface(typeface_semibold);
        label_nomorktp.setTypeface(typeface_semibold);
        label_email.setTypeface(typeface_semibold);

        text_nomorktp.setTypeface(typeface_regular);
        text_email.setTypeface(typeface_regular);

        button_back.setTypeface(typeface_regular);
        button_submit_forget_password.setTypeface(typeface_regular);
    }

    private void setMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(MainMenuIDE.pesan_account_nothing);
        builder.setPositiveButton(
            "Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /**
                     * Back to login
                     * */
                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);

                    /**
                     * End of main menu activity
                     * */
                    finishAffinity();
                }
            }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setNomorKTP ()
    {
        /**
         * Object
         * */
        text_nomorktp = (EditText) findViewById(R.id.text_nomorktp);
        text_email = (EditText) findViewById(R.id.text_email);

        /**
         * Buatkan sebuah shared preference
         * */
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(
                SQLConnection.SHARED_PREFERENCE_ID_LOGIN, Context.MODE_PRIVATE
        );

        String nomorktp;
        nomorktp = sharedPreferences.getString(SQLConnection.SHARED_PREFERENCE_NO_KTP, "");

        if (nomorktp.isEmpty() == true)
        {
            setMessage();
        }
        else
        {
            text_nomorktp.setText(nomorktp);
            text_nomorktp.setEnabled(false);

            text_email.requestFocus();
        }
    }

}
