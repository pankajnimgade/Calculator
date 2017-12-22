package pk.nimgade.calculator.presenter;

import javax.inject.Inject;

import pk.nimgade.calculator.view.IMainActivityView;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    @Inject
    public IMainActivityView iMainActivityView;



    public MainActivityPresenter() {
    }

    @Override
    public void compute() {

    }
}
