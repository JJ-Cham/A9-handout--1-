/**
 * Class to implement tree conversions
 *
 * @author JJ
 * @version Fall 2025
 */
public class Conversion {

    /** Public wrapper: Converts a sorted array to a balanced BST */
    public static <T extends Comparable<T>> BST<T> arrayToBST(T[] arr) {
        return arrayToBSTRec(arr, 0, arr.length);
    }

    /** Recursive helper: builds subtree using arr[low..high-1] */
    private static <T extends Comparable<T>> BST<T> arrayToBSTRec(T[] arr, int low, int high) {
        if (low >= high) return null;

        int mid = (low + high) / 2;
        BST<T> root = new BST<>(arr[mid]);

        // Build left subtree
        BST<T> left = arrayToBSTRec(arr, low, mid);
        root.setLeft(left);
        if (left != null) left.setParent(root);

        // Build right subtree
        BST<T> right = arrayToBSTRec(arr, mid + 1, high);
        root.setRight(right);
        if (right != null) right.setParent(root);

        return root;
    }

    // =======================================================
    // PHASE 2: BST to doubly linked list (destroying the tree)
    // =======================================================

    /**
     * Converts a BST to a doubly linked list (DLL)
     * in sorted inorder order, reusing existing nodes.
     */
    public static <T extends Comparable<T>> DLL<T> binaryTreeToDLL(BST<T> root) {
        if (root == null) return new DLL<>();

        // Recursively convert left and right
        DLL<T> leftList = binaryTreeToDLL((BST<T>) root.getLeft());
        DLL<T> rightList = binaryTreeToDLL((BST<T>) root.getRight());

        // Disconnect from tree
        root.setLeft(null);
        root.setRight(null);

        // Single-node middle list
        DLL<T> middle = new DLL<>(root, root);

        // Merge in correct order
        return concatenate(leftList, middle, rightList);
    }

    /** Merge A + B + C into one DLL */
    private static <T extends Comparable<T>> DLL<T> concatenate(DLL<T> A, DLL<T> B, DLL<T> C) {
        DLL<T> AB = link(A, B);
        return link(AB, C);
    }

    /** Connect L1.tail ↔ L2.head */
    private static <T extends Comparable<T>> DLL<T> link(DLL<T> L1, DLL<T> L2) {
        if (L1.isEmpty()) return L2;
        if (L2.isEmpty()) return L1;

        // Connect
        L1.getTail().setRight(L2.getHead());
        L2.getHead().setLeft(L1.getTail());

        return new DLL<>(L1.getHead(), L2.getTail());
    }
}

