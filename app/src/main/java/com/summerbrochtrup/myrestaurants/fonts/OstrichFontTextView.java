package com.summerbrochtrup.myrestaurants.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class OstrichFontTextView extends TextView {

    public OstrichFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/ostrich-regular.ttf"));
    }
}
