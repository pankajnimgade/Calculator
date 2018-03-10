package pk.nimgade.calculator.mockito.list;

import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.LinkedList;

/**
 * Created by Pankaj Nimgade on 3/10/2018.
 */

public class MockLinkedListTest {

    @Test
    public void testMockLinkedList(){
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }
}
