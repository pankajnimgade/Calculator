package pk.nimgade.calculator.view;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public interface IMainActivityView {

    char getCharacter();

    void setInputData(String inputData);

    void setInputEquationTextData(String inputData);

    void setOutputResult(String outputResult);

    void showErrorMessage(String errorMessage);

    void lastComputationEquation(String lastComputationEquation);
}
