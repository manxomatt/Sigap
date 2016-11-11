package com.app.sigap;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.master.MainMenuActivity;

public class BantuanTerdekatActivity extends AppCompatActivity {

    /**
     * UI Reference
     * */
    private ImageButton btn_close;

    private ImageView label_img_bantuan_terdekat_polisi;
    private ImageView label_img_bantuan_terdekat_rumah_sakit;
    private ImageView label_img_bantuan_terdekat_pemadam_kebakaran;
    private ImageView label_img_bantuan_terdekat_spbu;

    private TextView label_text_bantuan_terdekat_polisi;
    private TextView label_text_bantuan_terdekat_rumah_sakit;
    private TextView label_text_bantuan_terdekat_pemadam_kebakaran;
    private TextView label_text_bantuan_terdekat_spbu;
    private TextView txt_channel_name;
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

        setContentView(R.layout.activity_bantuan_terdekat);

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

        ClickPolisi();

        ClickRumahSakit();

        ClickPemadamKebakaran();

        ClickSPBU();
        /**
         * End of Listener
         * */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of bantuan terdekat activity
         * */
        finishAffinity();

        /**
         * Launch main dashboard
         * */
        Intent intent = new Intent(
            BantuanTerdekatActivity.this, MainMenuActivity.class
        );
        startActivity(intent);
    }

    private void ClickPemadamKebakaran ()
    {
        label_img_bantuan_terdekat_pemadam_kebakaran = (ImageView) findViewById(R.id.label_img_bantuan_terdekat_pemadam_kebakaran);
        label_text_bantuan_terdekat_pemadam_kebakaran = (TextView) findViewById(R.id.label_text_bantuan_terdekat_pemadam_kebakaran);

        label_img_bantuan_terdekat_pemadam_kebakaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat
                 * */
                finishAffinity();

                /**
                 * Launch data pemadam kebakaran
                 * */
                Intent intent = new Intent(BantuanTerdekatActivity.this, DamkarActivity.class);
                startActivity(intent);
            }
        });
        label_text_bantuan_terdekat_pemadam_kebakaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat
                 * */
                finishAffinity();

                /**
                 * Launch data pemadam kebakaran
                 * */
                Intent intent = new Intent(BantuanTerdekatActivity.this, DamkarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ClickPolisi ()
    {
        label_img_bantuan_terdekat_polisi = (ImageView) findViewById(R.id.label_img_bantuan_terdekat_polisi);
        label_text_bantuan_terdekat_polisi = (TextView) findViewById(R.id.label_text_bantuan_terdekat_polisi);

        label_img_bantuan_terdekat_polisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat
                 * */
                finishAffinity();

                /**
                 * Launch data bantuan terdekat polisi
                 * */
                Intent intent = new Intent(
                    BantuanTerdekatActivity.this,
                    PolisiActivity.class
                );
                startActivity(intent);
            }
        });
        label_text_bantuan_terdekat_polisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat
                 * */
                finishAffinity();

                /**
                 * Launch data bantuan terdekat polisi
                 * */
                Intent intent = new Intent(
                    BantuanTerdekatActivity.this,
                    PolisiActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void ClickRumahSakit ()
    {
        label_img_bantuan_terdekat_rumah_sakit = (ImageView) findViewById(R.id.label_img_bantuan_terdekat_rumah_sakit);
        label_text_bantuan_terdekat_rumah_sakit = (TextView) findViewById(R.id.label_text_bantuan_terdekat_rumah_sakit);

        label_img_bantuan_terdekat_rumah_sakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat activity
                 * */
                finishAffinity();

                /**
                 * Launch data rumah sakit
                 * */
                Intent intent = new Intent(
                    BantuanTerdekatActivity.this, RumahSakitActivity.class
                );
                startActivity(intent);
            }
        });
        label_text_bantuan_terdekat_rumah_sakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat activity
                 * */
                finishAffinity();

                /**
                 * Launch data rumah sakit
                 * */
                Intent intent = new Intent(
                    BantuanTerdekatActivity.this, RumahSakitActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void ClickSPBU ()
    {
        label_img_bantuan_terdekat_spbu = (ImageView) findViewById(R.id.label_img_bantuan_terdekat_spbu);
        label_text_bantuan_terdekat_spbu = (TextView) findViewById(R.id.label_text_bantuan_terdekat_spbu);

        label_img_bantuan_terdekat_spbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat
                 * */
                finishAffinity();

                /**
                 * Launch data spbu terdekat
                 * */
                Intent intent = new Intent(BantuanTerdekatActivity.this, SPBUActivity.class);
                startActivity(intent);
            }
        });
        label_text_bantuan_terdekat_spbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of bantuan terdekat
                 * */
                finishAffinity();

                /**
                 * Launch data spbu terdekat
                 * */
                Intent intent = new Intent(BantuanTerdekatActivity.this, SPBUActivity.class);
                startActivity(intent);
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
                 * End of bantuan terdekat activity
                 * */
                finishAffinity();

                /**
                 * Launch main menu activity
                 * */
                Intent intent = new Intent(BantuanTerdekatActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("")
    private void setFonts ()
    {
        label_text_bantuan_terdekat_polisi = (TextView) findViewById(R.id.label_text_bantuan_terdekat_polisi);
        label_text_bantuan_terdekat_rumah_sakit = (TextView) findViewById(R.id.label_text_bantuan_terdekat_rumah_sakit);
        label_text_bantuan_terdekat_pemadam_kebakaran = (TextView) findViewById(R.id.label_text_bantuan_terdekat_pemadam_kebakaran);
        label_text_bantuan_terdekat_spbu = (TextView) findViewById(R.id.label_text_bantuan_terdekat_spbu);
        txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);

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
        label_text_bantuan_terdekat_polisi.setTypeface(typeface);
        label_text_bantuan_terdekat_rumah_sakit.setTypeface(typeface);
        label_text_bantuan_terdekat_pemadam_kebakaran.setTypeface(typeface);
        label_text_bantuan_terdekat_spbu.setTypeface(typeface);
        txt_channel_name.setTypeface(typeface);
    }

}
