package com.adrenastudies.intercorptest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.adrenastudies.intercorptest.App;

public class Preferences {

    private static final String TAG = "com.adrenastudies.intercorptest";
    private static final String IS_LOGGED = TAG + "_IS_LOGGED";

    //----------------------------- Set Preferences ---------------------------------------------

    public static void setIsLogged(String data){
        setValue(App.context, IS_LOGGED, data);
    }

    //----------------------------- Get Preferences ---------------------------------------------

    public static String isUserLogged(){
        return getStringValue(App.context, IS_LOGGED, Constants.FALSE);
    }

    /*---------------------------------------------------------------------------*/

    private static void setValue(Context ctx, String key, String value){
        SharedPreferences prefs = getPreferences(ctx);
        prefs.edit().putString(key, value).apply();
    }

    private static void setValue(Context ctx, String key, int value){
        SharedPreferences prefs = getPreferences(ctx);
        prefs.edit().putInt(key, value).apply();
    }

    private static String getStringValue(Context ctx, String key, String value){
        SharedPreferences prefs = getPreferences(ctx);
        return prefs.getString(key, value);
    }
    private static int getIntValue(Context ctx, String key, int value){
        SharedPreferences prefs = getPreferences(ctx);
        return prefs.getInt(key, value);
    }

    private static SharedPreferences getPreferences(Context ctx){
        return ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

}
