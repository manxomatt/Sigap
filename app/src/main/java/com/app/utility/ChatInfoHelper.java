package com.app.utility;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

public class ChatInfoHelper {
    public static String getDisplayDateTime(Context context, long milli) {
        Date date = new Date(milli);

        if (System.currentTimeMillis() - milli < 60 * 60 * 24 * 1000l) {
            return DateFormat.getTimeFormat(context).format(date);
        }

        return DateFormat.getDateFormat(context).format(date) + " " + DateFormat.getTimeFormat(context).format(date);
    }
}