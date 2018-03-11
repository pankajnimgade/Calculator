package pk.nimgade.calculator.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import pk.nimgade.calculator.presenter.IMainActivityPresenter
import pk.nimgade.calculator.view.IMainActivityView
import java.math.BigDecimal
import java.util.*

/**
 * Created by Pankaj Nimgade on 12/21/2017.
 */
class MainActivityModelTest {

    private val model = MainActivityModel()


    @Before
    fun setUp() {
        val presenter = mock(IMainActivityPresenter::class.java)
        Mockito.`when`(presenter.clearAll()).then { println("Clear All") }
        Mockito.`when`(presenter.setView(mock(IMainActivityView::class.java))).then {
            ("SetView")
        }
        Mockito.`when`(presenter.clearAll()).then { println("Clear All") }
        Mockito.`when`(presenter.deleteLastCharacter()).then { println("deleteLastCharacter") }
        Mockito.`when`(presenter.setUpdatedInput("")).then {

            ("setUpdatedInput")
        }
        Mockito.`when`(presenter.inputCharacter("")).then {
            println("input String")
        }
        Mockito.`when`(presenter.divideByZeroOccurred("")).then {

            ("divide by zero occurred")
        }
        model.setPresenter(presenter)
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkEquationAtEveryStep() {

        val listOfInputText = arrayListOf("12-", "123*", "61+00", "/745", "18*")
        model.clear()
        for (text in listOfInputText) {
            println(text)
            for (character in text) {
                model.addCharacter(character.toString())
            }
            val output = model.equationFromInputText
            println(output)

            println("\n#########\n")
        }
    }

    @Test
    fun checkEquationText() {
        val listOfEquationText = ArrayList<String>()

        listOfEquationText.add("1123+654+987/5*8-96+64/*/8-965+-*/59+8*9")
        listOfEquationText.add("9*/-+95/**-+648/*+69*/547/+69--*65/")
        listOfEquationText.add("+-*5+*8/4+/7-*8+5/-7484-*8/614-*78/46814-")
        listOfEquationText.add("123*-12")


        for (text in listOfEquationText) {
            println(text)
            model.clear()
            for (_char in text.toCharArray()) {
                model.addCharacter(_char + "")
            }
            println(model.equationFromInputText)
            println()
        }
    }

    @Test
    fun testEquateAddition() {
        println("MainActivityModelAndroidTest")
        val input = "123 + 321"
        model.clear()
        for (char in input) {
            model.addCharacter(char.toString())
        }
        val calculateOrCompute = model.calculateOrCompute()
        println("MainActivityModelAndroidTest")
        println(calculateOrCompute)
    }

    @Test
    fun testCheckForDivideByZero() {
        println("testCheckForDivideByZero")
        val model = MainActivityModel()
        val presenter = mock(IMainActivityPresenter::class.java)
        Mockito.`when`(presenter.clearAll()).then { println("Clear All") }
        Mockito.`when`(presenter.setView(mock(IMainActivityView::class.java))).then {
            ("SetView")
        }
        Mockito.`when`(presenter.clearAll()).then { println("Clear All") }
        Mockito.`when`(presenter.deleteLastCharacter()).then { println("deleteLastCharacter") }
        Mockito.`when`(presenter.setUpdatedInput("")).then {

            ("setUpdatedInput")
        }
        Mockito.`when`(presenter.inputCharacter("")).then {
            println("input String")
        }
        Mockito.`when`(presenter.divideByZeroOccurred("")).then {

            ("divide by zero occurred")
        }
        model.setPresenter(presenter)
        val input = "123 / 0"
        for (char in input) {
            model.addCharacter(char.toString())
        }
        val calculateOrCompute = model.calculateOrCompute()
        println(calculateOrCompute)
    }

    @Test
    fun testBigDecimalZero() {
        val zero = BigDecimal.ZERO
        println("BigDecimal.ZERO: $zero")

        val zeroWithDecimal = BigDecimal("0.0")
        println("BigDecimal(0.0): ${zeroWithDecimal.toPlainString()}")

        println("zero == zeroWithDecimal:  ${zero === zeroWithDecimal}")
    }

    @Test
    fun testCheckForDivideByZeroDecimal() {
        println("testCheckForDivideByZero")

        val input = "123 / 0.0"
        model.clear()
        for (char in input) {
            model.addCharacter(char.toString())
        }
        val calculateOrCompute = model.calculateOrCompute()
        println(calculateOrCompute)
    }

    @Test
    fun testAddPeriod() {
        model.clear()
        val input = "1 + ."
        for (char in input) {
            model.addCharacter(char.toString())
        }
        println(model.calculateOrCompute())
    }

    @Test
    fun testSubtractPeriod() {
        model.clear()
        val input = "1 + ."
        for (char in input) {
            model.addCharacter(char.toString())
        }
        println(model.calculateOrCompute())
    }

    @Test
    fun testMultiplyPeriod() {
        model.clear()
        val input = "1 * ."
        for (char in input) {
            model.addCharacter(char.toString())
        }
        println(model.calculateOrCompute())
    }

    @Test
    fun testDividePeriod() {
        model.clear()
        val input = "1 / ."
        for (char in input) {
            model.addCharacter(char.toString())
        }
        println(model.calculateOrCompute())
    }

    @Test
    fun testInputTextWithDecimal() {
        model.clear()
        val input = "1 . 3"
        for (char in input) {
            model.addCharacter(char.toString())
        }
        println(model.calculateOrCompute())
    }

    @Test
    fun testInputTextWithOperatorAfterDecimal() {
        model.clear()
        val input = "3 . + 2"
        for (char in input) {
            model.addCharacter(char.toString())
        }
        println(model.calculateOrCompute())
    }

    @Test
    fun testMultiTextInput() {
        val inputList = mutableListOf("12/1*24-64+56.31+1",
                "0.2*54/54*5-64.6/.",
                "0*5",
                "0/5",
                "0-5",
                "0+5",
                "215.21*5",
                "2.0.5 +1",
                "2.0*5",
                ".*5",
                "/*5",
                "3.24+6",
                "6*6-6",
                "-6/*43.2.+.-",
                "/*43.2.+.-*/535",
                "3.24+6*6-6/*43.2.+.-*/535")

        for (inputText in inputList) {
            model.clear()
            for (char in inputText) {
                model.addCharacter(character = char.toString())
            }
            println(model.calculateOrCompute())
        }
    }
}
