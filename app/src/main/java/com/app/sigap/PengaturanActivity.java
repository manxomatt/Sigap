package com.app.sigap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.master.MainMenuActivity;
import com.app.sources.LoginIDE;
import com.app.sources.MainMenuIDE;
import com.app.sources.SQLConnection;

import java.util.HashMap;
import java.util.Map;

public class PengaturanActivity extends AppCompatActivity {

    /**
     * UI reference
     * */
    private Button button_update_password;
    private EditText text_password_old, text_password_new, text_password_new_repeat;
    private ImageButton btn_close;
    private TextView label_gantipassword;
    /**
     * End of UI reference
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_pengaturan);

        /**
         * Config
         * */
        setFonts();
        /**
         * End of Config
         * */

        /**
         * Listener
         * */
        Exit();

        ClickUpdatePassword();
        /**
         * End of Listener
         * */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /**
         * End of pengaturan activity
         * */
        finishAffinity();

        /**
         * Launch main dashboard
         * */
        Intent intent = new Intent(PengaturanActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }

    private void ClickUpdatePassword ()
    {
        /**
         * Object
         * */
        button_update_password = (Button) findViewById(R.id.button_update_password);

        /**
         * Implement
         * */
        button_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Objects
                 * */
                text_password_old = (EditText) findViewById(R.id.text_password_old);
                text_password_new = (EditText) findViewById(R.id.text_password_new);
                text_password_new_repeat = (EditText) findViewById(R.id.text_password_new_repeat);

                /**
                 * Buatkan sebuah shared preference
                 * */
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(
                    SQLConnection.SHARED_PREFERENCE_ID_LOGIN, Context.MODE_PRIVATE
                );

                String username, sCheckOldPasword, message;
                String password_old, password_new, password_new_repeat;
                password_old = text_password_old.getText().toString();
                password_new = text_password_new.getText().toString();
                password_new_repeat = text_password_new_repeat.getText().toString();

                username = sharedPreferences.getString(SQLConnection.SHARED_PREFERENCE_USERNAME, "");
                sCheckOldPasword = sharedPreferences.getString(SQLConnection.SHARED_PREFERENCE_PASSWORD, "");

                if (password_old.isEmpty() == true || password_new.isEmpty() == true || password_new_repeat.isEmpty() == true)
                {
                    message = "Mohon lengkapi form dengan benar.";
                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (!password_old.equalsIgnoreCase(sCheckOldPasword))
                {
                    message = "Masukan password lama dengan benar." + "\n";
                    message = message + "Password lama : " + sCheckOldPasword;
                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (password_new.length() < LoginIDE.password_min_length)
                {
                    message = "Min. password baru 6 karakter.";
                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (password_new.equalsIgnoreCase(password_old) == true)
                {
                    message = "Mohon gunakan password yang berbeda dari password lama.";
                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (password_new_repeat.length() < LoginIDE.password_min_length)
                {
                    message = "Min. repeat new password is 6 characters.";
                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (!password_new_repeat.equalsIgnoreCase(password_new))
                {
                    message = "Ulangi password dengan benar.";
                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    UpdatePassword(username, password_new);
                }
            }
        });
    }

    private void Exit ()
    {
        btn_close = (ImageButton) findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pengaturan activity
                 * */
                finishAffinity();

                /**
                 * Launch main dashboard
                 * */
                Intent intent = new Intent(PengaturanActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("")
    private void setFonts ()
    {
        button_update_password = (Button) findViewById(R.id.button_update_password);
        text_password_old = (EditText) findViewById(R.id.text_password_old);
        text_password_new = (EditText) findViewById(R.id.text_password_new);
        text_password_new_repeat = (EditText) findViewById(R.id.text_password_new_repeat);
        label_gantipassword = (TextView) findViewById(R.id.label_gantipassword);

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
        label_gantipassword.setTypeface(typeface_semibold);
        text_password_old.setTypeface(typeface_regular);
        text_password_new.setTypeface(typeface_regular);
        text_password_new_repeat.setTypeface(typeface_regular);
        button_update_password.setTypeface(typeface_regular);
    }

    private void setMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(MainMenuIDE.pesan_update_password_success);
        builder.setPositiveButton(
            "Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /**
                     * Getting out
                     * */
                    SharedPreferences preferences = getSharedPreferences(
                        SQLConnection.SHARED_PREFERENCE_ID_LOGIN, Context.MODE_PRIVATE
                    );

                    /**
                     * Getting editor
                     */
                    SharedPreferences.Editor editor = preferences.edit();

                    /**
                     * Put login boolean as false
                     * */
                    editor.putBoolean(
                        SQLConnection.SHARED_PREFERENCE_LOGIN, false
                    );

                    /**
                     * Put username values
                     * */
                    editor.putString(
                        SQLConnection.SHARED_PREFERENCE_USERNAME, ""
                    );
                    editor.putString(
                        SQLConnection.SHARED_PREFERENCE_PASSWORD, ""
                    );

                    /**
                     * Save to shared preferences
                     * */
                    editor.commit();

                    /**
                     * Back to login
                     * */
                    Intent intent = new Intent(PengaturanActivity.this, LoginActivity.class);
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

    private void UpdatePassword (final String username, final String password)
    {
        /**
         * Set request method
         * */
        StringRequest stringRequest;
        stringRequest = new StringRequest
                (
                        Request.Method.POST,
                        SQLConnection.URL_PENGATURAN_CHANGEPASSWORD,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                String message;

                                /**
                                 * Jika respon gagal
                                 * */
                                if(!response.equalsIgnoreCase(SQLConnection.PENGATURAN_SUCCESS))
                                {
                                    message = MainMenuIDE.pesan_update_password_failed;
                                    Toast.makeText(PengaturanActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    SharedPreferences sharedPreferences;
                                    sharedPreferences = getSharedPreferences(
                                        SQLConnection.SHARED_PREFERENCE_ID_LOGIN, Context.MODE_PRIVATE
                                    );
                                    /**
                                     * Buatkan Sebuah variabel Editor Untuk penyimpanan Nilai shared preferences
                                     * */
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    /**
                                     * Tambahkan Nilai ke Editor
                                     * */
                                    editor.putBoolean(SQLConnection.SHARED_PREFERENCE_PENGATURAN, true);
                                    editor.putString(SQLConnection.SHARED_PREFERENCE_PASSWORD, password);

                                    /**
                                     * Simpan Nilai ke Variabel editor
                                     * */
                                    editor.commit();

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
                 * Set parameter to send database
                 * */
                Map<String,String> params = new HashMap<String, String>();
                params.put(SQLConnection.KEY_USERNAME, username);
                params.put(SQLConnection.KEY_PASSWORD, password);
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

}
