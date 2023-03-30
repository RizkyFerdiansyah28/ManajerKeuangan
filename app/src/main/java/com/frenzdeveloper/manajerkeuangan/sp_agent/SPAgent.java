package com.frenzdeveloper.manajerkeuangan.sp_agent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPAgent {
    private static String USERNAME_LOGGED_IN = "username";
    private static String ID_LOGGED_IN = "id";
    private static String IS_LOGGED_IN = "status_login";

    /** Pendeklarasian Shared Preferences yang berdasarkan paramater context */
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getUsernameLoggedIn(Context context) {
        return getSharedPreference(context).getString(USERNAME_LOGGED_IN, null);
    }

    public static void setUsernameLoggedIn(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USERNAME_LOGGED_IN, username);
        editor.apply();
    }

    public static int getIdLoggedIn(Context context) {
        return getSharedPreference(context).getInt(ID_LOGGED_IN, -1);
    }

    public static void setIdLoggedIn(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(ID_LOGGED_IN, id);
        editor.apply();
    }

    public static boolean getIsLoggedIn(Context context) {
        return getSharedPreference(context).getBoolean(IS_LOGGED_IN, false);
    }

    public static void setIsLoggedIn(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(IS_LOGGED_IN, status);
        editor.apply();
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(USERNAME_LOGGED_IN);
        editor.remove(ID_LOGGED_IN);
        editor.remove(IS_LOGGED_IN);
        editor.apply();
    }
}
