package com.amador.cites.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.amador.cites.R;
import com.amador.cites.interfaces.IActivityLoader;
import com.amador.cites.interfaces.IView;
import com.amador.cites.preferences.Preferences;
import com.amador.cites.utils.Validate;

/**
 * Created by amador on 5/03/17.
 */

public class Preferences_Fragment extends Fragment implements IView {

    private EditText edtName, edtEmail, edtPhone, edtLocation, edtCompany,
    edtCif;
    private Button btnSave;
    private LinearLayout parent;
    private IActivityLoader activityLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.preferences_fragment, container, false);

        edtCif = (EditText)rootView.findViewById(R.id.edtCif);
        edtName = (EditText)rootView.findViewById(R.id.edtUserName);
        edtLocation = (EditText)rootView.findViewById(R.id.edtLocation);
        edtEmail = (EditText)rootView.findViewById(R.id.edtMail);
        edtPhone = (EditText)rootView.findViewById(R.id.edtPhone);
        btnSave = (Button)rootView.findViewById(R.id.btnSaveProfile);
        edtCompany = (EditText) rootView.findViewById(R.id.edtCompany);
        parent = (LinearLayout)rootView.findViewById(R.id.parentPreferences);
        final Preferences preferences = new Preferences(getContext());

        edtPhone.setText(preferences.getPhone());
        edtEmail.setText(preferences.getEmail());
        edtLocation.setText(preferences.getLocation());
        edtCif.setText(preferences.getCif());
        edtCompany.setText(preferences.getCompany());
        edtName.setText(preferences.getName());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateEmail(edtEmail.getText().toString()) && validatePhone(edtPhone.getText().toString()) &&
                        validateSimpleText(edtCif.getText().toString()) && validateSimpleText(edtLocation.getText().toString()) &&
                        validateSimpleText(edtName.getText().toString())){

                    preferences.setCif(edtCif.getText().toString());
                    preferences.setCompany(edtCompany.getText().toString());
                    preferences.setEmail(edtEmail.getText().toString());
                    preferences.setName(edtName.getText().toString());
                    preferences.setPhone(edtPhone.getText().toString());
                    preferences.setLocation(edtLocation.getText().toString());
                    activityLoader.loadMenu();
                }
            }
        });



        return rootView;
    }

    public boolean validateEmail(String email){

        boolean result = Validate.validateEmail(email);

        if(!result){

            showMessage(getString(R.string.invalid_format_email));
        }

        return result;
    }

    public boolean validatePhone(String phone){

        boolean result = Validate.validatePhone(phone);

        if(!result){

            showMessage(getString(R.string.invalid_format_phone));
        }

        return result;
    }

    public boolean validateSimpleText(String simpleText){

        boolean result = Validate.validateSimpleText(simpleText);

        if(!result){

            showMessage(getString(R.string.text_empty));
        }

        return result;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        activityLoader = (IActivityLoader)activity;
    }

    @Override
    public void showMessage(String msg) {

        Snackbar.make(parent, msg, Snackbar.LENGTH_LONG).show();
    }
}
