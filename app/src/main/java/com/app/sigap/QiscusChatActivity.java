package com.app.sigap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.master.MainMenuActivity;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.ui.QiscusActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QiscusChatActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.qiscus_primary_light));
        }
        */

        setContentView(R.layout.activity_qiscus_chat);

        configChat();

        setUser();

        /*
        showLoading();
        Qiscus.buildChatRoomWith("1996.fadhli@gmail.com")
              .build();
        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * End of
         * */
        finishAffinity();

        /**
         * Launch main dashboard
         * */
        Intent intent = new Intent(QiscusChatActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }

    private void configChat ()
    {
        Qiscus.getChatConfig()
                .setStatusBarColor(android.R.color.holo_green_dark)
                .setAppBarColor(android.R.color.holo_green_dark)
                .setTitleColor(android.R.color.white)
                .setLeftBubbleColor(android.R.color.holo_green_dark)
                .setRightBubbleColor(android.R.color.holo_blue_dark)
                .setRightBubbleTextColor(android.R.color.white)
                .setRightBubbleTimeColor(android.R.color.white);
    }

    private void openChat ()
    {
        Qiscus.buildChatWith("sigap.id@gmail.com")
                .withTitle("Sigap")
                .build(this, new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivity(intent);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        showError(
                            throwable.getMessage() + "\n" + "Open chat error."
                        );
                    }
                });
    }

    private void setUser ()
    {
        Qiscus.setUser("1996.fadhli@gmail.com", "12345678")
                .withUsername("1996fadhli")
                .save(new Qiscus.SetUserListener() {
                    @Override
                    public void onSuccess(QiscusAccount qiscusAccount) {
                        /*
                        DataManager.saveQiscusAccount(qiscusAccount);
                        startActivity(new Intent(this, ConsultationListActivity.class));
                        */
                        openChat();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        showError(throwable.getMessage());
                    }
                });
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissLoading() {
        progressDialog.dismiss();
    }

}
