package com.abmtech.mybillingsolution.util;

public class CommonMethods {


    public static boolean validEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
