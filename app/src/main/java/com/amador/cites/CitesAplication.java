package com.amador.cites;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.amador.cites.services.CitesService;

/**
 * Created by amador on 5/03/17.
 */

public class CitesAplication extends Application {

    private static Context context;

    public static Context getContext(){

       return context;
    }

    public CitesAplication(){

        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        startService(new Intent(context, CitesService.class));
    }
}
