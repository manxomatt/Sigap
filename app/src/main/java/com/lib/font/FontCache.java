package com.lib.font;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by blue on 18/10/16.
 */

public class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface (String font_name, Context context)
    {
        Typeface typeface = fontCache.get(font_name);

        if (typeface == null)
        {
            try
            {
                typeface = Typeface.createFromAsset
                            (
                                context.getAssets(), font_name
                            );
            }
            catch (Exception e)
            {
                return null;
            }
            fontCache.put(font_name, typeface);
        }

        return typeface;
    }

}
