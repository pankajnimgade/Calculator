package pk.nimgade.calculator.presenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import pk.nimgade.calculator.view.IMainActivityView;

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */

public interface IMainActivityPresenter {

    void setView(IMainActivityView iMainActivityView);

    void compute();

    void inputCharacter(String inputCharacter);

    void divideByZeroOccurred(@NotNull String divideByZeroError);

    void deleteLastCharacter();

    void setUpdatedInput(@Nullable String equationFromInputText);
}
