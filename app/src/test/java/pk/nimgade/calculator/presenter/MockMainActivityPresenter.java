package pk.nimgade.calculator.presenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pk.nimgade.calculator.view.IMainActivityView;

/**
 * Created by Pankaj Nimgade on 3/10/2018.
 */
public class MockMainActivityPresenter implements IMainActivityPresenter {
    @Before
    public void setUp() {
        System.out.println("set up");
    }

    @After
    public void tearDown() {
        System.out.println("tear down");
    }

    @Override
    public void setView(IMainActivityView iMainActivityView) {
        System.out.println("setView");
    }

    @Override
    public void compute() {
        System.out.println("compute");
    }

    @Override
    public void inputCharacter(String inputCharacter) {
        System.out.println("inputCharacter");
    }

    @Override
    public void divideByZeroOccurred(@NotNull String divideByZeroError) {
        System.out.println("divideByZeroOccurred");
    }

    @Override
    public void deleteLastCharacter() {
        System.out.println("deleteLastCharacter");
    }


    @Override
    public void setUpdatedInput(@Nullable String equationFromInputText) {
        System.out.println("setUpdatedInput");
    }

    @Test
    public void clearAll() {
        System.out.println("clearAll");
    }

}