package com.example.spasli;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_USER_TERDAFTAR ="user", KEY_PASS_TERDAFTAR ="pass";
    static final String KEY_USERNAME_LOGGED = "username_logged_in";
    static final String KEY_STATUS_LOGGED = "status_logged_in";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_TERDAFTAR, username);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_TERDAFTAR,"");
    }

    public static void setRegisteredPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_TERDAFTAR, password);
        editor.apply();
    }

    public static String getRegisteredPass(Context context){
        return getSharedPreference(context).getString(KEY_PASS_TERDAFTAR,"");
    }

    public static void setLoggedUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME_LOGGED, username);
        editor.apply();
    }

    public static String getLoggedUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME_LOGGED,"");
    }

    public static void setLoggedStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_LOGGED,status);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_LOGGED,false);
    }

    public static void clearLogged (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME_LOGGED);
        editor.remove(KEY_STATUS_LOGGED);
        editor.apply();
    }
}
