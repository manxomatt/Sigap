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

public class PelayananPolresActivity extends AppCompatActivity {

    /**
     * UI reference
     * */
    private ImageButton btn_close;
    private ImageView label_img_skck, label_img_sim, label_img_sp2hp;
    private TextView txt_channel_name;
    private TextView label_text_skck, label_text_sim, label_text_sp2hp;
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

        setContentView(R.layout.activity_pelayanan_polres);

        /**
         * Config
         * */
        setFonts();
        /**
         * End of config
         * */

        /**
         * Listener
         * */
        Exit();

        ClickSKCK();

        ClickSIM();

        ClickSP2HP();
        /**
         * End of listener
         * */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of pelayanan polres dashboard
         * */
        finishAffinity();

        /**
         * Launch main dashboard
         * */
        Intent intent = new Intent(PelayananPolresActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }

    private void ClickSIM ()
    {
        label_img_sim = (ImageView) findViewById(R.id.label_img_sim);
        label_text_sim = (TextView) findViewById(R.id.label_text_sim);

        label_img_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pelayanan polres activity
                 * */
                finishAffinity();

                /**
                 * Launch pelayanan sim
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, Sim1Activity.class);
                startActivity(intent);
            }
        });
        label_text_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pelayanan polres activity
                 * */
                finishAffinity();

                /**
                 * Launch pelayanan sim
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, Sim1Activity.class);
                startActivity(intent);
            }
        });
    }

    private void ClickSKCK ()
    {
        label_img_skck = (ImageView) findViewById(R.id.label_img_skck);
        label_text_skck = (TextView) findViewById(R.id.label_text_skck);

        label_img_skck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pelayanan polres activity
                 * */
                finishAffinity();

                /**
                 * Launch SKCK
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, SKCKActivity.class);
                startActivity(intent);
            }
        });
        label_text_skck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pelayanan polres activity
                 * */
                finishAffinity();

                /**
                 * Launch SKCK
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, SKCKActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ClickSP2HP ()
    {
        label_img_sp2hp = (ImageView) findViewById(R.id.label_img_sp2hp);
        label_text_sp2hp = (TextView) findViewById(R.id.label_text_sp2hp);

        label_img_sp2hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pelayanan polres activity
                 * */
                finishAffinity();

                /**
                 * Launch SP2HP
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, SP2HPActivity.class);
                startActivity(intent);
            }
        });
        label_text_sp2hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of pelayanan polres activity
                 * */
                finishAffinity();

                /**
                 * Launch SP2HP
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, SP2HPActivity.class);
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
                 * End of pelayanan polres dashboard
                 * */
                finishAffinity();

                /**
                 * Launch main dashboard
                 * */
                Intent intent = new Intent(PelayananPolresActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("")
    private void setFonts ()
    {
        txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);
        label_text_skck = (TextView) findViewById(R.id.label_text_skck);
        label_text_sim = (TextView) findViewById(R.id.label_text_sim);
        label_text_sp2hp = (TextView) findViewById(R.id.label_text_sp2hp);

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
        txt_channel_name.setTypeface(typeface);
        label_text_skck.setTypeface(typeface);
        label_text_sim.setTypeface(typeface);
        label_text_sp2hp.setTypeface(typeface);
    }

}
