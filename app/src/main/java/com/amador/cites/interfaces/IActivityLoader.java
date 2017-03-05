package com.amador.cites.interfaces;

import android.os.Bundle;

/**
 * Created by amador on 5/03/17.
 */

public interface IActivityLoader {

    void loadMenu();

    void loadListClient();

    void loadListCites();

    void loadListClient(Bundle bundle);

    void loadListCites(Bundle bundle);

    void loadFrmClient(Bundle bundle);

    void loadFrmCites(Bundle bundle);

    void actionPopHup();

    void loadPreferences();
}
