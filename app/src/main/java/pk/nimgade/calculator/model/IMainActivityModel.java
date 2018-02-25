package pk.nimgade.calculator.model;

import pk.nimgade.calculator.presenter.IMainActivityPresenter;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public interface IMainActivityModel {

    void setPresenter(IMainActivityPresenter presenter);

    String addCharacter(String character);

    void clear();

    String calculateOrCompute();

    String getInputTextEquationBeforeComputation();

    String getEquationFromInputText();

    void deleteLastCharacter();

    void clearCompleteEquation();
}
