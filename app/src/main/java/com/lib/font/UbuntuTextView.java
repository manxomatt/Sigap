package com.lib.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by blue on 18/10/16.
 */

public class UbuntuTextView extends TextView {

    public UbuntuTextView (Context context)
    {
        super(context);

        applyCustomFont(context);
    }

    public UbuntuTextView (Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        applyCustomFont(context);
    }

    public UbuntuTextView (Context context, AttributeSet attributeSet, int defStyle)
    {
        super(context, attributeSet, defStyle);

        applyCustomFont(context);
    }

    public void applyCustomFont (Context context)
    {
        Typeface customFont;
        customFont = FontCache.getTypeface("fonts/Ubuntu-L.ttf", context);

        setTypeface(customFont);
    }

}
