package com.apiit.eeashopingandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserEmail(String email) {
        prefs.edit().putString("email", email).commit();
    }

    public void setPassword(String password) {
        prefs.edit().putString("password", password).commit();
    }

    public String getUserEmail() {
        String email = prefs.getString("email","no_user");
        return email;
    }
    public String getpassword() {
        String password = prefs.getString("password","");
        return password;
    }

    public void signOut(){
        prefs.edit().clear().apply();

    }

    public boolean hasUserLoggedIn(){
        if(getUserEmail() == "no_user" || getUserEmail() == null){
            return false;
        }else {
            return true;
        }
    }
}
