package com.app.sigap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.master.MainMenuActivity;
import com.app.sources.SQLConnection;

public class SplashScreen extends AppCompatActivity
{

    /**
     * Variable boolean for checking user login or not.
     * Set this var as false.
     * */
    private boolean login = false;

    /**
     * Variables
     * */
    private static int splash_interval = 5000;

    /**
     * UI Reference
     * */
    private TextView label_splash_app;
    private TextView label_splash_thanks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_splash_screen);

        setFont();

        onLoadDevice();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        onLoadDevice();
    }

    private void onLoadDevice ()
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences
                (
                        SQLConnection.SHARED_PREFERENCE_ID_LOGIN,
                        Context.MODE_PRIVATE
                );
        login = sharedPreferences.getBoolean
                (
                        SQLConnection.SHARED_PREFERENCE_LOGIN, false
                );

        if (login)
        {
            /**
             * Show main menu activity
             * */
            Intent intent = new Intent(SplashScreen.this, MainMenuActivity.class);
            startActivity(intent);

            /**
             * End of splash screen
             * */
            finishAffinity();
        }
        else
        {
            new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * Show login activity
                         * */
                        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(intent);

                        /**
                         * End of splash screen
                         * */
                        finishAffinity();
                    }
                }
            , splash_interval);
        }
    }

    @SuppressWarnings("")
    private void setFont()
    {
        label_splash_app = (TextView) findViewById(R.id.label_splash_app);
        label_splash_thanks = (TextView) findViewById(R.id.label_splash_thanks);

        /**
         * Set typeface
         * */
        Typeface typeface = Typeface.createFromAsset(
            getApplicationContext().getAssets(), "fonts/titillium_regular_webfont.ttf"
        );

        /**
         * Set custom fonts
         * */
        label_splash_app.setTypeface(typeface);
        label_splash_thanks.setTypeface(typeface);
    }

}
