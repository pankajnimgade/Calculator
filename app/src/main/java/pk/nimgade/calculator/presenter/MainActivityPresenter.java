package pk.nimgade.calculator.presenter;

import pk.nimgade.calculator.model.IMainActivityModel;
import pk.nimgade.calculator.view.IMainActivityView;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    public IMainActivityView view;

    private IMainActivityModel model;

    public MainActivityPresenter() {

    }

    @Override
    public void setView(IMainActivityView iMainActivityView) {
        this.view = iMainActivityView;
    }

    @Override
    public void compute() {

    }
}
