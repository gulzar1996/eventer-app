package com.eventer.app.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import static com.eventer.app.EventerApp.color;
import com.eventer.app.EventerApp;
import com.eventer.app.R;

/**
 * Created by Gulzar on 24-10-2016.
 */
public final class ViewUtils {

    private ViewUtils() {}

    public static int getStatusBarHeight() {
        return getInternalDimension("status_bar_height");
    }

    public static int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(EventerApp.context()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return (!hasMenuKey && !hasBackKey) ? getInternalDimension("navigation_bar_height") : 0;
    }

    private static int getInternalDimension(String name) {
        Resources res = EventerApp.context().getResources();
        int resId = res.getIdentifier(name, "dimen", "android");
        return resId != 0 ? res.getDimensionPixelSize(resId) : 0;
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                EventerApp.context().getResources().getDisplayMetrics());
    }

    public static void setBottomMargin(View view, int bottomMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottomMargin);
    }
    public static void setTopMargin(View view, int topMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin);
    }


    public static void applyColorFilter(ImageView imageView, @ColorRes int resId) {
        imageView.setColorFilter(new PorterDuffColorFilter(
                EventerApp.color(resId), PorterDuff.Mode.SRC_ATOP));
    }

    public static void fadeView(final View view, boolean show, long duration) {
        if (show && view.getVisibility() == View.INVISIBLE) {
            ViewCompat.animate(view).alpha(1f).setDuration(duration).withStartAction(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                }
            });

        } else if (!show && view.getVisibility() == View.VISIBLE) {
            ViewCompat.animate(view).alpha(0f).setDuration(duration)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    public static void tintDrawable(TextView textView, int position) {
        DrawableCompat.setTint(textView.getCompoundDrawables()[position], color(R.color.textNormal));
    }
    public static void highlightIcons(ImageView imageView)
    {
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.colorPrimary));
    }
    public static boolean isNavBarOnBottom(@NonNull Context context) {
        final Resources res= context.getResources();
        final Configuration cfg = context.getResources().getConfiguration();
        final DisplayMetrics dm =res.getDisplayMetrics();
        boolean canMove = (dm.widthPixels != dm.heightPixels &&
                cfg.smallestScreenWidthDp < 600);
        return(!canMove || dm.widthPixels < dm.heightPixels);
    }
    public static float getSingleLineTextSize(String text,
                                              TextPaint paint,
                                              float targetWidth,
                                              float low,
                                              float high,
                                              float precision,
                                              DisplayMetrics metrics) {
        final float mid = (low + high) / 2.0f;

        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, metrics));
        final float maxLineWidth = paint.measureText(text);

        if ((high - low) < precision) {
            return low;
        } else if (maxLineWidth > targetWidth) {
            return getSingleLineTextSize(text, paint, targetWidth, low, mid, precision, metrics);
        } else if (maxLineWidth < targetWidth) {
            return getSingleLineTextSize(text, paint, targetWidth, mid, high, precision, metrics);
        } else {
            return mid;
        }
    }
}
