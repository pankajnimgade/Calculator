package pk.nimgade.calculator.presenter;

import android.util.Log;

import javax.inject.Inject;

import pk.nimgade.calculator.model.IMainActivityModel;
import pk.nimgade.calculator.view.IMainActivityView;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public class MainActivityPresenter implements IMainActivityPresenter {
    
    private static final String TAG = "MainActivityPresenter";

    public IMainActivityView view;

    private IMainActivityModel model;


    @Inject
    public MainActivityPresenter(IMainActivityModel model) {
        this.model = model;
    }

    @Override
    public void setView(IMainActivityView iMainActivityView) {
        this.view = iMainActivityView;
    }

    @Override
    public void compute() {
        Log.d(TAG, "compute: ");
        model.calculateOrCompute();
    }

    @Override
    public void inputCharacter(String inputCharacter) {
        String inputEquationDisplayText = model.addCharacter(inputCharacter);
        view.setInputData(inputEquationDisplayText);
    }
}
