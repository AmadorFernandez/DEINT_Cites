package com.amador.cites.adapters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.amador.cites.R;
import com.amador.cites.interfaces.ILoaderID;
import com.amador.cites.interfaces.IView;
import com.amador.cites.model.Client;
import com.amador.cites.provider.CitesProviderContract;

/**
 * Created by amador on 5/03/17.
 */

public class ListClientAdapter extends CursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private IView view;

    public ListClientAdapter(Context context, IView view) {
        super(context, null, 0);
        this.context = context;
        this.view = view;
        ((Activity)context).getLoaderManager().initLoader(ILoaderID.LOADER_LIST_CLIEN, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Loader<Cursor> loader = new CursorLoader(context, CitesProviderContract.ClienEntry.CONTENT_URI,
                CitesProviderContract.ClienEntry.PROJECTIONS, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        swapCursor(cursor);
        cursor.setNotificationUri(context.getContentResolver(), CitesProviderContract.ClienEntry.CONTENT_URI);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        swapCursor(null);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_client, viewGroup, false);
        ListClientHolder holder = new ListClientHolder();
        holder.txvNameClient = (TextView)view.findViewById(R.id.txvCLientName);
        holder.txvPhoneClient = (TextView)view.findViewById(R.id.txvCLientPhone);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ListClientHolder holder = (ListClientHolder)view.getTag();
        holder.txvNameClient.setText(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.NAME)) +
        " "+cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.SURNAMES)));
        holder.txvPhoneClient.setText(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.PHONE)));
    }

    public Client getClientAtPosition(int position){

        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        Client client = new Client();

        client.setId(cursor.getLong(cursor.getColumnIndex(CitesProviderContract.ClienEntry._ID)));
        client.setName(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.NAME)));
        client.setSurnames(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.SURNAMES)));
        client.setAddress(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.ADDRESS)));
        client.setPhone(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.PHONE)));

        return client;
    }

    public void addCLient(Client client){

        ContentValues values = new ContentValues();
        values.put(CitesProviderContract.ClienEntry.ADDRESS, client.getAddress());
        values.put(CitesProviderContract.ClienEntry.NAME, client.getName());
        values.put(CitesProviderContract.ClienEntry.PHONE, client.getPhone());
        values.put(CitesProviderContract.ClienEntry.SURNAMES, client.getSurnames());
        Uri resultUri = null;

        resultUri = context.getContentResolver().insert(CitesProviderContract.ClienEntry.CONTENT_URI, values);

        if(resultUri == null){

            view.showMessage(context.getString(R.string.fail_insert));

        }else {

            view.showMessage(context.getString(R.string.insert_ok));
        }

    }

    public void updateClient(Client client){

        ContentValues values = new ContentValues();
        values.put(CitesProviderContract.ClienEntry.ADDRESS, client.getAddress());
        values.put(CitesProviderContract.ClienEntry.NAME, client.getName());
        values.put(CitesProviderContract.ClienEntry.PHONE, client.getPhone());
        values.put(CitesProviderContract.ClienEntry.SURNAMES, client.getSurnames());
        String where = CitesProviderContract.ClienEntry._ID+" = ?";
        String[] whereParams = {String.valueOf(client.getId())};
        int result = 0;

        result = context.getContentResolver().update(CitesProviderContract.ClienEntry.CONTENT_URI, values, where, whereParams);

        if(result == -1){

            view.showMessage(context.getString(R.string.fail_update));

        }else {

            view.showMessage(context.getString(R.string.update_ok));
        }
    }

    public void deleteClient(long id){

        String where = CitesProviderContract.ClienEntry._ID+" = ?";
        String[] whereParams = {String.valueOf(id)};
        int result = 0;

        result = context.getContentResolver().delete(CitesProviderContract.ClienEntry.CONTENT_URI, where, whereParams);

        if(result > 0){

            view.showMessage(context.getString(R.string.delete_ok));

        }else {

            view.showMessage(context.getString(R.string.delete_fail));
        }
    }

    class ListClientHolder{

        TextView txvNameClient, txvPhoneClient;
    }
}
