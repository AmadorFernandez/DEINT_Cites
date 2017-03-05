package com.amador.cites.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amador.cites.R;
import com.amador.cites.interfaces.IActivityLoader;

/**
 * Created by amador on 5/03/17.
 */

public class Home_Fragment extends Fragment {

    private Button btnPreferences, btnClient, btnCites;
    private IActivityLoader activityLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        btnCites = (Button)rootView.findViewById(R.id.btnCites);
        btnPreferences = (Button)rootView.findViewById(R.id.btnPreferences);
        btnClient = (Button)rootView.findViewById(R.id.btnClien);

        btnCites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityLoader.loadListCites();
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityLoader.loadListClient();
            }
        });

        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityLoader.loadPreferences();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityLoader = (IActivityLoader)activity;
    }
}
