package pk.nimgade.calculator.mockito.list;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Pankaj Nimgade on 3/10/2018.
 */

public class MockList {

    @Test
    public void testList() {
        List mockedList = mock(List.class);
        mockedList.add("One");
        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("One");
        verify(mockedList).clear();
    }
}
