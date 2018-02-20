package pk.nimgade.calculator.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by Pankaj Nimgade on 12/21/2017.
 */
class MainActivityModelTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkEquationAtEveryStep() {

        val listOfInputText = arrayListOf("12-", "123*", "61+00", "/745", "18*")

        for (text in listOfInputText){
            val model:IMainActivityModel = MainActivityModel()
            println(text)
            for (character in text){
                model.addCharacter(character.toString())
            }
            val output =model.equationFromInputText
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
            val model = MainActivityModel()
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
        val model = MainActivityModel()
        val input = "123 + 321"
        for (char in input) {
            model.addCharacter(char.toString())
        }
        val calculateOrCompute = model.calculateOrCompute()
        println("MainActivityModelAndroidTest")
        println(calculateOrCompute)
    }
}