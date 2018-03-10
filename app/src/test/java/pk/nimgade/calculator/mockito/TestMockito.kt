package pk.nimgade.calculator.mockito

import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by Pankaj Nimgade on 3/10/2018.
 */
class TestMockito {

    @Test
    fun testMockito(){

        var mockedList = mock(List::class.java)
        println(mockedList.isEmpty())
    }
}