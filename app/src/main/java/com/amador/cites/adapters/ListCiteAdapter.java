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
import com.amador.cites.model.Cite;
import com.amador.cites.provider.CitesProviderContract;

/**
 * Created by amador on 5/03/17.
 */

public class ListCiteAdapter extends CursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {


    private Context context;
    private IView view;

    public ListCiteAdapter(Context context, IView view) {
        super(context, null, 0);
        this.context = context;
        this.view = view;
        ((Activity)context).getLoaderManager().initLoader(ILoaderID.LOADER_LIST_CITES, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Loader<Cursor> loader = new CursorLoader(context, CitesProviderContract.CitesEntry.CONTENT_URI,
                CitesProviderContract.CitesEntry.PROJECTIONS, null, null, null);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        swapCursor(cursor);
        cursor.setNotificationUri(context.getContentResolver(), CitesProviderContract.CitesEntry.CONTENT_URI);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        swapCursor(null);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_cites, viewGroup, false);
        ListCitesHolder holder = new ListCitesHolder();

        holder.txvDate = (TextView)view.findViewById(R.id.txvDate);
        holder.txvTimeEnd = (TextView)view.findViewById(R.id.txvTimeEnd);
        holder.txvTimeStart = (TextView)view.findViewById(R.id.txvTimeStart);
        holder.txvNameCompleteClient = (TextView)view.findViewById(R.id.txvCLientName);
        view.setTag(holder);


        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ListCitesHolder holder = (ListCitesHolder) view.getTag();

        holder.txvDate.setText(cursor.getString(cursor.getColumnIndex(CitesProviderContract.CitesEntry.DATE)));
        holder.txvTimeStart.setText("Desde las: "+cursor.getString(cursor.getColumnIndex(CitesProviderContract.CitesEntry.TIME_START)));
        holder.txvTimeEnd.setText("hasta las: "+cursor.getString(cursor.getColumnIndex(CitesProviderContract.CitesEntry.TIME_END)));
        holder.txvNameCompleteClient.setText(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.NAME)) + " "+
        cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.SURNAMES)));
    }

    public Cite getCiteAtPosition(int position){

        Cite cite = new Cite();
        Cursor cursor = getCursor();

        cursor.moveToPosition(position);

        cite.setId(cursor.getLong(CitesProviderContract.CitesEntry.POS_CITE_ID));
        cite.setCompleteNameClien(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.NAME)) + " "+
                cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.SURNAMES)));
        cite.setDate(cursor.getString(cursor.getColumnIndex(CitesProviderContract.CitesEntry.DATE)));
        cite.setTimeStart(cursor.getString(cursor.getColumnIndex(CitesProviderContract.CitesEntry.TIME_START)));
        cite.setTimeEnd(cursor.getString(cursor.getColumnIndex(CitesProviderContract.CitesEntry.TIME_END)));

        return cite;
    }

    public void addCite(Cite cite){

        Uri uriResult = null;

        ContentValues values = new ContentValues();
        values.put(CitesProviderContract.CitesEntry.CLIEN_ID, cite.getIdClient());
        values.put(CitesProviderContract.CitesEntry.DATE, cite.getDate());
        values.put(CitesProviderContract.CitesEntry.TIME_END, cite.getTimeEnd());
        values.put(CitesProviderContract.CitesEntry.TIME_START, cite.getTimeStart());
        uriResult = context.getContentResolver().insert(CitesProviderContract.CitesEntry.CONTENT_URI,values);

        if(uriResult == null){

            view.showMessage(context.getString(R.string.fail_insert));

        }else {

            view.showMessage(context.getString(R.string.insert_ok));
        }

    }

    public void updateCite(Cite cite){

        int result = 0;
        ContentValues values = new ContentValues();
        values.put(CitesProviderContract.CitesEntry.CLIEN_ID, cite.getIdClient());
        values.put(CitesProviderContract.CitesEntry.DATE, cite.getDate());
        values.put(CitesProviderContract.CitesEntry.TIME_END, cite.getTimeEnd());
        values.put(CitesProviderContract.CitesEntry.TIME_START, cite.getTimeStart());
        String where = CitesProviderContract.ClienEntry._ID+" = ?";
        String[] whereParams = {String.valueOf(cite.getId())};
        result = context.getContentResolver().update(CitesProviderContract.CitesEntry.CONTENT_URI,values, where, whereParams);

        if(result <= 0){

            view.showMessage(context.getString(R.string.fail_update));

        }else {

            view.showMessage(context.getString(R.string.update_ok));
        }
    }

    public void deleteCite(long id){

        int result = 0;
        String where = CitesProviderContract.CitesEntry._ID+" = ?";
        String[] whereParams = {String.valueOf(id)};

        result = context.getContentResolver().delete(CitesProviderContract.CitesEntry.CONTENT_URI, where, whereParams);

        if(result == 0){

            view.showMessage(context.getString(R.string.delete_fail));

        }else {

            view.showMessage(context.getString(R.string.delete_ok));
        }
    }

    class ListCitesHolder{

        TextView txvNameCompleteClient, txvDate, txvTimeStart, txvTimeEnd;
    }
}
