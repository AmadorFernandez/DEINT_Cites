package com.amador.cites.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.amador.cites.R;
import com.amador.cites.adapters.SpinnerAdapter;
import com.amador.cites.interfaces.IActivityLoader;
import com.amador.cites.interfaces.IDataBaseCrud;
import com.amador.cites.interfaces.IView;
import com.amador.cites.model.Cite;
import com.amador.cites.utils.Validate;

/**
 * Created by amador on 5/03/17.
 */

public class FrmCites_Fragment extends Fragment implements IView {

    private LinearLayout parent;
    private EditText edtDate, edtTimeStart, edtTimeEnd;
    private Spinner spClient;
    private Button btnSave;
    private IActivityLoader activityLoader;
    private SpinnerAdapter adapter;
    private Cite cite;
    private int order;

    public static FrmCites_Fragment newInstance(Bundle bundle) {

        FrmCites_Fragment fragment = new FrmCites_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frm_cites_fragment, container, false);

        parent = (LinearLayout)rootView.findViewById(R.id.parentCites);
        adapter = new SpinnerAdapter(getContext());
        spClient = (Spinner)rootView.findViewById(R.id.spinner);
        spClient.setAdapter(adapter);
        edtDate = (EditText)rootView.findViewById(R.id.edtDate);
        edtTimeEnd = (EditText)rootView.findViewById(R.id.edtTimeEnd);
        edtTimeStart = (EditText)rootView.findViewById(R.id.edtTimeStart);
        cite = getArguments().getParcelable(IDataBaseCrud.RECOVERY_MODEL);
        order = getArguments().getInt(IDataBaseCrud.RECOVERY_ORDER);
        btnSave = (Button)rootView.findViewById(R.id.btnSaveCite);

        if(order == IDataBaseCrud.UPDATE){

            spClient.setSelection(adapter.getPositionClientByIdClient(cite.getIdClient()));
            edtTimeStart.setText(cite.getTimeStart());
            edtTimeEnd.setText(cite.getTimeEnd());
            edtDate.setText(cite.getDate());
        }


        spClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                cite.setIdClient(l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Validate.validateFormatTime(edtTimeEnd.getText().toString()) || !Validate.validateFormatTime(edtTimeStart.getText().toString())){

                    showMessage(getString(R.string.invalid_format_time));

                }else if(!Validate.validateFormatDate(edtDate.getText().toString())){

                    showMessage(getString(R.string.invalid_format_date));

                }else if(!Validate.validateTimesCite(edtTimeStart.getText().toString(), edtTimeEnd.getText().toString())){

                    showMessage(getString(R.string.invalid_time_cite));

                }else {

                    cite.setDate(edtDate.getText().toString());
                    cite.setTimeEnd(edtTimeEnd.getText().toString());
                    cite.setTimeStart(edtTimeStart.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(IDataBaseCrud.RECOVERY_MODEL, cite);
                    bundle.putInt(IDataBaseCrud.RECOVERY_ORDER, order);
                    activityLoader.loadListCites(bundle);
                }
            }
        });



        return rootView;
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
