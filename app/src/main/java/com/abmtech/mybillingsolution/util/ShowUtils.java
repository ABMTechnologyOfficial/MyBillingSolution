package com.abmtech.mybillingsolution.util;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ShowUtils {
    public static void makeSnackShort(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
    public static void makeSnackLong(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
    public static void makeSnackInfinite(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).show();
    }
}
