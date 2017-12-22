package pk.nimgade.calculator.dagger;

import android.app.Application;

import dagger.Component;
import pk.nimgade.calculator.view.MainActivity;

/**
 * Created by Pankaj Nimgade on 12/22/2017.
 */

@Component(modules = {MainActivityModule.class})
public interface MainActivityComponent {

    Application getApplicationContext();

    void inject(MainActivity mainActivity);
}
