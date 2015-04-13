package me.doapps.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by jnolascob on 06/09/2014.
 */
public class Util_Fonts {
    public static Typeface setPNALight(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(),"fonts/pna_light.otf");
    }

    public static Typeface setPNASemiBold(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(),"fonts/pna_semi_bold.otf");
    }

    public static Typeface setPNACursivaLight(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(),"fonts/pna_cursiva_light.otf");
    }
}
