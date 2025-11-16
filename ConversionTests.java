import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for array/BST/DLL conversion functions.
 *
 * @author JJ Cham
 * @version Fall 2025
 */
public class ConversionTests {
    /** Helper method: verify that two arrays contain the same sequence. */
    private static <T> void verifyArray(T[] expected, T[] actual) {
        assertEquals("Array lengths differ", expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Mismatch at position " + i, expected[i], actual[i]);
        }
    }

    /** Helper method: verify that DLL nodes and data match expected array. */
    private static <T> void verifyList(DLL<T> list, T[] arr) {
        if (arr.length == 0) {
            assertNull("Head should be null for empty list", list.getHead());
            assertNull("Tail should be null for empty list", list.getTail());
        } else {
            assertNull("Head's previous should be null", list.getHead().getLeft());
            assertNull("Tail's next should be null", list.getTail().getRight());

            for (int i = 0; i < arr.length; i++) {
                NodeDL<T> pos = list.getHead();
                for (int j = 0; j < i; j++) pos = pos.getRight();

                NodeDL<T> pos2 = list.getTail();
                for (int j = 0; j < arr.length - 1 - i; j++) pos2 = pos2.getLeft();

                assertSame("Node mismatch at position " + i, pos, pos2);
                assertEquals("Value mismatch at position " + i, arr[i], pos.getData());
            }
        }
    }
}
