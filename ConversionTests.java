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

    // ---------------------------------------------------------
    // arrayToBST tests
    // ---------------------------------------------------------

    @Test
    public void testArrayToBSTSingle() {
        Integer[] arr = {10};
        BST<Integer> root = Conversion.arrayToBST(arr);

        Integer[][] expected = {{10}};
        verifyBT(root, expected);
    }

    @Test
    public void testArrayToBSTOdd() {
        /*
           arr = [1,2,3,4,5]
           mid = 3 → root=3
                 3
               /   \
              2     5
             /     /
            1     4
        */
        Integer[] arr = {1,2,3,4,5};
        BST<Integer> root = Conversion.arrayToBST(arr);

        Integer[][] expected = {
            {3},
            {2, 5},
            {1, null, 4, null}
        };

        verifyBT(root, expected);
    }

    @Test
    public void testArrayToBSTEven() {
        /*
           arr = [1,2,3,4]
           mid = 2 → root=3
                3
               / \
              2   4
             /
            1
        */
        Integer[] arr = {1,2,3,4};
        BST<Integer> root = Conversion.arrayToBST(arr);

        Integer[][] expected = {
            {3},
            {2,4},
            {1, null, null, null}
        };

        verifyBT(root, expected);
    }

    @Test
    public void testArrayToBSTEmpty() {
        Integer[] arr = {};
        BST<Integer> root = Conversion.arrayToBST(arr);
        assertNull(root);
    }

    // ---------------------------------------------------------
    // binaryTreeToDLL tests
    // ---------------------------------------------------------

    @Test
    public void testBSTToDLLOneNode() {
        BST<Integer> t = new BST<>(42);

        DLL<Integer> dll = Conversion.binaryTreeToDLL(t);

        assertEquals(42, (int) dll.getHead().getData());
        assertEquals(42, (int) dll.getTail().getData());
        assertNull(dll.getHead().getLeft());
        assertNull(dll.getTail().getRight());
    }

    @Test
    public void testBSTToDLLInorder() {
        /*
                4
              /   \
             2     6
            / \   / \
           1  3  5   7
        */

        BST<Integer> t = new BST<>(4);
        t.insert(2);
        t.insert(6);
        t.insert(1);
        t.insert(3);
        t.insert(5);
        t.insert(7);

        DLL<Integer> dll = Conversion.binaryTreeToDLL(t);

        Object[] forward = dllForward(dll);
        Object[] backward = dllBackward(dll);

        assertArrayEquals(new Object[]{1,2,3,4,5,6,7}, forward);
        assertArrayEquals(new Object[]{7,6,5,4,3,2,1}, backward);

        // check head/tail
        assertEquals(1, dll.getHead().getData().intValue());
        assertEquals(7, dll.getTail().getData().intValue());
    }

    @Test
    public void testBSTToDLLEmptyTree() {
        DLL<Integer> dll = Conversion.binaryTreeToDLL(null);

        assertTrue(dll.isEmpty());
        assertNull(dll.getHead());
        assertNull(dll.getTail());
    }

    @Test
    public void testBSTNodesDisconnected() {
        /*
            2
           /
          1
        */
        BST<Integer> t = new BST<>(2);
        t.insert(1);

        DLL<Integer> dll = Conversion.binaryTreeToDLL(t);

        BinaryTree<Integer> n1 = dll.getHead();
        BinaryTree<Integer> n2 = dll.getTail();

        // ensure tree links removed
        assertNull(n1.getLeft());
        assertEquals(n2, n1.getRight());

        assertEquals(n1, n2.getLeft());
        assertNull(n2.getRight());
    }

    // ---------------------------------------------------------
    // verifyBT helper reused from BSTTests
    // ---------------------------------------------------------

    private static <T> void verifyBT(BinaryTree<? extends T> t, T[][] contents) {
        for (int i = 0; i <= contents.length; i++) {
            int nj = (int) Math.pow(2, i);
            for (int j = 0; j < nj; j++) {
                int h = (int) Math.pow(2, i - 1);
                int k = j;
                BinaryTree<?> node = t;

                while (h > 0 && node != null) {
                    if (k >= h) node = node.getRight();
                    else node = node.getLeft();
                    k = k % h;
                    h /= 2;
                }

                if ((i == contents.length || contents[i][j] == null) && node != null) {
                    fail("Row " + i + " position " + j +
                            " should be null but found data: " + node.getData());
                } else if (i < contents.length && contents[i][j] != null) {
                    if (node == null) {
                        fail("Row " + i + " position " + j +
                                " should be " + contents[i][j] + " but found null");
                    } else {
                        assertEquals(contents[i][j], node.getData());
                    }
                }
            }
        }
    }
}
}
