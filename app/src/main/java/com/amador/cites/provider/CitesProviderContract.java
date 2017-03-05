package com.amador.cites.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by amador on 5/03/17.
 */

public class CitesProviderContract {

    public static final String AUTORITY = "com.amador.cites";
    public static final Uri AUTORITY_URI = Uri.parse("content://"+AUTORITY);

    public static class ClienEntry implements BaseColumns{

        public static final String CONTENT_PATH = "client";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTORITY_URI, CONTENT_PATH);

        public static final String NAME = "cli_name";
        public static final String SURNAMES = "cli_surnames";
        public static final String PHONE = "cli_phone";
        public static final String ADDRESS = "cli_address";
        public static final String[] PROJECTIONS = {

             _ID, NAME, SURNAMES, PHONE, ADDRESS
        };

        public static final String SQL_CREATE = "CREATE TABLE "+CONTENT_PATH+ " ("+
                _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" TEXT,"+SURNAMES+" TEXT, "+
                PHONE+" TEXT,"+ADDRESS+" TEXT)";
        public static final String SQL_DELETE = "DROP TABLE IF EXISTS "+CONTENT_PATH;

    }


    public static class CitesEntry implements BaseColumns{

        public static final String CONTENT_PATH = "ciites";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTORITY_URI, CONTENT_PATH);
        public static final String DATE = "ci_date";
        public static final String TIME_START = "ci_time_start";
        public static final String TIME_END = "ci_time_end";
        public static final String CLIEN_ID = "ci_client_id";
        public static final int POS_CITE_ID = 0;
        public static final String[] PROJECTIONS = {

            "ci."+_ID, DATE, TIME_START, TIME_END, CLIEN_ID,
                ClienEntry.NAME, ClienEntry.SURNAMES, ClienEntry.PHONE
        };

        public static final String JOIN_CLIEN = " "+CONTENT_PATH+" ci "+"INNER JOIN "+ClienEntry.CONTENT_PATH+" cli" +
                " ON ci."+CLIEN_ID +" = cli."+_ID;

        public static final String SQL_CREATE = "CREATE TABLE "+CONTENT_PATH+" ("+
                _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+DATE+" TEXT,"+TIME_START+" TEXT,"+
                TIME_END+" TEXT,"+CLIEN_ID+" INTEGER REFERENCES "+ClienEntry.CONTENT_PATH+"("+ClienEntry._ID+") ON UPDATE CASCADE ON DELETE RESTRICT)";

        public static final String SQL_DELETE = "DROP TABLE IF EXISTS "+CONTENT_PATH;
    }



}
