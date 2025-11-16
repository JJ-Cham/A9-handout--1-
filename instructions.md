# A9: Binary Search Tree

For the first phase of this project you will implement a `BST` class that includes four key operations of a binary search tree:  lookup, insert, delete (using copy left), and left/right rotations.
These are defined in the `BST_Ops` interface.

## Getting Started

Begin by creating class `BST` that extends `BinaryTree`.
Your BST class will be generic on a base type that implements the `Comparable` interface, so that you can perform the necessary comparisons on the elements.
- You will need to create a BST constructor, which can call the parent constructor from the BinaryTree class.
- Implement the override of the `setLeft` and `setRight` methods a little differently in this homework than we did for decision trees.
This will simplify some inheritance issues.
Instead of overloading the methods, we just override the existing method with a conditional to catch inappropriate use.
Here is the template for `setLeft`:

        /** Override inherited manipulator to accept only BST */
        public void setLeft(BinaryTree<E> left) {
          if ((left==null)||(left instanceof BST<E>)) {
            super.setLeft(left);
          } else {
            throw new UnsupportedOperationException("Only BST children allowed");
          }
        }

## BST Methods

Your new class will also need to implement `BST_Ops`.
To get started, copy the method signatures into `BST` as stubs.
This will prevent issues with compiling while you work on developing each method.

As you fill out the required operations, test them thoroughly to make sure they work.
For insertion, remember duplicates are not allowed: inserting an existing key should not change the tree.
Remember that rotations must preserve BST order, update parents/root, and not allocate new nodes.
We have provided a starter `BSTTests` class containing a helper method and a few simple JUnit tests to demonstrate the usage.
You should add the JUnit tests you have written and submit them with the rest of your work.

# Great Recursive Conversions

For the second phase you will create two methods that convert to and from a binary search tree.
One will create a balanced BST structure starting from a sorted array.
The other will relink the nodes of a BST so that they form a doubly linked list with elements following the inorder traversal of the original tree.
(The tree itself is destroyed by this process.)
For full credit, your method should not allocate any new nodes: the existing node objects of the BST are repurposed as the nodes in the linked list.
(This means that instead of `next` and `previous`, this linked list will use the existing `left` and `right` fields.)

For grading convenience, both methods will be implemented as static methods in a class named `Conversion`.  These may call mothods in the other classes if you find that you need to access private fields to complete your implementation.

## Array to BST

The `static` method `arrayToBST` should take the array to be converted as its argument and return a balanced `BST` with the same elements in the same order (assuming inorder traversal).  Your function will want to call a recursive helper.  This helper will accept a sorted array, plus low and high index numbers.  It will only pay attention to the portion of the array between the low index (included in the range) and the high index (excluded).  With these elements it will build a balanced binary tree by splitting the active portion around its middle element (called the pivot) and recursively calling itself on the subranges remaining on either side of the pivot.

## BST to Linked List

This `static` method will be called `binaryTreeToDLL`.  We are providing a basic `DLL` class that uses `BinaryTree` nodes as its list nodes and therefore uses `getLeft` and `getRight` to move through the list, rather than `getPrevious` and `getNext`, respectively.  You shouldn't need to modify the methods in this class.  

The conversion then follows a simple recursive process:  get lists for the left and right subtrees.  Form a new list by sticking them together with the root node in the middle between the two sides, and return the result.  The key thing here is that you should never need to create new nodes, or call methods that do so.  The entire conversion should be carried out just by updating the links between nodes.

You should test both of your methods thoroughly to see that they work.  We have provided the beginnings of a `ConversionTests` class containing some helper methods that may be useful.  You should add the JUnit tests you have written and submit the new version with the rest of your work.
