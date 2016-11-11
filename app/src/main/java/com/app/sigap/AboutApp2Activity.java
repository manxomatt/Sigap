package com.app.sigap;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.master.MainMenuActivity;

public class AboutApp2Activity extends AppCompatActivity {

    private TextView text_app_name, text_app_version, text_app_copyright, text_app_license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_about_app2);

        /**
         * Config
         * */
        setFonts();
        /**
         * End of Config
         * */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of about app
         * */
        finishAffinity();

        /**
         * Launch main dashboard
         * */
        Intent intent = new Intent(AboutApp2Activity.this, MainMenuActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("")
    private void setFonts ()
    {
        text_app_name = (TextView) findViewById(R.id.text_app_name);
        text_app_version = (TextView) findViewById(R.id.text_app_version);
        text_app_copyright = (TextView) findViewById(R.id.text_app_copyright);
        text_app_license = (TextView) findViewById(R.id.text_app_license);

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
        text_app_name.setTypeface(typeface_semibold);
        text_app_version.setTypeface(typeface_regular);
        text_app_copyright.setTypeface(typeface_regular);
        text_app_license.setTypeface(typeface_semibold);
    }

}
