package com.eventer.app.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gulzar on 28-10-2016.
 */
public class FontUtil {

    private FontUtil() { }

    private static final Map<String, Typeface> sTypefaceCache = new HashMap<>();

    public static Typeface get(Context context, String font) {
        synchronized (sTypefaceCache) {
            if (!sTypefaceCache.containsKey(font)) {
                Typeface tf = Typeface.createFromAsset(
                        context.getApplicationContext().getAssets(), "fonts/" + font + ".ttf");
                sTypefaceCache.put(font, tf);
            }
            return sTypefaceCache.get(font);
        }
    }

    public static String getName(@NonNull Typeface typeface) {
        for (Map.Entry<String, Typeface> entry : sTypefaceCache.entrySet()) {
            if (entry.getValue() == typeface) {
                return entry.getKey();
            }
        }
        return null;
    }
}
