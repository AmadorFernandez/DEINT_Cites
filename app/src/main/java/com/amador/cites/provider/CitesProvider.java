package com.amador.cites.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.amador.cites.database.DataBaseHelper;

public class CitesProvider extends ContentProvider {

    private SQLiteDatabase database;
    private static final int CITE = 1;
    private static final int CLIENT = 2;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(CitesProviderContract.AUTORITY, CitesProviderContract.ClienEntry.CONTENT_PATH, CLIENT);
        uriMatcher.addURI(CitesProviderContract.AUTORITY, CitesProviderContract.CitesEntry.CONTENT_PATH, CITE);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int result = -1;

        switch (uriMatcher.match(uri)){

            case CITE:
               result = database.delete(CitesProviderContract.CitesEntry.CONTENT_PATH, selection, selectionArgs);
                break;
            case CLIENT:
                result = database.delete(CitesProviderContract.ClienEntry.CONTENT_PATH, selection, selectionArgs);
                break;
        }

        if(result > 0){

            getContext().getContentResolver().notifyChange(uri, null);
        }


        return result;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri newUri = null;
        long result = -1;

        switch (uriMatcher.match(uri)){

            case CITE:
                result = database.insert(CitesProviderContract.CitesEntry.CONTENT_PATH ,null, values);
                break;
            case CLIENT:
                result = database.insert(CitesProviderContract.ClienEntry.CONTENT_PATH, null, values);
                break;
        }

        if(result != -1){


            newUri = ContentUris.withAppendedId(uri, result);
            getContext().getContentResolver().notifyChange(newUri, null);

        }


        return newUri;
    }

    @Override
    public boolean onCreate() {

        database = DataBaseHelper.getInstance().getDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)){

            case CLIENT:
                cursor = database.query(CitesProviderContract.ClienEntry.CONTENT_PATH, projection, selection, selectionArgs, null,null
                ,sortOrder);
                break;
            case CITE:
                queryBuilder.setTables(CitesProviderContract.CitesEntry.JOIN_CLIEN);
                queryBuilder.setDistinct(true);
                if(selection != null){

                    queryBuilder.appendWhere(selection);
                }
                cursor = queryBuilder.query(database,projection,null,selectionArgs,null,null,sortOrder);
                break;
        }


        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int result = -1;

        switch (uriMatcher.match(uri)){

            case CITE:
                result = database.update(CitesProviderContract.CitesEntry.CONTENT_PATH, values, selection, selectionArgs);
                break;
            case CLIENT:
                result = database.update(CitesProviderContract.ClienEntry.CONTENT_PATH, values, selection, selectionArgs);
                break;
        }

        if(result > 0){

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return result;
    }
}
