package com.app.sources;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by blue on 12/11/16.
 */

public class StaticUtils {

    public static Typeface typeface (Context context)
    {
        Typeface typeface = Typeface.createFromAsset(
            context.getAssets(),
            "fonts/titillium_regular_webfont.ttf"
        );
        return typeface;
    }

}
