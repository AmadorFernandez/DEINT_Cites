package com.amador.cites;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amador.cites.fragment.FrmCites_Fragment;
import com.amador.cites.fragment.FrmClient_Fragment;
import com.amador.cites.fragment.Home_Fragment;
import com.amador.cites.fragment.ListCites_Fragment;
import com.amador.cites.fragment.ListClient_Fragment;
import com.amador.cites.fragment.Preferences_Fragment;
import com.amador.cites.interfaces.IActivityLoader;
import com.amador.cites.preferences.Preferences;

public class HomeActivity extends AppCompatActivity implements IActivityLoader {

    private Home_Fragment home_fragment;
    private ListCites_Fragment listCites_fragment;
    private ListClient_Fragment listClient_fragment;
    private Preferences_Fragment preferences_fragment;
    public static final String TAG_HOME = "home";
    public static final String TAG_LIST_CLIENT = "list_client";
    public static final String TAG_LIST_CITES = "list_cites";
    public static final String TAG_PREFERENCES = "preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Preferences preferences = new Preferences(this);

        if(preferences.getName().isEmpty()){

            showDialogPreferences();

        }else {

          loadMenu();
        }

    }

    private void showDialogPreferences(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.user_no_registed));

        builder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                loadPreferences();

            }
        }).show();
    }

    @Override
    public void loadMenu() {

        home_fragment = (Home_Fragment)getSupportFragmentManager().findFragmentByTag(TAG_HOME);

        if(home_fragment == null){

            home_fragment = new Home_Fragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, home_fragment, TAG_HOME).commit();
    }

    @Override
    public void loadListClient() {

        listClient_fragment = (ListClient_Fragment)getSupportFragmentManager().findFragmentByTag(TAG_LIST_CLIENT);

        if(listClient_fragment == null){

            listClient_fragment = new ListClient_Fragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, listClient_fragment, TAG_HOME).addToBackStack(null).commit();
    }

    @Override
    public void loadListCites() {

        listCites_fragment = (ListCites_Fragment)getSupportFragmentManager().findFragmentByTag(TAG_LIST_CITES);

        if(listCites_fragment == null){

            listCites_fragment = new ListCites_Fragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, listCites_fragment, TAG_HOME).addToBackStack(null).commit();
    }

    @Override
    public void loadListClient(Bundle bundle) {

        getSupportFragmentManager().popBackStack();
        listClient_fragment.createOrUpdateClient(bundle);
    }

    @Override
    public void loadListCites(Bundle bundle) {

        getSupportFragmentManager().popBackStack();
        listCites_fragment.createOrUpdateCite(bundle);
    }

    @Override
    public void loadFrmClient(Bundle bundle) {

        FrmClient_Fragment frmClientFragment = FrmClient_Fragment.newInstance(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, frmClientFragment, TAG_PREFERENCES).addToBackStack(null).commit();
    }

    @Override
    public void loadFrmCites(Bundle bundle) {

        FrmCites_Fragment frmCites_fragment = FrmCites_Fragment.newInstance(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, frmCites_fragment, TAG_PREFERENCES).addToBackStack(null).commit();
    }

    @Override
    public void actionPopHup() {

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void loadPreferences() {

        preferences_fragment = (Preferences_Fragment)getSupportFragmentManager().findFragmentByTag(TAG_PREFERENCES);

        if(preferences_fragment == null){

            preferences_fragment = new Preferences_Fragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, preferences_fragment, TAG_PREFERENCES).addToBackStack(null).commit();
    }
}
