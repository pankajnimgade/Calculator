package pk.nimgade.calculator.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import pk.nimgade.calculator.presenter.MainActivityPresenter;
import pk.nimgade.calculator.view.MainActivity;

/**
 * Created by Pankaj Nimgade on 12/22/2017.
 */

@Singleton
@Component(modules = {MainActivityModule.class})
public interface MainActivityComponent {

    MainActivityPresenter getIMainActivityPresenter();


    void inject(MainActivity mainActivity);
}
