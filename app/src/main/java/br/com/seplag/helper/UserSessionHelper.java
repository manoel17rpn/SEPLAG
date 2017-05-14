package br.com.seplag.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import br.com.seplag.view.MainActivity;

/**
 * Created by Manoel Neto on 30/04/2017.
 */

public class UserSessionHelper {
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "USER";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "Name";

    public static final String KEY_ID = "ID";

    // Email address (make variable public to access from outside)
    public static final String KEY_PHONE = "Phone";

    private static final String KEY_SCORE = "Score";

    public static final String KEY_NEIGHBORHOOD = "Neighborhood";

    public static final String KEY_OFFICE = "Office";

    // Constructor
    public UserSessionHelper(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void UpdateScore(String new_score){
        editor.putString(KEY_SCORE, new_score);
        editor.commit();
    }

    //Create login session
    public void createUserLoginSession(String name, String phone, String neighborhood, String score, int id, String office){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in preferences
        editor.putString(KEY_NAME, name);

        // Storing email in preferences
        editor.putString(KEY_PHONE,  phone);

        editor.putString(KEY_NEIGHBORHOOD, neighborhood);

        editor.putString(KEY_SCORE, score);

        editor.putInt(KEY_ID, id);

        editor.putString(KEY_OFFICE, office);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));

        user.put(KEY_NEIGHBORHOOD, pref.getString(KEY_NEIGHBORHOOD, null));

        user.put(KEY_OFFICE, pref.getString(KEY_OFFICE, null));

        user.put(KEY_SCORE, pref.getString(KEY_SCORE, null));

        user.put(KEY_ID, Integer.toString(pref.getInt(KEY_ID, 0)));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}