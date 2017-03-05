package com.amador.cites.receiber;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;

import com.amador.cites.HomeActivity;
import com.amador.cites.R;

public class ReceiberCites extends BroadcastReceiver {

    public static final String ACTION_CITES_TODAY = "com.amador.cites.CITES_TODAY";
    public static final String RECOVERY_COUNT_CITES = "cites";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder noty = new NotificationCompat.Builder(context);
        noty.setAutoCancel(true);
        int count = intent.getExtras().getInt(RECOVERY_COUNT_CITES);
        noty.setContentText(context.getString(R.string.today)+" "+String.valueOf(count)+" "+context.getString(R.string.cites_noty));
        noty.setVibrate(new long[]{1000, 1000});
        noty.setLights(Color.RED, 3000,3000);
        noty.setSmallIcon(android.R.drawable.ic_dialog_info);
        Intent i = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);
        noty.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, noty.build());

    }
}
