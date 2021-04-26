package com.infosys.junitmockito.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.infosys.junitmockito.modelnew.ItemRow;

public class AppPreference {
    private static final String TAG = "AppPreference";
    private static AppPreference mInstance;
    Context context;
    private ItemRow user;

    public static final String SHARED_PREF_FILE_NAME = "shopini_pref";

    public static final String AUTHORIZATIONHEADER_AFTER_LOGIN = "AuthorizationheaderAfterLogin";
    public static final String AUTHORIZATIONHEADER = "Authorizationheader";


    public static synchronized AppPreference getInstance(Context context) {
        if (mInstance == null) mInstance = new AppPreference();
        if (context != null && mInstance.context == null) mInstance.context = context;
        return mInstance;
    }

    public void cleanPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    public void addToSharedPref(String key, String val) {
        if (context != null) {
            SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, val);
            editor.apply();
        }
    }

    public String getFromSharedPref(String key) {
        SharedPreferences sharedPref = null;
        if (context != null) {
            sharedPref = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
            return sharedPref.getString(key, "");
        } else {
            return "";
        }

    }

    public void setUser(ItemRow user) {
        this.user = user;

        if (context != null) {
            Gson gson = new Gson();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = prefs.edit();
            if (user == null) {
                editor.remove("user");
            } else {
                editor.putString("user", gson.toJson(user));
            }
            editor.commit();
        }
    }

    public ItemRow getUser() {
        if (user == null && context == null) {
            Log.i(TAG, "Cant get user without a context");
            return null;
        }
        if (user == null && context != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String jsonUser = prefs.getString("user", null);
            if (jsonUser != null) {
                Gson gson = new Gson();
                user = gson.fromJson(jsonUser, ItemRow.class);
            }
        }
        return user;
    }
}
