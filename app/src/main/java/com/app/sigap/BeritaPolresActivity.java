package com.app.sigap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.master.MainMenuActivity;
import com.app.sources.SQLConnection;

public class BeritaPolresActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * Variables
     * */
    private static int splash_interval = 5000;

    SwipeRefreshLayout swipe;

    private WebView webview_berita_polres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_berita_polres);

        // menghubungkan variablel pada layout dan pada java
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           LoadBerita();
                       }
                   }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of berita polres
         * */
        finishAffinity();

        /**
         * Launch main dashboard
         * */
        Intent intent = new Intent(BeritaPolresActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview_berita_polres.canGoBack()) {
            webview_berita_polres.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        LoadBerita();
    }

    @Override
    protected void onStop() {
        super.onStop();
        swipe.setRefreshing(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void LoadBerita ()
    {
        webview_berita_polres = (WebView) findViewById(R.id.webview_berita_polres);

        final String url;
        url = SQLConnection.URL_BERITA_POLRES;

        WebSettings web_setting;
        web_setting = webview_berita_polres.getSettings();
        web_setting.setBuiltInZoomControls(false);
        web_setting.setJavaScriptEnabled(true);

        webview_berita_polres.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webview_berita_polres.loadUrl(url);

        new Handler().postDelayed
        (
            new Runnable()
            {
                @Override
                public void run()
                {
                    swipe.setRefreshing(false);
                }
            }
            , splash_interval
        );
    }

}
