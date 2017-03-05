package com.amador.cites.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
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
import com.amador.cites.interfaces.IActivityLoader;
import com.amador.cites.interfaces.IDataBaseCrud;
import com.amador.cites.interfaces.IView;
import com.amador.cites.model.Cite;
import com.amador.cites.provider.CitesProviderContract;

/**
 * Created by amador on 5/03/17.
 */

public class ListCites_Fragment extends Fragment implements IView, IDataBaseCrud {

    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private CoordinatorLayout parent;
    private IActivityLoader activityLoader;
    private ListCiteAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_cites_fragment, container, false);

        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fabAddCite);
        listView = (ListView)rootView.findViewById(R.id.listCites);
        adapter = new ListCiteAdapter(getContext(), this);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        parent = (CoordinatorLayout)rootView.findViewById(R.id.parent);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(verifyCountClient()) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(RECOVERY_MODEL, new Cite());
                    bundle.putInt(RECOVERY_ORDER, NEW);
                    activityLoader.loadFrmCites(bundle);

                }else {

                    showMessage(getString(R.string.not_client));
                }
            }
        });

        return rootView;
    }


    private boolean verifyCountClient(){

        Cursor cursor = getActivity().getContentResolver().query(CitesProviderContract.ClienEntry.CONTENT_URI,
                CitesProviderContract.ClienEntry.PROJECTIONS, null, null, null);

        return cursor.getCount() > 0;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Cite cite = adapter.getCiteAtPosition(info.position);

        switch (item.getItemId()){

            case R.id.action_delete:
                showDialogDelete(cite);
                break;
            case R.id.action_update:
                Bundle bundle = new Bundle();
                bundle.putParcelable(RECOVERY_MODEL, cite);
                bundle.putInt(RECOVERY_ORDER, UPDATE);
                activityLoader.loadFrmCites(bundle);
                break;
        }

        return super.onOptionsItemSelected(item);


    }

    public void createOrUpdateCite(Bundle bundle){

        int order = bundle.getInt(RECOVERY_ORDER);
        Cite cite = bundle.getParcelable(RECOVERY_MODEL);

        switch (order){

            case NEW:
                adapter.addCite(cite);
                break;
            case UPDATE:
                adapter.updateCite(cite);
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

    void showDialogDelete(final Cite cite){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.sure_delete));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                adapter.deleteCite(cite.getId());
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
