package com.app.config;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.app.sigap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by blue on 29/10/16.
 */

public class SignVerificationConfig extends AppCompatActivity {

    public final void Setting(
        EditText text_kode_verifikasi, EditText text_username
    ) {
        text_kode_verifikasi = (EditText) findViewById(R.id.text_kode_verifikasi);
        text_username = (EditText) findViewById(R.id.text_username);

        Date date;
        date = new Date();

        String pattern = "Hms";

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat(pattern);

        String result;
        result = simpleDateFormat.format(date);

        text_kode_verifikasi.setText(result);

        text_kode_verifikasi.setFocusable(false);
        text_username.requestFocus();
    }

}
