import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Binary Search Tree (BST) class.
 *
 * @author JJ Cham
 * @version Fall 2025
 */
public class BSTTests {
    /** Helper method: verifies that a BinaryTree has the expected structure and contents. */
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

                // Compare expected and actual structure
                if ((i == contents.length || contents[i][j] == null) && node != null) {
                    fail("Row " + i + " position " + j +
                         " should be null but found data: " + node.getData());
                } else if (i < contents.length && contents[i][j] != null) {
                    if (node == null) {
                        fail("Row " + i + " position " + j +
                             " should be " + contents[i][j] + " but found null");
                    } else {
                        assertEquals("Row " + i + " position " + j + 
                                     " expected " + contents[i][j] + 
                                     " but got " + node.getData(),
                                     contents[i][j], node.getData());
                    }
                }
            }
        }
    }

    // Sample tests...
    @Test
    public void testBSTInsertions() {
        Integer[][] gt1 = {{5}};
        Integer[][] gt2 = {{5},{null,7}};

        BST<Integer> tree = new BST<>(5);
        verifyBT(tree, gt1);

        tree.insert(7);
        verifyBT(tree, gt2);
    }

     // =====================================================
    // INSERT TESTS
    // =====================================================

    @Test
    public void testBSTInsertions() {
        Integer[][] gt1 = {{5}};
        Integer[][] gt2 = {{5}, {3, 7}};
        Integer[][] gt3 = {{5}, {3, 7}, {null, null, 6, null}};

        BST<Integer> tree = new BST<>(5);
        verifyBT(tree, gt1);

        tree.insert(3);
        tree.insert(7);
        verifyBT(tree, gt2);

        tree.insert(6);
        verifyBT(tree, gt3);

        // duplicates should NOT change structure
        tree.insert(7);
        verifyBT(tree, gt3);
    }

    // =====================================================
    // LOOKUP TESTS
    // =====================================================

    @Test
    public void testBSTLookup() {
        BST<Integer> tree = new BST<>(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(12);

        assertTrue(tree.lookup(10));
        assertTrue(tree.lookup(5));
        assertTrue(tree.lookup(15));
        assertTrue(tree.lookup(12));

        assertFalse(tree.lookup(99));
        assertFalse(tree.lookup(-1));
    }

    // =====================================================
    // DELETE TESTS (COPY-LEFT)
    // =====================================================

    @Test
    public void testDeleteLeaf() {
        BST<Integer> t = new BST<>(10);
        t.insert(5);
        t.insert(15);

        t.delete(5); // delete leaf

        Integer[][] expected = {{10}, {null, 15}};
        verifyBT(t, expected);
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        BST<Integer> t = new BST<>(10);
        t.insert(5);
        t.insert(2); // child of leaf
        t.delete(5); // delete node that has a single child (2)

        Integer[][] expected = {{10}, {2, null}};
        verifyBT(t, expected);
    }

    @Test
    public void testDeleteNodeWithTwoChildrenCopyLeft() {
        /*
              10
             /  \
            5    15
           /
          2
        */

        BST<Integer> t = new BST<>(10);
        t.insert(5);
        t.insert(2);
        t.insert(15);

        t.delete(5);  // copy-left → replace with predecessor (2)

        Integer[][] expected = {
            {10},
            {2, 15},
            {null, null, null, null}
        };

        verifyBT(t, expected);
    }

    // =====================================================
    // ROTATION TESTS
    // =====================================================

    @Test
    public void testRightRotation() {
        /*
               10
              /
             5
        */

        BST<Integer> t = new BST<>(10);
        t.insert(5);

        // rotate right at root
        t = t.rotateRight();

        /*
             5
              \
               10
        */
        Integer[][] expected = {
            {5},
            {null, 10}
        };

        verifyBT(t, expected);
        assertEquals((Integer)10, t.getRight().getData());
    }

    @Test
    public void testLeftRotation() {
        /*
             10
               \
                15
        */
        BST<Integer> t = new BST<>(10);
        t.insert(15);

        t = t.rotateLeft();

        /*
             15
            /
           10
        */
        Integer[][] expected = {
            {15},
            {10, null}
        };

        verifyBT(t, expected);
        assertEquals((Integer)10, t.getLeft().getData());
    }
}
