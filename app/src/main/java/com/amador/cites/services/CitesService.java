package com.amador.cites.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.amador.cites.provider.CitesProviderContract;
import com.amador.cites.receiber.ReceiberCites;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CitesService extends IntentService {

    public CitesService() {
        super("AnyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                String actualTime;
                String actualDate;
                int count = 0;
                String where = "time("+ CitesProviderContract.CitesEntry.TIME_START+") >= time(?) AND date("+ CitesProviderContract.CitesEntry.DATE+") = date(?)";
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                actualTime = timeFormat.format(new Date(System.currentTimeMillis()));
                actualDate = dateFormat.format(new Date(System.currentTimeMillis()));
                String[] whereParams = {actualTime, actualDate};
                Cursor cursor = getContentResolver().query(CitesProviderContract.CitesEntry.CONTENT_URI,
                        CitesProviderContract.CitesEntry.PROJECTIONS, where, whereParams, null);
                count = cursor.getCount();

                if(count > 0){

                    Intent intent1 = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt(ReceiberCites.RECOVERY_COUNT_CITES, count);
                    intent1.putExtras(bundle);
                    intent1.setAction(ReceiberCites.ACTION_CITES_TODAY);
                    sendBroadcast(intent1);
                }

                handler.postDelayed(this, 10000);

            }
        }, 10000);

        return START_STICKY;
    }
}
