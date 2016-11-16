package com.app.sigap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.app.adapter.AdapterKapolres;
import com.app.config.AppController;
import com.app.master.MainMenuActivity;
import com.app.sources.Data;
import com.app.sources.SQLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TentangPolresActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * UI reference
     * */
    private NestedScrollView nsv_1, nsv_2;
    private RelativeLayout ct_data_kapolres;
    private TabLayout tabs;

    private TextView info_head_01, info_head_02, info_head_03;
    private TextView info_value_01, info_value_02, info_value_03;

    private TextView kontak_01, kontak_02, kontak_03, kontak_04, kontak_05, kontak_06, kontak_07;
    /**
     * End of UI reference
     * */

    /**
     * Data kapolres variables
     * */
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    AdapterKapolres adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama, txt_keterangan;
    String id, nama, keterangan;

    private ImageButton btn_close;
    private TextView txt_channel_name;

    private static final String TAG = PolisiActivity.class.getSimpleName();

    private static String url_select 	 = SQLConnection.URL_POLRES_SELECT_KAPOLRES;
    private static String url_insert 	 = SQLConnection.URL_BANTUAN_TERDEKAT_POLISI + "insert.php";
    private static String url_edit 	     = SQLConnection.URL_BANTUAN_TERDEKAT_POLISI + "edit.php";
    private static String url_update 	 = SQLConnection.URL_BANTUAN_TERDEKAT_POLISI + "update.php";
    private static String url_delete 	 = SQLConnection.URL_BANTUAN_TERDEKAT_POLISI + "delete.php";

    public static final String TAG_ID       = "id";
    public static final String TAG_NAMA     = "nama";
    public static final String TAG_KETERANGAN   = "keterangan";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    /**
     * End of data kapolres variables
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_tentang_polres);

        /*
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        // menghubungkan variablel pada layout dan pada java
        /*
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        */
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterKapolres(TentangPolresActivity.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        // fungsi floating action button memanggil form kontak
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "", "SIMPAN");
            }
        });
        */

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        /*
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(PolisiActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(idx);
                                break;
                            case 1:
                                delete(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        */

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

        OpenTabs();
        /**
         * End of Listener
         * */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of data polisi terdekat
         * */
        finishAffinity();

        /**
         * Launch bantuan terdekat dashboard
         * */
        Intent intent = new Intent(
            TentangPolresActivity.this, MainMenuActivity.class
        );
        startActivity(intent);
    }

    private void Exit ()
    {
        btn_close = (ImageButton) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * End of tentang polres activity
                 * */
                finishAffinity();

                /**
                 * Launch main menu activity
                 * */
                Intent intent = new Intent(TentangPolresActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void OpenTabs ()
    {
        tabs = (TabLayout) findViewById(R.id.tabs);
        ct_data_kapolres = (RelativeLayout) findViewById(R.id.ct_data_kapolres);
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
                        ct_data_kapolres.setVisibility(View.GONE);
                        nsv_1.setVisibility(View.VISIBLE);
                        nsv_2.setVisibility(View.GONE);
                        break;
                    case 2:
                        ct_data_kapolres.setVisibility(View.GONE);
                        nsv_1.setVisibility(View.GONE);
                        nsv_2.setVisibility(View.VISIBLE);
                        break;
                    default:
                        ct_data_kapolres.setVisibility(View.VISIBLE);
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

        info_head_01 = (TextView) findViewById(R.id.info_head_01);
        info_head_02 = (TextView) findViewById(R.id.info_head_02);
        info_head_03 = (TextView) findViewById(R.id.info_head_03);

        info_value_01 = (TextView) findViewById(R.id.info_value_01);
        info_value_02 = (TextView) findViewById(R.id.info_value_02);
        info_value_03 = (TextView) findViewById(R.id.info_value_03);

        kontak_01 = (TextView) findViewById(R.id.kontak_01);
        kontak_02 = (TextView) findViewById(R.id.kontak_02);
        kontak_03 = (TextView) findViewById(R.id.kontak_03);
        kontak_04 = (TextView) findViewById(R.id.kontak_04);
        kontak_05 = (TextView) findViewById(R.id.kontak_05);
        kontak_06 = (TextView) findViewById(R.id.kontak_06);
        kontak_07 = (TextView) findViewById(R.id.kontak_07);

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

        info_head_01.setTypeface(typeface_semibold);
        info_head_02.setTypeface(typeface_semibold);
        info_head_03.setTypeface(typeface_semibold);

        info_value_01.setTypeface(typeface_regular);
        info_value_02.setTypeface(typeface_regular);
        info_value_03.setTypeface(typeface_regular);

        kontak_01.setTypeface(typeface_semibold);
        kontak_02.setTypeface(typeface_regular);
        kontak_03.setTypeface(typeface_semibold);
        kontak_04.setTypeface(typeface_regular);
        kontak_05.setTypeface(typeface_semibold);
        kontak_06.setTypeface(typeface_regular);
        kontak_07.setTypeface(typeface_semibold);
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk mengosongi edittext pada form
    private void kosong(){
        txt_id.setText(null);
        txt_nama.setText(null);
        txt_keterangan.setText(null);
    }

    // untuk menampilkan dialog from kontak
    private void DialogForm(String idx, String namax, String keteranganx, String button) {
        dialog = new AlertDialog.Builder(TentangPolresActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_kapolres, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Form Entry");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        txt_keterangan  = (EditText) dialogView.findViewById(R.id.txt_keterangan);

        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_nama.setText(namax);
            txt_keterangan.setText(keteranganx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id      = txt_id.getText().toString();
                nama    = txt_nama.getText().toString();
                keterangan  = txt_keterangan.getText().toString();

                simpan_update();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }

    // untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        item.setId(obj.getString(TAG_ID));
                        item.setNama(obj.getString(TAG_NAMA));
                        item.setKeterangan(obj.getString(TAG_KETERANGAN));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()){
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        callVolley();
                        kosong();

                        Toast.makeText(TentangPolresActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(TentangPolresActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TentangPolresActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("nama", nama);
                    params.put("keterangan", keterangan);
                } else {
                    params.put("id", id);
                    params.put("nama", nama);
                    params.put("keterangan", keterangan);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get edit data
    private void edit(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx      = jObj.getString(TAG_ID);
                        String namax    = jObj.getString(TAG_NAMA);
                        String keteranganx  = jObj.getString(TAG_KETERANGAN);

                        DialogForm(idx, namax, keteranganx, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(TentangPolresActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TentangPolresActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk menghapus
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        callVolley();

                        Toast.makeText(TentangPolresActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(TentangPolresActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TentangPolresActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
