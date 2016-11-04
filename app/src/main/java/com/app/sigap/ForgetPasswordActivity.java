package com.app.sigap;

import android.content.Context;
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
import com.app.sources.SQLConnection;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {

    /**
     * UI Reference
     * */
    private TextView label_title, label_email;
    private EditText text_email;
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
        String email = text_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email))
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
        label_email = (TextView) findViewById(R.id.label_email);
        text_email = (EditText) findViewById(R.id.text_email);
        button_back = (Button) findViewById(R.id.button_back);
        button_submit_forget_password = (Button) findViewById(R.id.button_submit_forget_password);

        /**
         * Set typeface
         * */
        Typeface typeface = Typeface.createFromAsset(
            getApplicationContext().getAssets(),
            "fonts/titillium_regular_webfont.ttf"
        );

        /**
         * Set custom fonts
         * */
        label_title.setTypeface(typeface);
        label_email.setTypeface(typeface);
        text_email.setTypeface(typeface);
        button_back.setTypeface(typeface);
        button_submit_forget_password.setTypeface(typeface);
    }

}
