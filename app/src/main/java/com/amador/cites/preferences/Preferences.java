package com.amador.cites.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by amador on 5/03/17.
 */

public class Preferences {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String PREFERENCES_NAME = "name";
    public static final String PREFERENCES_COMPANY = "company";
    public static final String PREFERENCES_PHONE = "phone";
    public static final String PREFERENCES_CIF = "cif";
    public static final String PREFERENCES_LOCATION = "location";
    public static final String PREFERENCES_EMAIL = "email";

    public Preferences(Context context){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void setName(String name){

        editor.putString(PREFERENCES_NAME, name);
        editor.commit();
    }

    public void setCompany(String company){

        editor.putString(PREFERENCES_COMPANY, company);
        editor.commit();
    }

    public void setPhone(String phone){

        editor.putString(PREFERENCES_PHONE, phone);
        editor.commit();
    }

    public void setCif(String cif){

        editor.putString(PREFERENCES_CIF, cif);
        editor.commit();
    }

    public void setLocation(String location){

        editor.putString(PREFERENCES_LOCATION, location);
        editor.commit();
    }

    public void setEmail(String email){

        editor.putString(PREFERENCES_EMAIL, email);
        editor.commit();
    }

    public String getEmail(){

        return sharedPreferences.getString(PREFERENCES_EMAIL, "");
    }

    public String getName(){

        return sharedPreferences.getString(PREFERENCES_NAME, "");
    }

    public String getPhone(){

        return sharedPreferences.getString(PREFERENCES_PHONE, "");
    }

    public String getLocation(){

        return sharedPreferences.getString(PREFERENCES_LOCATION, "");
    }

    public String getCif(){

        return sharedPreferences.getString(PREFERENCES_CIF, "");
    }

    public String getCompany(){

        return sharedPreferences.getString(PREFERENCES_COMPANY, "");
    }






}
