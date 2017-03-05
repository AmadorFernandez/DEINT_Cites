package com.amador.cites.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amador.cites.R;
import com.amador.cites.adapters.ListCiteAdapter;
import com.amador.cites.adapters.ListClientAdapter;
import com.amador.cites.interfaces.IActivityLoader;
import com.amador.cites.interfaces.IDataBaseCrud;
import com.amador.cites.interfaces.IView;
import com.amador.cites.model.Cite;
import com.amador.cites.model.Client;

/**
 * Created by amador on 5/03/17.
 */

public class ListClient_Fragment extends Fragment implements IView, IDataBaseCrud {

    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private CoordinatorLayout parent;
    private IActivityLoader activityLoader;
    private ListClientAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_client_fragment, container, false);

        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fabAddClient);
        listView = (ListView)rootView.findViewById(R.id.listClient);
        adapter = new ListClientAdapter(getContext(), this);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        parent = (CoordinatorLayout)rootView.findViewById(R.id.parent);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelable(RECOVERY_MODEL, new Client());
                bundle.putInt(RECOVERY_ORDER, NEW);
                activityLoader.loadFrmClient(bundle);
            }
        });

        return rootView;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Client client = adapter.getClientAtPosition(info.position);

        switch (item.getItemId()){

            case R.id.action_delete:
                showDialogDelete(client);
                break;
            case R.id.action_update:
                Bundle bundle = new Bundle();
                bundle.putParcelable(RECOVERY_MODEL, client);
                bundle.putInt(RECOVERY_ORDER, UPDATE);
                activityLoader.loadFrmClient(bundle);
                break;
        }

        return super.onContextItemSelected(item);
    }





    public void createOrUpdateClient(Bundle bundle){

        int order = bundle.getInt(RECOVERY_ORDER);
        Client client = bundle.getParcelable(RECOVERY_MODEL);

        switch (order){

            case NEW:
                adapter.addCLient(client);
                break;
            case UPDATE:
                adapter.updateClient(client);
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getActivity().getMenuInflater().inflate(R.menu.context_crud_menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void showMessage(String msg) {

        Snackbar.make(parent, msg, Snackbar.LENGTH_LONG).show();
    }

    void showDialogDelete(final Client client){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.sure_delete));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                adapter.deleteClient(client.getId());

            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        }).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityLoader = (IActivityLoader)activity;
    }
}
