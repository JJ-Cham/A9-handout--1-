/**
 * Implements binary search trees.
 *
 * @author JJ Cham
 * @version Fall 2025
 */


public class BST<E extends Comparable<E>> extends BinaryTree<E> implements BST_Ops<E> {
    //constructor
    public BST(){
        super();
    }

    public BST(E data){
        super(data);
    }

    /** Only allow BST children */
    @Override
    public void setLeft(BinaryTree<E> left) {
        if (left == null || left instanceof BST<?>) {
            super.setLeft(left);
        } else {
            throw new UnsupportedOperationException("Only BST children allowed");
        }
    }

    /** Only allow BST children */
    @Override
    public void setRight(BinaryTree<E> right) {
        if (right == null || right instanceof BST<?>) {
            super.setRight(right);
        } else {
            throw new UnsupportedOperationException("Only BST children allowed");
        }
    }

    //methods from BST_Ops interface stubs
    //goal: return the BST node containing data, or null if not found
    //? condtional operator
    @Override
    public BST<E> lookup(E data) {
        if (getData() == null) return null;

        int cmp = data.compareTo(getData());

        if (cmp == 0) return this;

        if (cmp < 0) {
            if (getLeft() == null) return null;
            return ((BST<E>) getLeft()).lookup(data);
        } else {
            if (getRight() == null) return null;
            return ((BST<E>) getRight()).lookup(data);
        }
    }

    //no duplicates allowed, create new node when inserting
    @Override
    public void insert(E data) {
        if (getData() == null) {       // Empty root case
            setData(data);
            return;
        }

        int cmp = data.compareTo(getData());

        if (cmp == 0) return;          // Duplicate – do nothing

        if (cmp < 0) {
            if (getLeft() == null) {
                BST<E> child = new BST<>(data);
                setLeft(child);
                child.setParent(this);
            } else {
                ((BST<E>) getLeft()).insert(data);
            }
        } else {
            if (getRight() == null) {
                BST<E> child = new BST<>(data);
                setRight(child);
                child.setParent(this);
            } else {
                ((BST<E>) getRight()).insert(data);
            }
        }
    }

    //if the node to delete has two children, replace with the largest node in left subtree
    //recursive
    @Override
    public BST<E> deleteWithCopyLeft(E evictee) {
        BST<E> target = lookup(evictee);
        if (target == null) return getRoot();

        // --- case 1: two children → use in-order predecessor ---
        if (target.getLeft() != null && target.getRight() != null) {
            BST<E> pred = (BST<E>) target.getLeft();
            while (pred.getRight() != null)
                pred = (BST<E>) pred.getRight();

            target.setData(pred.getData());
            deleteNodeWithAtMostOneChild(pred);

        } else {
            // --- case 2 & 3: 0 or 1 child ---
            deleteNodeWithAtMostOneChild(target);
        }

        return getRoot();
    }

    @Override
    public BST<E> rotateLeft() {
        BST<E> pivot = (BST<E>) getRight();
        if (pivot == null) return getRoot();

        BST<E> parent = (BST<E>) getParent();
        BST<E> pivotLeft = (BST<E>) pivot.getLeft();

        // Pivot becomes parent of this node
        pivot.setLeft(this);
        this.setParent(pivot);

        // My right child becomes pivot's left
        this.setRight(pivotLeft);
        if (pivotLeft != null) pivotLeft.setParent(this);

        // Attach pivot to the old parent
        pivot.setParent(parent);

        if (parent != null) {
            if (parent.getLeft() == this) parent.setLeft(pivot);
            else parent.setRight(pivot);
        }

        // If rotating at the root, pivot *is* the new root
        return pivot.getRoot();
    }

    @Override
    public BST<E> rotateRight() {
        BST<E> pivot = (BST<E>) getLeft();
        if (pivot == null) return getRoot();

        BST<E> parent = (BST<E>) getParent();
        BST<E> pivotRight = (BST<E>) pivot.getRight();

        // Pivot becomes parent of this node
        pivot.setRight(this);
        this.setParent(pivot);

        // My left child becomes pivot's right
        this.setLeft(pivotRight);
        if (pivotRight != null) pivotRight.setParent(this);

        // Attach pivot to parent
        pivot.setParent(parent);
        if (parent != null) {
            if (parent.getLeft() == this) parent.setLeft(pivot);
            else parent.setRight(pivot);
        }

        return pivot.getRoot();
    }
}


