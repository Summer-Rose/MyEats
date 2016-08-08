package com.summerbrochtrup.myrestaurants.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
public class OstrichFontButton extends Button{

    public OstrichFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/ostrich-regular.ttf"));
    }
}
