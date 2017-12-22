package pk.nimgade.calculator;

import android.app.Application;

import pk.nimgade.calculator.dagger.MainActivityComponent;

/**
 * Created by Pankaj Nimgade on 12/22/2017.
 */

public class StartUp extends Application {

    private MainActivityComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
