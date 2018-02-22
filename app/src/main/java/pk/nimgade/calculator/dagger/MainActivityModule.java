package pk.nimgade.calculator.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pk.nimgade.calculator.model.IMainActivityModel;
import pk.nimgade.calculator.model.MainActivityModel;
import pk.nimgade.calculator.presenter.IMainActivityPresenter;
import pk.nimgade.calculator.presenter.MainActivityPresenter;

/**
 * Created by Pankaj Nimgade on 12/22/2017.
 */

@Module
public class MainActivityModule {

    @Provides
    @Singleton
    IMainActivityPresenter getMainActivityPresenter(IMainActivityModel model) {
        return new MainActivityPresenter(model);
    }

    @Provides
    @Singleton
    IMainActivityModel getIMainActivityModel(){
        return new MainActivityModel();
    }
}
