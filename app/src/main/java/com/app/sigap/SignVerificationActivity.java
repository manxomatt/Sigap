package com.app.sigap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.config.SignVerificationConfig;
import com.app.master.MainMenuActivity;
import com.app.sources.MainMenuIDE;
import com.app.sources.MemberLog;
import com.app.sources.SQLConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignVerificationActivity extends AppCompatActivity {

    /**
     * UI Reference
     */
    private TextView label_kode_verifikasi;
    private TextView label_username;
    private TextView label_password;
    private TextView label_repeat_password;
    private EditText text_kode_verifikasi;
    private EditText text_username;
    private EditText text_password;
    private EditText text_repeat_password;
    private Button button_save;
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

        setContentView(R.layout.activity_sign_verification);

        setFont();

        /**
         * Set method for signup activity
         * */
        text_kode_verifikasi = (EditText) findViewById(R.id.text_kode_verifikasi);
        text_username = (EditText) findViewById(R.id.text_username);
        text_password = (EditText) findViewById(R.id.text_password);
        text_repeat_password = (EditText) findViewById(R.id.text_repeat_password);

        setCode();

        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateForm();
            }
        });
        /**
         * End of Set method for signup activity
         * */
    }

    private void SaveUserSignup(
        final String kode_verifikasi, final String username,  final String password
    )
    {
        /**
         * Set request method
         * */
        StringRequest stringRequest;
        stringRequest = new StringRequest
                (
                        Request.Method.POST,
                        SQLConnection.URL_SIGNUP_USER,
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
                                if(!response.equalsIgnoreCase(SQLConnection.SIGNUP_SUCCESS))
                                {
                                    text_username.setError("* username sudah ada");
                                    focusView = text_username;
                                    cancel = true;
                                }

                                if (cancel)
                                {
                                    focusView.requestFocus();
                                }
                                else
                                {
                                    setMessage();
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
                                //error.printStackTrace();
                            }
                        }
                ){
            protected Map<String, String> getParams()
            {
                /**
                 * Get parameter from memory options
                 * */
                String nomor_ktp = MemberLog.getNoKTP();
                String email = MemberLog.getEmail();

                /**
                 * Set parameter to send database
                 * */
                Map<String,String> params = new HashMap<String, String>();
                params.put(SQLConnection.KEY_NO_KTP, nomor_ktp);
                params.put(SQLConnection.KEY_USERNAME, username);
                params.put(SQLConnection.KEY_PASSWORD, password);
                params.put(SQLConnection.KEY_EMAIL, email);
                params.put(SQLConnection.KEY_KODE_VERIFIKASI, kode_verifikasi);
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

    private void ValidateForm ()
    {
        /**
         * Variables
         * */
        String kode_verifikasi = text_kode_verifikasi.getText().toString();
        String username = text_username.getText().toString();
        String password = text_password.getText().toString();
        String repeat_password = text_repeat_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(kode_verifikasi))
        {
            text_kode_verifikasi.setError("* harus diisi");
            focusView = text_kode_verifikasi;
            cancel = true;
        }
        else if (TextUtils.isEmpty(username))
        {
            text_username.setError("* harus diisi");
            focusView = text_username;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password))
        {
            text_password.setError("* harus diisi");
            focusView = text_password;
            cancel = true;
        }
        else if (password.length() < 6)
        {
            text_password.setError("* min password 6 karakter");
            focusView = text_password;
            cancel = true;
        }
        else if (TextUtils.isEmpty(repeat_password))
        {
            text_repeat_password.setError("* harus diisi");
            focusView = text_repeat_password;
            cancel = true;
        }
        else if (repeat_password.length() < 6)
        {
            text_repeat_password.setError("* min password 6 karakter");
            focusView = text_repeat_password;
            cancel = true;
        }
        else if (!repeat_password.equals(password))
        {
            text_repeat_password.setError("* password tidak sama");
            focusView = text_repeat_password;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            /**
             * Save user signup
             * */
            SaveUserSignup(kode_verifikasi, username, password);
        }
    }

    @SuppressWarnings("")
    private void setFont()
    {
        label_kode_verifikasi = (TextView) findViewById(R.id.label_kode_verifikasi);
        label_username = (TextView) findViewById(R.id.label_username);
        label_password = (TextView) findViewById(R.id.label_password);
        label_repeat_password = (TextView) findViewById(R.id.label_repeat_password);
        text_kode_verifikasi = (EditText) findViewById(R.id.text_kode_verifikasi);
        text_username = (EditText) findViewById(R.id.text_username);
        text_password = (EditText) findViewById(R.id.text_password);
        text_repeat_password = (EditText) findViewById(R.id.text_repeat_password);
        button_save = (Button) findViewById(R.id.button_save);

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
        label_kode_verifikasi.setTypeface(typeface);
        label_username.setTypeface(typeface);
        label_password.setTypeface(typeface);
        label_repeat_password.setTypeface(typeface);
        text_kode_verifikasi.setTypeface(typeface);
        text_username.setTypeface(typeface);
        text_password.setTypeface(typeface);
        text_repeat_password.setTypeface(typeface);
        button_save.setTypeface(typeface);
    }

    private void setCode()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH" + "mm" + "ss");
        String currentTime = sdf.format(new Date());

        text_kode_verifikasi = (EditText) findViewById(R.id.text_kode_verifikasi);
        text_kode_verifikasi.setEnabled(false);
        text_kode_verifikasi.setText(currentTime);

        text_username.requestFocus();
    }

    private void setMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(MainMenuIDE.pesan_signup_finished);
        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /**
                         * Start login activity
                         * */
                        Intent intent = new Intent(
                            SignVerificationActivity.this, LoginActivity.class
                        );
                        startActivity(intent);

                        /**
                         * End of sign verification activity
                         * */
                        finishAffinity();
                    }
                }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
