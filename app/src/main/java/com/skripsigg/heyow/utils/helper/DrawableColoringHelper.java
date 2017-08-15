package com.skripsigg.heyow.utils.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Aldyaz on 5/20/2017.
 */

public class DrawableColoringHelper {
    @NonNull Context context;
    private int color;
    private Drawable drawable;
    private Drawable wrappedDrawable;

    public DrawableColoringHelper(@NonNull Context context) {
        this.context = context;
    }

    public static DrawableColoringHelper withContext(@NonNull Context context) {
        return new DrawableColoringHelper(context);
    }

    public DrawableColoringHelper withDrawable(@DrawableRes int drawableRes) {
        drawable = ContextCompat.getDrawable(context, drawableRes);
        return this;
    }

    public DrawableColoringHelper withDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public DrawableColoringHelper withColor(@ColorRes int colorRes) {
        color = ContextCompat.getColor(context, colorRes);
        return this;
    }

    public DrawableColoringHelper tint() {
        if (drawable == null) {
            throw new NullPointerException(
                    "Drawable resource must be set using withDrawable() method!");
        }

        if (color == 0) {
            throw new IllegalStateException(
                    "Color resource must be set using withColor() method!");
        }

        wrappedDrawable = drawable.mutate();
        wrappedDrawable = DrawableCompat.wrap(wrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_IN);

        return this;
    }

    public void applyToBackground(@NonNull View view) {
        if (wrappedDrawable == null) {
            throw new NullPointerException("Wrapped drawable must be set using tint() method!");
        }
        view.setBackground(wrappedDrawable);
    }

    public void applyTo(@NonNull ImageView imageView) {
        if (wrappedDrawable == null) {
            throw new NullPointerException("Wrapped drawable must be set using tint() method!");
        }
        imageView.setImageDrawable(wrappedDrawable);
    }

    public void applyTo(@NonNull MenuItem menuItem) {
        if (wrappedDrawable == null) {
            throw new NullPointerException("Wrapped drawable must be set using tint() method!");
        }
        menuItem.setIcon(wrappedDrawable);
    }

    public Drawable get() {
        if (wrappedDrawable == null) {
            throw new NullPointerException("Wrapped drawable must be set using tint() method!");
        }
        return wrappedDrawable;
    }
}
