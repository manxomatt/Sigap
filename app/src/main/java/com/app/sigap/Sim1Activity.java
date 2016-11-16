package com.app.sigap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.master.MainMenuActivity;
import com.app.sources.SQLConnection;
import com.app.sources.SimIDE;
import com.app.sources.SimLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sim1Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * UI reference
     * */
    private Button button_next_identitas, button_back_keluarga, button_next_keluarga;
    private Button button_back_pendidikan, button_finish_pendidikan;
    private EditText text_namadepan, text_namabelakang, text_tinggibadan, text_tempatlahir, text_tanggallahir_tahun;
    private EditText text_alamatlengkap, text_rtrw, text_kota, text_kodepos, text_nomortelepon;
    private EditText text_nomorktp, text_namaayah, text_namaibu;
    private ImageButton btn_close;
    private RadioButton radio_pria, radio_wanita, radio_wni, radio_wna;
    private RelativeLayout ct_data_sim1, ct_data_sim2, ct_data_sim3;
    private Spinner spinner_jenis_permohonan, spinner_golongansim, spinner_pekerjaan;
    private Spinner spinner_tanggallahir_bulan, spinner_tanggallahir_tanggal;
    private Spinner spinner_pendidikan, spinner_cacatfisiklain;
    private TabLayout tabs;
    private TextView txt_channel_name;
    private TextView label_jenis_permohonan, label_golongansim;
    private TextView label_namadepan, label_namabelakang;
    private TextView label_jeniskelamin, label_kewarganegaraan;
    private TextView label_tinggibadan, label_tempatlahir, label_tanggallahir;
    private TextView label_pekerjaan, label_alamatlengkap, label_rtrw, label_kota, label_kodepos, label_nomortelepon;
    private TextView label_nomorktp, label_namaayah, label_namaibu;
    private TextView label_pendidikan, label_cacatfisiklain;
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

        setContentView(R.layout.activity_sim1);

        /**
         * Config
         * */
        setFonts();

        LoadJenisPermohonan();

        LoadGolonganSIM();

        LoadTanggalLahirListsBulan();

        LoadTanggalLahirListsTanggal(true);

        LoadPekerjaan();

        LoadPendidikan();

        LoadCacatFisikLain();
        /**
         * End of config
         * */

        /**
         * Listener
         * */
        Exit();

        OpenTabs();

        ClickJenisPermohonan();

        ClickGolonganSIM();

        ClickJenisKelamin();

        ClickKewarganegaraan();

        ClickTanggalLahirBulan();

        ClickTanggalLahirTanggal();

        ClickPekerjaan();

        ClickSelanjutnyaOnIdentitas();

        ClickKembaliOnKeluarga();

        ClickSelanjutnyaOnKeluarga();

        ClickPendidikan();

        ClickCacatFisikLain();

        ClickKembaliOnPendidikan();

        ClickSelesaiOnPendidikan();
        /**
         * End of listener
         * */

        /**
         * Special config
         * */
        setTabs();

        setTanggalLahir();

        setTahunSmartphone();
        /**
         * End of Special config
         * */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of sim activity
         * */
        finishAffinity();

        /**
         * Launch pelayanan polres activity
         * */
        Intent intent = new Intent(Sim1Activity.this, PelayananPolresActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {

    }

    private void ClickCacatFisikLain ()
    {
        spinner_cacatfisiklain = (Spinner) findViewById(R.id.spinner_cacatfisiklain);

        spinner_cacatfisiklain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                int index;
                index = position;

                String CacatFisikLain;

                switch (index)
                {
                    case 1:
                        CacatFisikLain = SimIDE.cacatfisiklain_1;
                        break;
                    case 2:
                        CacatFisikLain = SimIDE.cacatfisiklain_2;
                        break;
                    case 3:
                        CacatFisikLain = SimIDE.cacatfisiklain_3;
                        break;
                    case 4:
                        CacatFisikLain = SimIDE.cacatfisiklain_4;
                        break;
                    case 5:
                        CacatFisikLain = SimIDE.cacatfisiklain_5;
                        break;
                    default:
                        CacatFisikLain = "";
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setCacatFisikLain(CacatFisikLain);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void ClickGolonganSIM ()
    {
        spinner_golongansim = (Spinner) findViewById(R.id.spinner_golongansim);

        spinner_golongansim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                int index;
                index = position;

                String golongan;

                switch (index)
                {
                    case 1:
                        golongan = SimIDE.golongan_1;
                        break;
                    case 2:
                        golongan = SimIDE.golongan_2;
                        break;
                    case 3:
                        golongan = SimIDE.golongan_3;
                        break;
                    case 4:
                        golongan = SimIDE.golongan_4;
                        break;
                    case 5:
                        golongan = SimIDE.golongan_5;
                        break;
                    case 6:
                        golongan = SimIDE.golongan_6;
                        break;
                    case 7:
                        golongan = SimIDE.golongan_7;
                        break;
                    case 8:
                        golongan = SimIDE.golongan_8;
                        break;
                    default:
                        golongan = "";
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setGolonganSIM(golongan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void ClickJenisKelamin ()
    {
        radio_pria = (RadioButton) findViewById(R.id.radio_pria);
        radio_wanita = (RadioButton) findViewById(R.id.radio_wanita);

        radio_pria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kelamin;

                radio_pria.setChecked(true);
                radio_wanita.setChecked(false);
                kelamin = SimIDE.jeniskelamin_pria;

                /**
                 * Set variable into memory options
                 * */
                SimLog.setJenisKelamin(kelamin);
            }
        });
        radio_wanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kelamin;

                radio_pria.setChecked(false);
                radio_wanita.setChecked(true);
                kelamin = SimIDE.jeniskelamin_wanita;

                /**
                 * Set variable into memory options
                 * */
                SimLog.setJenisKelamin(kelamin);
            }
        });
    }

    private void ClickJenisPermohonan ()
    {
        spinner_jenis_permohonan = (Spinner) findViewById(R.id.spinner_jenis_permohonan);

        spinner_jenis_permohonan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                int index;
                index = position;

                String jenis_permohonan;

                switch (index)
                {
                    case 1:
                        jenis_permohonan = SimIDE.jenis_permohonan_1;
                        break;
                    case 2:
                        jenis_permohonan = SimIDE.jenis_permohonan_2;
                        break;
                    case 3:
                        jenis_permohonan = SimIDE.jenis_permohonan_3;
                        break;
                    case 4:
                        jenis_permohonan = SimIDE.jenis_permohonan_4;
                        break;
                    case 5:
                        jenis_permohonan = SimIDE.jenis_permohonan_5;
                        break;
                    case 6:
                        jenis_permohonan = SimIDE.jenis_permohonan_6;
                        break;
                    default:
                        jenis_permohonan = "";
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setJenisPermohonan(jenis_permohonan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void ClickKembaliOnKeluarga ()
    {
        /**
         * Object
         * */
        button_back_keluarga = (Button) findViewById(R.id.button_back_keluarga);
        /**
         * End of Object
         * */

        button_back_keluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Select tab item
                 * */
                tabs = (TabLayout) findViewById(R.id.tabs);
                tabs.getTabAt(0).select();
            }
        });
    }

    private void ClickKembaliOnPendidikan ()
    {
        /**
         * Object
         * */
        button_back_pendidikan = (Button) findViewById(R.id.button_back_pendidikan);
        /**
         * End of Object
         * */

        button_back_pendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Select tab item
                 * */
                tabs = (TabLayout) findViewById(R.id.tabs);
                tabs.getTabAt(1).select();
            }
        });
    }

    private void ClickKewarganegaraan ()
    {
        radio_wni = (RadioButton) findViewById(R.id.radio_wni);
        radio_wna = (RadioButton) findViewById(R.id.radio_wna);

        radio_wni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kewarganegaraan;

                radio_wni.setChecked(true);
                radio_wna.setChecked(false);
                kewarganegaraan = SimIDE.wni;

                /**
                 * Set variable into memory option
                 * */
                SimLog.setKewarganegaraan(kewarganegaraan);
            }
        });
        radio_wna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kewarganegaraan;

                radio_wni.setChecked(false);
                radio_wna.setChecked(true);
                kewarganegaraan = SimIDE.wna;

                /**
                 * Set variable into memory option
                 * */
                SimLog.setKewarganegaraan(kewarganegaraan);
            }
        });
    }

    private void ClickPekerjaan ()
    {
        spinner_pekerjaan = (Spinner) findViewById(R.id.spinner_pekerjaan);

        spinner_pekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                int index;
                index = position;

                String pekerjaan;

                switch (index)
                {
                    case 1:
                        pekerjaan = SimIDE.pekerjaan_1;
                        break;
                    case 2:
                        pekerjaan = SimIDE.pekerjaan_2;
                        break;
                    case 3:
                        pekerjaan = SimIDE.pekerjaan_3;
                        break;
                    case 4:
                        pekerjaan = SimIDE.pekerjaan_4;
                        break;
                    case 5:
                        pekerjaan = SimIDE.pekerjaan_5;
                        break;
                    case 6:
                        pekerjaan = SimIDE.pekerjaan_6;
                        break;
                    default:
                        pekerjaan = "";
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setPekerjaan(pekerjaan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void ClickPendidikan ()
    {
        spinner_pendidikan = (Spinner) findViewById(R.id.spinner_pendidikan);

        spinner_pendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                int index;
                index = position;

                String pendidikan;

                switch (index)
                {
                    case 1:
                        pendidikan = SimIDE.pendidikan_1;
                        break;
                    case 2:
                        pendidikan = SimIDE.pendidikan_2;
                        break;
                    case 3:
                        pendidikan = SimIDE.pendidikan_3;
                        break;
                    case 4:
                        pendidikan = SimIDE.pendidikan_4;
                        break;
                    case 5:
                        pendidikan = SimIDE.pendidikan_5;
                        break;
                    case 6:
                        pendidikan = SimIDE.pendidikan_6;
                        break;
                    default:
                        pendidikan = "";
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setPendidikan(pendidikan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    @SuppressWarnings("")
    private void ClickSelanjutnyaOnIdentitas ()
    {
        /**
         * Object
         * */
        button_next_identitas = (Button) findViewById(R.id.button_next_identitas);
        /**
         * End of Object
         * */

        /**
         * Listener
         * */
        button_next_identitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Objects
                 * */
                radio_pria = (RadioButton) findViewById(R.id.radio_pria);
                radio_wanita = (RadioButton) findViewById(R.id.radio_wanita);
                radio_wni = (RadioButton) findViewById(R.id.radio_wni);
                radio_wna = (RadioButton) findViewById(R.id.radio_wna);
                spinner_jenis_permohonan = (Spinner) findViewById(R.id.spinner_jenis_permohonan);
                spinner_golongansim = (Spinner) findViewById(R.id.spinner_golongansim);
                spinner_tanggallahir_bulan = (Spinner) findViewById(R.id.spinner_tanggallahir_bulan);
                spinner_tanggallahir_tanggal = (Spinner) findViewById(R.id.spinner_tanggallahir_tanggal);
                spinner_pekerjaan = (Spinner) findViewById(R.id.spinner_pekerjaan);
                tabs = (TabLayout) findViewById(R.id.tabs);
                ct_data_sim1 = (RelativeLayout) findViewById(R.id.ct_data_sim1);
                ct_data_sim2 = (RelativeLayout) findViewById(R.id.ct_data_sim2);
                ct_data_sim3 = (RelativeLayout) findViewById(R.id.ct_data_sim3);
                /**
                 * End of Objects
                 * */

                /**
                 * Variables
                 * */
                String JenisPermohonan, GolonganSIM, Pekerjaan;
                String NamaDepan, NamaBelakang;
                String TinggiBadan, TempatLahir, TanggalLahir;
                String tglLeft, tglMid, tglRight;
                String AlamatLengkap, RTRW, Kota, KodePos, NomorTelepon, NomorKTP;
                Integer iTglLeftSmartphone, iTglLeft, iUsia;
                TextView textJenisPermohonan, textGolonganSIM, textPekerjaan;
                TextView textBulan, textTanggal;

                textJenisPermohonan = (TextView) spinner_jenis_permohonan.getSelectedView();
                textGolonganSIM = (TextView) spinner_golongansim.getSelectedView();
                textPekerjaan = (TextView) spinner_pekerjaan.getSelectedView();
                textBulan = (TextView) spinner_tanggallahir_bulan.getSelectedView();
                textTanggal = (TextView) spinner_tanggallahir_tanggal.getSelectedView();

                JenisPermohonan = spinner_jenis_permohonan.getSelectedItem().toString();
                GolonganSIM = spinner_golongansim.getSelectedItem().toString();
                NamaDepan = text_namadepan.getText().toString();
                NamaBelakang = text_namabelakang.getText().toString();
                TinggiBadan = text_tinggibadan.getText().toString();
                TempatLahir = text_tempatlahir.getText().toString();
                tglMid = spinner_tanggallahir_bulan.getSelectedItem().toString();
                tglRight = spinner_tanggallahir_tanggal.getSelectedItem().toString();
                tglLeft = text_tanggallahir_tahun.getText().toString();
                iTglLeft = Integer.parseInt(tglLeft);
                iTglLeftSmartphone = Integer.parseInt(SimLog.getTglLeftSmartphone());
                iUsia = iTglLeftSmartphone - iTglLeft;
                TanggalLahir = tglLeft + "-" + tglMid + "-" + tglRight;
                Pekerjaan = spinner_pekerjaan.getSelectedItem().toString();
                AlamatLengkap = text_alamatlengkap.getText().toString();
                RTRW = text_rtrw.getText().toString();
                Kota = text_kota.getText().toString();
                KodePos = text_kodepos.getText().toString();
                NomorTelepon = text_nomortelepon.getText().toString();
                NomorKTP = text_nomorktp.getText().toString();
                /**
                 * End of Variables
                 * */

                String message;

                if (JenisPermohonan.equalsIgnoreCase(SimIDE.spinner_default_value_index_0))
                {
                    message = "Anda belum memilih jenis permohonan.";
                    textJenisPermohonan.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (GolonganSIM.equalsIgnoreCase(SimIDE.spinner_default_value_index_0))
                {
                    message = "Anda belum memilih golongan SIM yang diminta.";
                    textGolonganSIM.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (NamaDepan.isEmpty() == true)
                {
                    message = "Anda belum mengisikan nama depan.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (NamaBelakang.isEmpty() == true)
                {
                    message = "Anda belum mengisikan nama belakang.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (radio_pria.isChecked() == false && radio_wanita.isChecked() == false)
                {
                    message = "Anda belum memilih jenis kelamin.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (radio_wni.isChecked() == false && radio_wna.isChecked() == false)
                {
                    message = "Anda belum memilih kewarganegaraan.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (TinggiBadan.isEmpty() == true)
                {
                    message = "Anda belum mengisikan tinggi badan.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (TempatLahir.isEmpty() == true)
                {
                    message = "Anda belum mengisikan tempat lahir.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (tglMid.equalsIgnoreCase(SimIDE.spinner_default_bulan_index_0))
                {
                    message = "Anda belum memilih bulan lahir.";
                    textBulan.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (tglRight.equalsIgnoreCase(SimIDE.spinner_default_tanggal_index_0))
                {
                    message = "Anda belum memilih tanggal lahir.";
                    textTanggal.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (tglLeft.isEmpty() == true)
                {
                    message = "Anda belum mengisikan tahun lahir.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (iTglLeft > iTglLeftSmartphone)
                {
                    message = "Mohon masukan tahun lahir dengan benar.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (iUsia < SimIDE.usia_minimal)
                {
                    message = "Usia minimal 17 tahun.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (Pekerjaan.equalsIgnoreCase(SimIDE.spinner_default_value_index_0))
                {
                    message = "Anda belum memilih pekerjaan.";
                    textPekerjaan.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (AlamatLengkap.isEmpty() == true)
                {
                    message = "Anda belum mengisikan alamat lengkap.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (RTRW.isEmpty() == true)
                {
                    message = "Anda belum mengisikan RTRW.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (Kota.isEmpty() == true)
                {
                    message = "Anda belum mengisikan kota.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (KodePos.isEmpty() == true)
                {
                    message = "Anda belum mengisikan kode pos.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (NomorTelepon.isEmpty() == true)
                {
                    message = "Anda belum mengisikan nomor telepon.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (NomorKTP.isEmpty() == true)
                {
                    message = "Anda belum mengisikan nomor KTP.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    /**
                     * Set variables into memory option
                     * */
                    SimLog.setNamaDepan(NamaDepan);
                    SimLog.setNamaBelakang(NamaBelakang);
                    SimLog.setTinggiBadan(TinggiBadan);
                    SimLog.setTempatLahir(TempatLahir);
                    SimLog.setTanggalLahir(TanggalLahir);
                    SimLog.setAlamatLengkap(AlamatLengkap);
                    SimLog.setRTRW(RTRW);
                    SimLog.setKota(Kota);
                    SimLog.setKodePos(KodePos);
                    SimLog.setNomorTelepon(NomorTelepon);
                    SimLog.setNomorKTP(NomorKTP);

                    /**
                     * Enable tab keluarga
                     * */
                    LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
                    tabStrip.setEnabled(false);
                    for (int i = 0; i < tabStrip.getChildCount(); i++) {
                        tabStrip.getChildAt(i).setClickable(false);
                    }
                    tabStrip.getChildAt(0).setClickable(true);
                    tabStrip.getChildAt(1).setClickable(true);
                    tabStrip.getChildAt(2).setClickable(false);

                    /**
                     * Select tab item
                     * */
                    tabs.getTabAt(1).select();

                    /**
                     * Switch UI
                     * */
                    ct_data_sim1.setVisibility(View.GONE);
                    ct_data_sim2.setVisibility(View.VISIBLE);
                    ct_data_sim3.setVisibility(View.GONE);
                }
            }
        });
    }

    @SuppressWarnings("")
    private void ClickSelanjutnyaOnKeluarga ()
    {
        /**
         * Object
         * */
        button_next_keluarga = (Button) findViewById(R.id.button_next_keluarga);
        /**
         * End of Object
         * */

        button_next_keluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Objects
                 * */
                ct_data_sim1 = (RelativeLayout) findViewById(R.id.ct_data_sim1);
                ct_data_sim2 = (RelativeLayout) findViewById(R.id.ct_data_sim2);
                ct_data_sim3 = (RelativeLayout) findViewById(R.id.ct_data_sim3);
                /**
                 * End of Objects
                 * */

                /**
                 * Variables
                 * */
                String NamaAyah, NamaIbu;
                NamaAyah = text_namaayah.getText().toString();
                NamaIbu = text_namaibu.getText().toString();
                /**
                 * End of Variables
                 * */

                String message;

                if (NamaAyah.isEmpty() == true)
                {
                    message = "Anda belum mengisikan nama ayah.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if (NamaIbu.isEmpty() == true)
                {
                    message = "Anda belum mengisikan nama ibu.";
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    /**
                     * Set variables into memory option
                     * */
                    SimLog.setNamaAyah(NamaAyah);
                    SimLog.setNamaIbu(NamaIbu);

                    /**
                     * Enable tab pendidikan
                     * */
                    LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
                    tabStrip.setEnabled(false);
                    for (int i = 0; i < tabStrip.getChildCount(); i++) {
                        tabStrip.getChildAt(i).setClickable(false);
                    }
                    tabStrip.getChildAt(0).setClickable(true);
                    tabStrip.getChildAt(1).setClickable(true);
                    tabStrip.getChildAt(2).setClickable(true);

                    /**
                     * Select tab item
                     * */
                    tabs.getTabAt(2).select();

                    /**
                     * Switch UI
                     * */
                    ct_data_sim1.setVisibility(View.GONE);
                    ct_data_sim2.setVisibility(View.GONE);
                    ct_data_sim3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @SuppressWarnings("")
    private void ClickSelesaiOnPendidikan ()
    {
        /**
         * Object
         * */
        button_finish_pendidikan = (Button) findViewById(R.id.button_finish_pendidikan);
        /**
         * End of Object
         * */

        button_finish_pendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Objects
                 * */
                spinner_pendidikan = (Spinner) findViewById(R.id.spinner_pendidikan);
                spinner_cacatfisiklain = (Spinner) findViewById(R.id.spinner_cacatfisiklain);
                /**
                 * End of Objects
                 * */

                /**
                 * Variables
                 * */
                String Pendidikan, CacatFisikLain;
                TextView textPendidikan, textCacatFisikLain;

                Pendidikan = spinner_pendidikan.getSelectedItem().toString();
                CacatFisikLain = spinner_cacatfisiklain.getSelectedItem().toString();
                textPendidikan = (TextView) spinner_pendidikan.getSelectedView();
                textCacatFisikLain = (TextView) spinner_cacatfisiklain.getSelectedView();
                /**
                 * End of Variables
                 * */

                String message;

                if (Pendidikan.equalsIgnoreCase(SimIDE.spinner_default_value_index_0))
                {
                    message = "Anda belum memilih pendidikan.";
                    textPendidikan.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else if(CacatFisikLain.equalsIgnoreCase(SimIDE.spinner_default_value_index_0))
                {
                    message = "Anda belum memilih data cacat fisik lain.";
                    textCacatFisikLain.setError(null);
                    Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    /**
                     * Save registrasi SIM
                     * */
                    SaveRegistrasiSIM();
                }
            }
        });
    }

    private void ClickTanggalLahirBulan ()
    {
        /**
         * Object
         * */
        spinner_tanggallahir_bulan = (Spinner) findViewById(R.id.spinner_tanggallahir_bulan);

        /**
         * Implement
         * */
        spinner_tanggallahir_bulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                /**
                 * Objects
                 * */
                spinner_tanggallahir_tanggal = (Spinner) findViewById(R.id.spinner_tanggallahir_tanggal);
                text_tanggallahir_tahun = (EditText) findViewById(R.id.text_tanggallahir_tahun);
                /**
                 * End of Objects
                 * */

                int index;
                index = position;

                /**
                 * Set block or unblock
                 * */
                if (index > 0)
                {
                    spinner_tanggallahir_tanggal.setEnabled(true);
                    text_tanggallahir_tahun.setEnabled(false);
                }
                else
                {
                    spinner_tanggallahir_tanggal.setEnabled(false);
                    text_tanggallahir_tahun.setEnabled(false);
                }

                /**
                 * Read index, meanwhile if it's as ganjil set true for LoadTanggalLahirListsTanggal(true) and neither.
                 * */
                String tglMid;

                switch (index)
                {
                    case 1:
                        tglMid = SimIDE.tanggallahir_bulan_01;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    case 2:
                        tglMid = SimIDE.tanggallahir_bulan_02;
                        LoadTanggalLahirListsTanggal(false);
                        break;
                    case 3:
                        tglMid = SimIDE.tanggallahir_bulan_03;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    case 4:
                        tglMid = SimIDE.tanggallahir_bulan_04;
                        LoadTanggalLahirListsTanggal(false);
                        break;
                    case 5:
                        tglMid = SimIDE.tanggallahir_bulan_05;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    case 6:
                        tglMid = SimIDE.tanggallahir_bulan_06;
                        LoadTanggalLahirListsTanggal(false);
                        break;
                    case 7:
                        tglMid = SimIDE.tanggallahir_bulan_07;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    case 8:
                        tglMid = SimIDE.tanggallahir_bulan_08;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    case 9:
                        tglMid = SimIDE.tanggallahir_bulan_09;
                        LoadTanggalLahirListsTanggal(false);
                        break;
                    case 10:
                        tglMid = SimIDE.tanggallahir_bulan_10;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    case 11:
                        tglMid = SimIDE.tanggallahir_bulan_11;
                        LoadTanggalLahirListsTanggal(false);
                        break;
                    case 12:
                        tglMid = SimIDE.tanggallahir_bulan_12;
                        LoadTanggalLahirListsTanggal(true);
                        break;
                    default:
                        tglMid = "";
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setTglMid(tglMid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void ClickTanggalLahirTanggal ()
    {
        /**
         * Object
         * */
        spinner_tanggallahir_tanggal = (Spinner) findViewById(R.id.spinner_tanggallahir_tanggal);

        /**
         * Implement
         * */
        spinner_tanggallahir_tanggal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                text_tanggallahir_tahun = (EditText) findViewById(R.id.text_tanggallahir_tahun);

                int index;
                index = position;

                /**
                 * Set block or unblock
                 * */
                if (index > 0)
                {
                    text_tanggallahir_tahun.setEnabled(true);
                }
                else
                {
                    text_tanggallahir_tahun.setEnabled(false);
                }

                /**
                 * Set variable declaration
                 * */
                String tglRight;

                switch (index)
                {
                    case 0:
                        tglRight = "";
                        break;
                    default:
                        if (index >= 1 && index < 10)
                        {
                            tglRight = "0" + index;
                        }
                        else
                        {
                            tglRight = "" + index;
                        }
                        break;
                }

                /**
                 * Set variable into memory option
                 * */
                SimLog.setTglRight(tglRight);

                view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
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
                 * End of sim activity
                 * */
                finishAffinity();

                /**
                 * Launch pelayanan polres activity
                 * */
                Intent intent = new Intent(Sim1Activity.this, PelayananPolresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoadCacatFisikLain ()
    {
        spinner_cacatfisiklain = (Spinner) findViewById(R.id.spinner_cacatfisiklain);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sim_cacatfisiklain_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_cacatfisiklain.setAdapter(adapter);
    }

    private void LoadGolonganSIM ()
    {
        spinner_golongansim = (Spinner) findViewById(R.id.spinner_golongansim);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sim_golongansim_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices  #simple_spinner_dropdown_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_golongansim.setAdapter(adapter);
    }

    private void LoadJenisPermohonan ()
    {
        spinner_jenis_permohonan = (Spinner) findViewById(R.id.spinner_jenis_permohonan);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sim_jenis_permohonan_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_jenis_permohonan.setAdapter(adapter);
    }

    private void LoadPekerjaan ()
    {
        spinner_pekerjaan = (Spinner) findViewById(R.id.spinner_pekerjaan);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sim_pekerjaan_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_pekerjaan.setAdapter(adapter);
    }

    private void LoadPendidikan ()
    {
        spinner_pendidikan = (Spinner) findViewById(R.id.spinner_pendidikan);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sim_pendidikan_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_pendidikan.setAdapter(adapter);
    }

    private void LoadTanggalLahirListsBulan ()
    {
        spinner_tanggallahir_bulan = (Spinner) findViewById(R.id.spinner_tanggallahir_bulan);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sim_tanggallahir_bulan, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_tanggallahir_bulan.setAdapter(adapter);
    }

    private void LoadTanggalLahirListsTanggal (Boolean confirm)
    {
        spinner_tanggallahir_tanggal = (Spinner) findViewById(R.id.spinner_tanggallahir_tanggal);

        ArrayAdapter<CharSequence> adapter;
        // Create an ArrayAdapter using the string array and a default spinner layout
        if (confirm == true)
        {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.sim_tanggallahir_list_tanggal_31, android.R.layout.simple_spinner_item);
        }
        else
        {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.sim_tanggallahir_list_tanggal_30, android.R.layout.simple_spinner_item);
        }

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_tanggallahir_tanggal.setAdapter(adapter);
    }

    private void OpenTabs ()
    {
        tabs = (TabLayout) findViewById(R.id.tabs);
        ct_data_sim1 = (RelativeLayout) findViewById(R.id.ct_data_sim1);
        ct_data_sim2 = (RelativeLayout) findViewById(R.id.ct_data_sim2);
        ct_data_sim3 = (RelativeLayout) findViewById(R.id.ct_data_sim3);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                int index;
                index = tab.getPosition();

                switch (index)
                {
                    case 1:
                        ct_data_sim1.setVisibility(View.GONE);
                        ct_data_sim2.setVisibility(View.VISIBLE);
                        ct_data_sim3.setVisibility(View.GONE);
                        break;
                    case 2:
                        ct_data_sim1.setVisibility(View.GONE);
                        ct_data_sim2.setVisibility(View.GONE);
                        ct_data_sim3.setVisibility(View.VISIBLE);
                        break;
                    default:
                        ct_data_sim1.setVisibility(View.VISIBLE);
                        ct_data_sim2.setVisibility(View.GONE);
                        ct_data_sim3.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void SaveRegistrasiSIM ()
    {
        /**
         * Set request method
         * */
        StringRequest stringRequest;
        stringRequest = new StringRequest
            (
                Request.Method.POST,
                SQLConnection.URL_REG_SIM,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        String message;

                        /**
                         * Jika respon gagal
                         * */
                        if (!response.equalsIgnoreCase(SQLConnection.REG_SIM_SUCCESS))
                        {
                            message = "Gagal registrasi SIM.";
                            Toast.makeText(Sim1Activity.this, message, Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            /**
                             * Successful
                             * */
                            SaveSuccessMessage();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @SuppressWarnings("")
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        /**
                         * Tambahkan apa yang terjadi setelah Pesan Error muncul, alternatif
                         * */
                        error.printStackTrace();
                        Toast.makeText(Sim1Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            ){
            protected Map<String, String> getParams()
            {
                /**
                 * Get parameter from memory options
                 * */
                String nomorktp = SimLog.getNomorKTP().toUpperCase();
                String jenispermohonan = SimLog.getJenisPermohonan().toUpperCase();
                String golongansim = SimLog.getGolonganSIM().toUpperCase();
                String namadepan = SimLog.getNamaDepan().toUpperCase();
                String namabelakang = SimLog.getNamaBelakang().toUpperCase();
                String jeniskelamin = SimLog.getJenisKelamin().toUpperCase();
                String kewarganegaraan = SimLog.getKewarganegaraan().toUpperCase();
                String tinggibadan = SimLog.getTinggiBadan().toUpperCase();
                String tempatlahir = SimLog.getTempatLahir().toUpperCase();
                String tanggallahir = SimLog.getTanggalLahir().toUpperCase();
                String pekerjaan = SimLog.getPekerjaan().toUpperCase();
                String alamatlengkap = SimLog.getAlamatLengkap().toUpperCase();
                String rtrw = SimLog.getRTRW().toUpperCase();
                String kota = SimLog.getKota().toUpperCase();
                String kodepos = SimLog.getKodePos().toUpperCase();
                String nomortelepon = SimLog.getNomorTelepon().toUpperCase();
                String namaayah = SimLog.getNamaAyah().toUpperCase();
                String namaibu = SimLog.getNamaIbu().toUpperCase();
                String pendidikan = SimLog.getPendidikan().toUpperCase();
                String cacatfisiklain = SimLog.getCacatFisikLain().toUpperCase();

                /*
                Toast.makeText(
                    Sim1Activity.this,
                        "Nomor KTP : " + nomorktp + "\n" +
                        "Jenis permohonan : " + jenispermohonan + "\n" +
                        "Golongan SIM : " + golongansim + "\n" +
                        "Nama : " + namadepan + " " + namabelakang + "\n" +
                        "Jenis kelamin : " + jeniskelamin + "\n" +
                        "Kewarganegaraan : " + kewarganegaraan + "\n" +
                        "Tinggi badan : " + tinggibadan + " Cm" + "\n" +
                        "Tempat lahir : " + tempatlahir + "\n" +
                        "Tanggal lahir : " + tanggallahir + "\n" +
                        "Pekerjaan : " + pekerjaan + "\n" +
                        "Alamat : " + alamatlengkap + "\n" +
                        "RT/RW : " + rtrw + "\n" +
                        "Kota : " + kota + "\n" +
                        "Kode pos : " + kodepos + "\n" +
                        "No. Telp : " + nomortelepon + "\n" +
                        "Ayah : " + namaayah + "\n" +
                        "Ibu : " + namaibu + "\n" +
                        "Pendidikan : " + pendidikan + "\n" +
                        "Cacat fisik lain : " + cacatfisiklain,
                    Toast.LENGTH_LONG
                ).show();
                */

                /**
                 * Set parameter to send database
                 * */
                Map<String,String> params = new HashMap<String, String>();
                params.put(SQLConnection.KEY_SIM_NOMORKTP, nomorktp);
                params.put(SQLConnection.KEY_SIM_JENISPERMOHONAN, jenispermohonan);
                params.put(SQLConnection.KEY_SIM_GOLONGANSIM, golongansim);
                params.put(SQLConnection.KEY_SIM_NAMADEPAN, namadepan);
                params.put(SQLConnection.KEY_SIM_NAMABELAKANG, namabelakang);
                params.put(SQLConnection.KEY_SIM_JENISKELAMIN, jeniskelamin);
                params.put(SQLConnection.KEY_SIM_KEWARGANEGARAAN, kewarganegaraan);
                params.put(SQLConnection.KEY_SIM_TINGGIBADAN, tinggibadan);
                params.put(SQLConnection.KEY_SIM_TEMPATLAHIR, tempatlahir);
                params.put(SQLConnection.KEY_SIM_TANGGALLAHIR, tanggallahir);
                params.put(SQLConnection.KEY_SIM_PEKERJAAN, pekerjaan);
                params.put(SQLConnection.KEY_SIM_ALAMATLENGKAP, alamatlengkap);
                params.put(SQLConnection.KEY_SIM_RTRW, rtrw);
                params.put(SQLConnection.KEY_SIM_KOTA, kota);
                params.put(SQLConnection.KEY_SIM_KODEPOS, kodepos);
                params.put(SQLConnection.KEY_SIM_NOMORTELEPON, nomortelepon);
                params.put(SQLConnection.KEY_SIM_NAMAAYAH, namaayah);
                params.put(SQLConnection.KEY_SIM_NAMAIBU, namaibu);
                params.put(SQLConnection.KEY_SIM_PENDIDIKAN, pendidikan);
                params.put(SQLConnection.KEY_SIM_CACATFISIKLAIN, cacatfisiklain);
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

    private void SaveSuccessMessage ()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(SimIDE.pesan_reg_success);
        builder.setPositiveButton(
            "Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /**
                     * End of reg SIM
                     * */
                    finishAffinity();

                    /**
                     * Launch main dashboard
                     * */
                    Intent intent = new Intent(
                        Sim1Activity.this, MainMenuActivity.class
                    );
                    startActivity(intent);
                }
            }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressWarnings("")
    private void setFonts ()
    {
        /**
         * Objects
         * */
        txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);

        label_jenis_permohonan = (TextView) findViewById(R.id.label_jenis_permohonan);;
        label_golongansim = (TextView) findViewById(R.id.label_golongansim);

        label_namadepan = (TextView) findViewById(R.id.label_namadepan);
        text_namadepan = (EditText) findViewById(R.id.text_namadepan);

        label_namabelakang = (TextView) findViewById(R.id.label_namabelakang);
        text_namabelakang = (EditText) findViewById(R.id.text_namabelakang);

        label_jeniskelamin = (TextView) findViewById(R.id.label_jeniskelamin);
        radio_pria = (RadioButton) findViewById(R.id.radio_pria);
        radio_wanita = (RadioButton) findViewById(R.id.radio_wanita);

        label_kewarganegaraan = (TextView) findViewById(R.id.label_kewarganegaraan);
        radio_wni = (RadioButton) findViewById(R.id.radio_wni);
        radio_wna = (RadioButton) findViewById(R.id.radio_wna);

        label_tinggibadan = (TextView) findViewById(R.id.label_tinggibadan);
        text_tinggibadan = (EditText) findViewById(R.id.text_tinggibadan);

        label_tempatlahir = (TextView) findViewById(R.id.label_tempatlahir);
        text_tempatlahir = (EditText) findViewById(R.id.text_tempatlahir);

        label_tanggallahir = (TextView) findViewById(R.id.label_tanggallahir);
        text_tanggallahir_tahun = (EditText) findViewById(R.id.text_tanggallahir_tahun);

        label_pekerjaan = (TextView) findViewById(R.id.label_pekerjaan);

        label_alamatlengkap = (TextView) findViewById(R.id.label_alamatlengkap);
        text_alamatlengkap = (EditText) findViewById(R.id.text_alamatlengkap);

        label_rtrw = (TextView) findViewById(R.id.label_rtrw);
        text_rtrw = (EditText) findViewById(R.id.text_rtrw);

        label_kota = (TextView) findViewById(R.id.label_kota);
        text_kota = (EditText) findViewById(R.id.text_kota);

        label_kodepos = (TextView) findViewById(R.id.label_kodepos);
        text_kodepos = (EditText) findViewById(R.id.text_kodepos);

        label_nomortelepon = (TextView) findViewById(R.id.label_nomortelepon);
        text_nomortelepon = (EditText) findViewById(R.id.text_nomortelepon);

        label_nomorktp = (TextView) findViewById(R.id.label_nomorktp);
        text_nomorktp = (EditText) findViewById(R.id.text_nomorktp);

        button_next_identitas = (Button) findViewById(R.id.button_next_identitas);

        label_namaayah = (TextView) findViewById(R.id.label_namaayah);
        text_namaayah = (EditText) findViewById(R.id.text_namaayah);

        label_namaibu = (TextView) findViewById(R.id.label_namaibu);
        text_namaibu = (EditText) findViewById(R.id.text_namaibu);

        button_back_keluarga = (Button) findViewById(R.id.button_back_keluarga);
        button_next_keluarga = (Button) findViewById(R.id.button_next_keluarga);

        label_pendidikan = (TextView) findViewById(R.id.label_pendidikan);
        label_cacatfisiklain = (TextView) findViewById(R.id.label_cacatfisiklain);

        button_back_pendidikan = (Button) findViewById(R.id.button_back_pendidikan);
        button_finish_pendidikan = (Button) findViewById(R.id.button_finish_pendidikan);
        /**
         * End of Objects
         * */

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

        label_jenis_permohonan.setTypeface(typeface_semibold);
        label_golongansim.setTypeface(typeface_semibold);

        label_namadepan.setTypeface(typeface_semibold);
        text_namadepan.setTypeface(typeface_regular);

        label_namabelakang.setTypeface(typeface_semibold);
        text_namabelakang.setTypeface(typeface_regular);

        label_jeniskelamin.setTypeface(typeface_semibold);
        radio_pria.setTypeface(typeface_regular);
        radio_wanita.setTypeface(typeface_regular);

        label_kewarganegaraan.setTypeface(typeface_semibold);
        radio_wni.setTypeface(typeface_regular);
        radio_wna.setTypeface(typeface_regular);

        label_tinggibadan.setTypeface(typeface_semibold);
        text_tinggibadan.setTypeface(typeface_regular);

        label_tempatlahir.setTypeface(typeface_semibold);
        text_tempatlahir.setTypeface(typeface_regular);

        label_tanggallahir.setTypeface(typeface_semibold);
        text_tanggallahir_tahun.setTypeface(typeface_regular);

        label_pekerjaan.setTypeface(typeface_semibold);

        label_alamatlengkap.setTypeface(typeface_semibold);
        text_alamatlengkap.setTypeface(typeface_regular);

        label_rtrw.setTypeface(typeface_semibold);
        text_rtrw.setTypeface(typeface_regular);

        label_kota.setTypeface(typeface_semibold);
        text_kota.setTypeface(typeface_regular);

        label_kodepos.setTypeface(typeface_semibold);
        text_kodepos.setTypeface(typeface_regular);

        label_nomortelepon.setTypeface(typeface_semibold);
        text_nomortelepon.setTypeface(typeface_regular);

        label_nomorktp.setTypeface(typeface_semibold);
        text_nomorktp.setTypeface(typeface_regular);

        button_next_identitas.setTypeface(typeface_regular);

        label_namaayah.setTypeface(typeface_semibold);
        text_namaayah.setTypeface(typeface_regular);

        label_namaibu.setTypeface(typeface_semibold);
        text_namaibu.setTypeface(typeface_regular);

        button_back_keluarga.setTypeface(typeface_regular);
        button_next_keluarga.setTypeface(typeface_regular);

        label_pendidikan.setTypeface(typeface_semibold);
        label_cacatfisiklain.setTypeface(typeface_semibold);

        button_back_pendidikan.setTypeface(typeface_regular);
        button_finish_pendidikan.setTypeface(typeface_regular);
    }

    @SuppressWarnings("")
    private void setTabs ()
    {
        //  success
        tabs = (TabLayout) findViewById(R.id.tabs);

        LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }
        tabStrip.getChildAt(0).setClickable(true);
        tabStrip.getChildAt(1).setClickable(false);
        tabStrip.getChildAt(2).setClickable(false);

        //  success
        /*
        LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        */
    }

    private void setTahunSmartphone ()
    {
        Date date = Calendar.getInstance().getTime();

        DateFormat format = new SimpleDateFormat("yyyy");

        /**
         * Get year on user smartphone
         * */
        String this_year = format.format(date);

        SimLog.setTglLeftSmartphone(this_year);
    }

    @SuppressWarnings("")
    private void setTanggalLahir ()
    {
        spinner_tanggallahir_tanggal = (Spinner) findViewById(R.id.spinner_tanggallahir_tanggal);
        text_tanggallahir_tahun = (EditText) findViewById(R.id.text_tanggallahir_tahun);

        spinner_tanggallahir_tanggal.setEnabled(false);
        text_tanggallahir_tahun.setEnabled(false);
    }

}