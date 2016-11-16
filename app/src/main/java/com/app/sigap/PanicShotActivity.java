
package com.app.sigap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class PanicShotActivity extends AppCompatActivity {
    private static final int CAMERA_AND_STORAGE_PERMISSION = 1945;
    private static final int REQUEST_CODE = 1946;

    private ImageView imgPanicSituation;
    private EditText txtDescription;

    private double latitude;
    private double longitude;
    private Uri uriFilePath;
    private String imgTakenPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (uriFilePath == null && savedInstanceState.getString("uri_file_path") != null) {
                uriFilePath = Uri.parse(savedInstanceState.getString("uri_file_path"));
            }
        }

        setContentView(R.layout.activity_panic_shot);

        if (!isPanicButtonPermissionGranted()) {
            finish();
        }

        Intent intent = getIntent();

        setupUI();
        setupUIListener();
        setupVariables(intent);
    }

    private boolean isPanicButtonPermissionGranted() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Please allow a requested permission to upload.", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA_AND_STORAGE_PERMISSION);
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Please allow a requested permission to upload.", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA_AND_STORAGE_PERMISSION);
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Please allow a requested permission to upload.", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            CAMERA_AND_STORAGE_PERMISSION);
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA},
                            CAMERA_AND_STORAGE_PERMISSION);
                }

                return false;
            }
        }

        return true;
    }

    private void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtDescription = (EditText) findViewById(R.id.txtDescription);
        imgPanicSituation = (ImageView) findViewById(R.id.imgPanicSituation);
    }

    private void setupUIListener() {
        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });
    }

    private void sendRequest() {
        String description = txtDescription.getText().toString();
        File file = new File(imgTakenPath);

        // TODO: send the request here with parameter description, file, latitude, longitude
    }

    private void setupVariables(Intent intent) {
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        //todo: show camera and take the shot
        startCameraActivity();
    }

    private void startCameraActivity() {
        PackageManager packageManager = getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            File mainDirectory = new File(Environment.getExternalStorageDirectory(), "sigap/tmp");

            if (!mainDirectory.exists()) {
                mainDirectory.mkdirs();
            }

            Calendar calendar = Calendar.getInstance();

            uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG_" + calendar.getTimeInMillis()));

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);

            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (uriFilePath != null) {
            outState.putString("uri_file_path", uriFilePath.toString());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                // Here is path of your captured image, so you can create bitmap from it, etc.
                imgTakenPath = uriFilePath.getPath();

                bindImageFromPath(imgTakenPath);

                System.out.println(imgTakenPath);
            }
        }
    }

    private void bindImageFromPath(String path) {
        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imgPanicSituation.setImageBitmap(myBitmap);
        }
    }
}