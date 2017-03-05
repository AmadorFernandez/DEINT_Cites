package com.amador.cites.adapters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.amador.cites.R;
import com.amador.cites.interfaces.ILoaderID;
import com.amador.cites.interfaces.IView;
import com.amador.cites.provider.CitesProviderContract;

/**
 * Created by amador on 5/03/17.
 */

public class SpinnerAdapter extends CursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;


    public SpinnerAdapter(Context context) {
        super(context, null,0);
        this.context = context;
        ((Activity)context).getLoaderManager().initLoader(ILoaderID.LOADER_SPINNER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Loader<Cursor> loader = new CursorLoader(context, CitesProviderContract.ClienEntry.CONTENT_URI,
                CitesProviderContract.ClienEntry.PROJECTIONS, null,null,null);

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

    public int getPositionClientByIdClient(long idClient){

        int pos = -1;
        Cursor cursor = getCursor();


        if(cursor != null) {

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                do {

                    if (cursor.getLong(cursor.getColumnIndex(CitesProviderContract.ClienEntry._ID)) == idClient) {
                        pos = cursor.getPosition();
                        break;
                    }

                } while (cursor.moveToNext());
            }
        }


        return pos;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner, viewGroup, false);

        SpinnerClientHolder holder = new SpinnerClientHolder();
        holder.exvNameClient = (TextView)view.findViewById(R.id.txv_spinner_name_client);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        SpinnerClientHolder holder = (SpinnerClientHolder) view.getTag();
        holder.exvNameClient.setText(cursor.getString(cursor.getColumnIndex(CitesProviderContract.ClienEntry.NAME)));
    }

    class SpinnerClientHolder{

        TextView exvNameClient;
    }
}
