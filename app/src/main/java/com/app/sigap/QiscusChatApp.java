package com.app.sigap;

import android.app.Application;
import com.qiscus.sdk.Qiscus;

/**
 * Created by blue on 10/11/16.
 */

public class QiscusChatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this, "com.app.sigap");
        //Qiscus.init(this, "Sigap16498");
        //Qiscus.init("qiscuswebapp.herokuapp.com");
    }

}
