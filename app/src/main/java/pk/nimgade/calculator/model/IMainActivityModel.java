package pk.nimgade.calculator.model;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public interface IMainActivityModel {

    String addCharacter(String character);

    void clear();

    String calculateOrCompute();

    String getInputTextEquationBeforeComputation();

    String getEquationFromInputText();

}
