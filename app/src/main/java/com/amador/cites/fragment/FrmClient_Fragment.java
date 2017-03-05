package com.amador.cites.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.amador.cites.R;
import com.amador.cites.interfaces.IActivityLoader;
import com.amador.cites.interfaces.IDataBaseCrud;
import com.amador.cites.interfaces.IView;
import com.amador.cites.model.Client;
import com.amador.cites.utils.Validate;

/**
 * Created by amador on 5/03/17.
 */

public class FrmClient_Fragment extends Fragment implements IView {

    private LinearLayout parent;
    private EditText edtNameClient, edtSurnamesClient, edtAddressClient, edtPhoneClient;
    private Client client;
    private Button btnSave;
    private IActivityLoader activityLoader;
    private int order;

    public static FrmClient_Fragment newInstance(Bundle bundle) {

        FrmClient_Fragment fragment = new FrmClient_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frm_client_fragment, container, false);

        btnSave = (Button)rootView.findViewById(R.id.btnSave);
        parent = (LinearLayout)rootView.findViewById(R.id.parentClient);
        edtNameClient = (EditText)rootView.findViewById(R.id.edtNameClient);
        edtSurnamesClient = (EditText)rootView.findViewById(R.id.edtSurnameClient);
        edtPhoneClient = (EditText)rootView.findViewById(R.id.edtPhone);
        edtAddressClient = (EditText)rootView.findViewById(R.id.edtAddressClient);
        client = getArguments().getParcelable(IDataBaseCrud.RECOVERY_MODEL);
        order = getArguments().getInt(IDataBaseCrud.RECOVERY_ORDER);

        edtAddressClient.setText(client.getAddress());
        edtSurnamesClient.setText(client.getSurnames());
        edtPhoneClient.setText(client.getPhone());
        edtNameClient.setText(client.getName());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validatePhone(edtPhoneClient.getText().toString()) && validateSimpleText(edtNameClient.getText().toString()) &&
                        validateSimpleText(edtAddressClient.getText().toString()) && validateSimpleText(edtSurnamesClient.getText().toString())){

                    client.setName(edtNameClient.getText().toString());
                    client.setPhone(edtPhoneClient.getText().toString());
                    client.setSurnames(edtSurnamesClient.getText().toString());
                    client.setAddress(edtAddressClient.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(IDataBaseCrud.RECOVERY_MODEL, client);
                    bundle.putInt(IDataBaseCrud.RECOVERY_ORDER, order);
                    activityLoader.loadListClient(bundle);
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
    public void showMessage(String msg) {

        Snackbar.make(parent, msg, Snackbar.LENGTH_LONG).show();
    }
}
