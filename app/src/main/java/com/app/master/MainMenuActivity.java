package com.app.master;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sigap.AboutApp2Activity;
import com.app.sigap.BantuanTerdekatActivity;
import com.app.sigap.BeritaPolresActivity;
import com.app.sigap.LiveChatActivity;
import com.app.sigap.LoginActivity;
import com.app.sigap.PanicShotActivity;
import com.app.sigap.PelayananPolresActivity;
import com.app.sigap.PengaturanActivity;
import com.app.sigap.R;
import com.app.sigap.TentangPolresActivity;
import com.app.sources.MainMenuIDE;
import com.app.sources.SQLConnection;
import com.lib.font.CustomTypefaceSpan;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, android.location.LocationListener {

    /**
     * UI Reference
     * */
    private TextView label_button_panic_text;
    private TextView label_button_promoter_text;
    private TextView label_button_sherif_text;
    private TextView label_button_police_text;
    private TextView label_footer_memo;
    private TextView toolbar_text;

    private TextView label_police_name;
    private TextView label_police_call_center;
    private TextView label_police_address;

    private ImageView label_button_promoter_img;
    private ImageView label_button_sherif_img;
    private ImageView label_button_police_img;
    private ImageView label_button_panic_img;

    private MenuItem nav_menu_informasi;
    private MenuItem nav_menu_aplikasi;

    /**
     * End of ui reference
     * */

    /**
     * Variables
     * */
    private static final int TAG_CODE_PERMISSION_LOCATION = 1945;
    private TapPanicState tapPanicState;
    private double latitude;
    private double longitude;
    /**
     * End of Variables
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_main_menu);

        setFontOnBody();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
        (
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * Dashboard listener
         * */
        tapPanicState = new TapPanicState();

        setupPanicButtonListener();

        ClickLiveChat();

        ClickBantuanTerdekat();

        ClickPelayananPolres();
        /**
         * End of Dashboard listener
         * */
    }

    private void setupPanicButtonListener() {
        label_button_panic_img = (ImageView) findViewById(R.id.label_button_panic_img);

        label_button_panic_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tapPanicState.maxTapReached()) {
                    tapPanicState.increase();

                    String message = String.format(getResources().getString(R.string.panic_button_tap_counter_needed),
                            tapPanicState.getRemainingTap());

                    Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_SHORT) .show();
                } else {
                    tapPanicState.reset();

                    startPanicRequest();
                }
            }
        });
    }

    private void startPanicRequest() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String bestProvider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(this, "Cannot determine current location", Toast.LENGTH_SHORT).show();
        }

        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Intent intent = new Intent(this, PanicShotActivity.class);

        intent.putExtra(getResources().getString(R.string.latitude), latitude);
        intent.putExtra(getResources().getString(R.string.longitude), longitude);

        startActivity(intent);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class TapPanicState {
        int TAP_NEEDED = 3;
        int tapCounter;

        void increase() {
            tapCounter++;
        }

        void reset() {
            tapCounter = 0;
        }

        boolean maxTapReached() {
            return tapCounter == (TAP_NEEDED - 1);
        }

        int getRemainingTap() {
            return TAP_NEEDED - tapCounter;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main_menu, menu);
        setFontNavigationOpen();
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_police_news) {
            NavPoliceNews();
        } else if (id == R.id.nav_police_about) {
            NavTentangPolres();
        } else if (id == R.id.nav_app_about) {
            NavAppAbout();
        } else if (id == R.id.nav_setting) {
            NavPengaturan();
        } else if (id == R.id.nav_exit) {
            NavExit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ClickLiveChat ()
    {
        label_button_promoter_text = (TextView) findViewById(R.id.label_button_promoter_text);
        label_button_promoter_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Start live chat activity
                 * */
                Intent intent = new Intent(MainMenuActivity.this, com.app.sigap.LiveChatActivity.class);
                //Intent intent = new Intent(MainMenuActivity.this, com.sendbird.android.sample.SendBirdGroupChatActivity.class);
                //intent.putExtra("channel_url", "sendbird_open_channel_10225_ad0bb96dcdd766dd2c35045344ca72d87ae711f5");
                startActivity(intent);
            }
        });

        label_button_promoter_img = (ImageView) findViewById(R.id.label_button_promoter_img);
        label_button_promoter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Start live chat activity
                 * */
                Intent intent = new Intent(MainMenuActivity.this, com.app.sigap.LiveChatActivity.class);
                //Intent intent = new Intent(MainMenuActivity.this, com.sendbird.android.sample.SendBirdGroupChatActivity.class);
                //intent.putExtra("channel_url", "sendbird_open_channel_10225_ad0bb96dcdd766dd2c35045344ca72d87ae711f5");
                startActivity(intent);
            }
        });
    }

    private void ClickBantuanTerdekat ()
    {
        label_button_sherif_text = (TextView) findViewById(R.id.label_button_sherif_text);
        label_button_sherif_img = (ImageView) findViewById(R.id.label_button_sherif_img);

        label_button_sherif_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Start bantuan terdekat
                 * */
                Intent intent = new Intent(MainMenuActivity.this, BantuanTerdekatActivity.class);
                startActivity(intent);
            }
        });
        label_button_sherif_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Start bantuan terdekat
                 * */
                Intent intent = new Intent(MainMenuActivity.this, BantuanTerdekatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ClickPelayananPolres ()
    {
        label_button_police_img = (ImageView) findViewById(R.id.label_button_police_img);
        label_button_police_text = (TextView) findViewById(R.id.label_button_police_text);

        label_button_police_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Start pelayanan polres
                 * */
                Intent intent = new Intent(MainMenuActivity.this, PelayananPolresActivity.class);
                startActivity(intent);
            }
        });
        label_button_police_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Start pelayanan polres
                 * */
                Intent intent = new Intent(MainMenuActivity.this, PelayananPolresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void NavAppAbout ()
    {
        /**
         * Launch app about
         * */
        Intent intent = new Intent(MainMenuActivity.this, AboutApp2Activity.class);
        startActivity(intent);
    }

    private void NavExit()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(MainMenuIDE.pesan_logout);
        builder.setPositiveButton(
            "Yes",
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
                    Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                    startActivity(intent);

                    /**
                     * End of main menu activity
                     * */
                    finishAffinity();
                }
            }
        );
        builder.setNegativeButton(
            "No",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /**
                     * Blocked
                     * */
                }
            }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void NavPengaturan ()
    {
        /**
         * End of main dashboard
         * */
        finishAffinity();

        /**
         * Launch pengaturan
         * */
        Intent intent = new Intent(MainMenuActivity.this, PengaturanActivity.class);
        startActivity(intent);
    }

    private void NavPoliceNews ()
    {
        /**
         * End of main dashboard
         * */
        finishAffinity();

        /**
         * Launch berita polres
         * */
        Intent intent = new Intent(MainMenuActivity.this, BeritaPolresActivity.class);
        startActivity(intent);
    }

    private void NavTentangPolres ()
    {
        Intent intent = new Intent(MainMenuActivity.this, TentangPolresActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("")
    private void setFontOnBody()
    {
        label_button_panic_text = (TextView) findViewById(R.id.label_button_panic_text);
        label_button_promoter_text = (TextView) findViewById(R.id.label_button_promoter_text);
        label_button_sherif_text = (TextView) findViewById(R.id.label_button_sherif_text);
        label_button_police_text = (TextView) findViewById(R.id.label_button_police_text);
        label_footer_memo = (TextView) findViewById(R.id.label_footer_memo);
        toolbar_text = (TextView) findViewById(R.id.toolbar_text);

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
        label_button_panic_text.setTypeface(typeface);
        label_button_promoter_text.setTypeface(typeface);
        label_button_sherif_text.setTypeface(typeface);
        label_button_police_text.setTypeface(typeface);
        label_footer_memo.setTypeface(typeface);
        toolbar_text.setTypeface(typeface);
    }

    @SuppressWarnings("")
    private void setFontNavigationOpen()
    {

        label_police_name = (TextView) findViewById(R.id.label_police_name);
        label_police_call_center = (TextView) findViewById(R.id.label_police_call_center);
        label_police_address = (TextView) findViewById(R.id.label_police_address);


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

        label_police_name.setTypeface(typeface);
        label_police_call_center.setTypeface(typeface);
        label_police_address.setTypeface(typeface);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu m = navigationView.getMenu();

        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j < subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/titillium_regular_webfont.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("fonts/titillium_regular_webfont.ttf" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }



}
