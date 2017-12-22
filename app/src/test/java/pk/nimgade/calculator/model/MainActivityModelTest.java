package pk.nimgade.calculator.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj Nimgade on 12/21/2017.
 */
class MainActivityModelTest {

    @Test
    public void checkEquationText() {
        final List<String> listOfEquationText = new ArrayList<String>();

        listOfEquationText.add("1123+654+987/5*8-96+64/*/8-965+-*/59+8*9");
        listOfEquationText.add("9*/-+95/**-+648/*+69*/547/+69--*65/");
        listOfEquationText.add("+-*5+*8/4+/7-*8+5/-7484-*8/614-*78/46814-");
        listOfEquationText.add("123*-12");


        for (String text : listOfEquationText) {
            System.out.println(text);
            final MainActivityModel model = new MainActivityModel();
            model.clear();
            for (char _char : text.toCharArray()) {
                model.addCharacter(_char + "");
            }
            model.compute();
            model.printElements();
            System.out.println();
        }
    }
}