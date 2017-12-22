package pk.nimgade.calculator.model;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public interface IMainActivityModel {

    void addCharacter(String character);

    void compute();

    String getEquationFromInputText();

    void clear();

}
