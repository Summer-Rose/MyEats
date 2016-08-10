package com.summerbrochtrup.myrestaurants.fonts;

/**
 * Created by summerbrochtrup on 8/9/16.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OstrichBoldTextView extends TextView {

    public OstrichBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/ostrich-bold.ttf"));
    }
}
