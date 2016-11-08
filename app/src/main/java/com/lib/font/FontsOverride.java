package com.lib.font;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by blue on 07/11/16.
 */

public final class FontsOverride {

    public static void setDefaultFont (Context context)
    {
        final Typeface regular = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/titillium_regular_webfont.ttf");

        String staticTypefaceFieldName = "MONOSPACE";

        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont (
        String staticTypefaceFieldName, final Typeface newTypeface
    ) {
        try
        {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        }
        catch (NoSuchFieldException e_1)
        {
            e_1.printStackTrace();
        }
        catch (IllegalAccessException e_2)
        {
            e_2.printStackTrace();
        }
    }

}
