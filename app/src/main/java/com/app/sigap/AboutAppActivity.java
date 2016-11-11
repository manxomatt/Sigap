package com.app.sigap;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.master.MainMenuActivity;
import com.lib.font.FontsOverride;

public class AboutAppActivity extends AppCompatActivity {

    private ImageButton btn_close;
    private NestedScrollView nsv_0, nsv_1, nsv_2;
    private TabLayout tabs;
    private TextView txt_channel_name;
    private TextView officer_name_01, officer_name_02, officer_name_03, officer_name_04;
    private TextView officer_job_01, officer_job_02, officer_job_03, officer_job_04;
    private TextView dev_name_01, dev_name_02, dev_name_03, dev_name_04, dev_name_05;
    private TextView dev_job_01, dev_job_02, dev_job_03, dev_job_04, dev_job_05;
    private TextView designer_name_01, designer_name_02;
    private TextView designer_job_01, designer_job_02;
    private TextView office_01, office_02, office_03, office_04, office_05, office_06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_about_app);

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

        OpenTabs();
        /**
         * End of listener
         * */
    }

    private void Exit ()
    {
        btn_close = (ImageButton) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of about app activity
                 * */
                finishAffinity();

                /**
                 * Launch main dashboard
                 * */
                Intent intent = new Intent(AboutAppActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void OpenTabs ()
    {
        tabs = (TabLayout) findViewById(R.id.tabs);
        nsv_0 = (NestedScrollView) findViewById(R.id.nsv_0);
        nsv_1 = (NestedScrollView) findViewById(R.id.nsv_1);
        nsv_2 = (NestedScrollView) findViewById(R.id.nsv_2);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                int index;
                index = tab.getPosition();

                switch (index)
                {
                    case 1:
                        nsv_0.setVisibility(View.GONE);
                        nsv_1.setVisibility(View.VISIBLE);
                        nsv_2.setVisibility(View.GONE);
                        break;
                    case 2:
                        nsv_0.setVisibility(View.GONE);
                        nsv_1.setVisibility(View.GONE);
                        nsv_2.setVisibility(View.VISIBLE);
                        break;
                    default:
                        nsv_0.setVisibility(View.VISIBLE);
                        nsv_1.setVisibility(View.GONE);
                        nsv_2.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @SuppressWarnings("")
    private void setFonts ()
    {
        txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);
        officer_name_01 = (TextView) findViewById(R.id.officer_name_01);
        officer_job_01 = (TextView) findViewById(R.id.officer_job_01);
        officer_name_02 = (TextView) findViewById(R.id.officer_name_02);
        officer_job_02 = (TextView) findViewById(R.id.officer_job_02);
        officer_name_03 = (TextView) findViewById(R.id.officer_name_03);
        officer_job_03 = (TextView) findViewById(R.id.officer_job_03);
        officer_name_04 = (TextView) findViewById(R.id.officer_name_04);
        officer_job_04 = (TextView) findViewById(R.id.officer_job_04);
        dev_name_01 = (TextView) findViewById(R.id.dev_name_01);
        dev_job_01 = (TextView) findViewById(R.id.dev_job_01);
        dev_name_02 = (TextView) findViewById(R.id.dev_name_02);
        dev_job_02 = (TextView) findViewById(R.id.dev_job_02);
        dev_name_03 = (TextView) findViewById(R.id.dev_name_03);
        dev_job_03 = (TextView) findViewById(R.id.dev_job_03);
        dev_name_04 = (TextView) findViewById(R.id.dev_name_04);
        dev_job_04 = (TextView) findViewById(R.id.dev_job_04);
        dev_name_05 = (TextView) findViewById(R.id.dev_name_05);
        dev_job_05 = (TextView) findViewById(R.id.dev_job_05);
        designer_name_01 = (TextView) findViewById(R.id.designer_name_01);
        designer_job_01 = (TextView) findViewById(R.id.designer_job_01);
        designer_name_02 = (TextView) findViewById(R.id.designer_name_02);
        designer_job_02 = (TextView) findViewById(R.id.designer_job_02);
        office_01 = (TextView) findViewById(R.id.office_01);
        office_02 = (TextView) findViewById(R.id.office_02);
        office_03 = (TextView) findViewById(R.id.office_03);
        office_04 = (TextView) findViewById(R.id.office_04);
        office_05 = (TextView) findViewById(R.id.office_05);
        office_06 = (TextView) findViewById(R.id.office_06);

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
        txt_channel_name.setTypeface(typeface_regular);
        officer_name_01.setTypeface(typeface_semibold);
        officer_job_01.setTypeface(typeface_regular);
        officer_name_02.setTypeface(typeface_semibold);
        officer_job_02.setTypeface(typeface_regular);
        officer_name_03.setTypeface(typeface_semibold);
        officer_job_03.setTypeface(typeface_regular);
        officer_name_04.setTypeface(typeface_semibold);
        officer_job_04.setTypeface(typeface_regular);
        dev_name_01.setTypeface(typeface_semibold);
        dev_job_01.setTypeface(typeface_regular);
        dev_name_02.setTypeface(typeface_semibold);
        dev_job_02.setTypeface(typeface_regular);
        dev_name_03.setTypeface(typeface_semibold);
        dev_job_03.setTypeface(typeface_regular);
        dev_name_04.setTypeface(typeface_semibold);
        dev_job_04.setTypeface(typeface_regular);
        dev_name_05.setTypeface(typeface_semibold);
        dev_job_05.setTypeface(typeface_regular);
        designer_name_01.setTypeface(typeface_semibold);
        designer_job_01.setTypeface(typeface_regular);
        designer_name_02.setTypeface(typeface_semibold);
        designer_job_02.setTypeface(typeface_regular);
        office_01.setTypeface(typeface_semibold);
        office_02.setTypeface(typeface_regular);
        office_03.setTypeface(typeface_semibold);
        office_04.setTypeface(typeface_regular);
        office_05.setTypeface(typeface_semibold);
        office_06.setTypeface(typeface_regular);

        FontsOverride.setDefaultFont(AboutAppActivity.this);
    }

}
