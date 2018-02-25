package pk.nimgade.calculator.presenter;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        this.model.setPresenter(this);
    }

    @Override
    public void setView(IMainActivityView iMainActivityView) {
        this.view = iMainActivityView;
    }

    @Override
    public void compute() {
        Log.d(TAG, "compute: ");
        String result = model.calculateOrCompute();
        view.setInputData(result);
        view.setInputEquationTextData(model.getInputTextEquationBeforeComputation());
    }

    @Override
    public void inputCharacter(String inputCharacter) {
        String inputEquationDisplayText = model.addCharacter(inputCharacter);
        view.setInputData(inputEquationDisplayText);
    }

    @Override
    public void divideByZeroOccurred(@NotNull String divideByZeroError) {
        view.showErrorMessage(divideByZeroError);
    }

    @Override
    public void deleteLastCharacter() {
        Log.d(TAG, "deleteLastCharacter(): ");
        this.model.deleteLastCharacter();
    }

    @Override
    public void setUpdatedInput(@Nullable String equationFromInputText) {
        this.view.setOutputResult(equationFromInputText+"");
        this.view.setInputData(equationFromInputText+"");
    }

    @Override
    public void clearAll() {
       this.model.clearCompleteEquation();
    }
}
