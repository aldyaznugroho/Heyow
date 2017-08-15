package com.skripsigg.heyow.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsigg.heyow.R;

/**
 * Created by Aldyaz on 5/9/2017.
 */

public class FeedbackUtils {
    private final String TAG = getClass().getSimpleName();

    private FeedbackUtils() {
    }

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static Snackbar showSnackbar(View view, Context context, String message) {
        Snackbar snackbar = Snackbar.make(view.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        TextView textView =
                (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.color_white));
        snackbar.show();
        return snackbar;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
