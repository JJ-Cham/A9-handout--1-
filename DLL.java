/**
 * Class to implement a doubly linked list using BinaryTree nodes
 *
 * @author Nick Howe
 * @version Fall 2025
 */
public class DLL<T> {
  /** The head node of the list */
  private BinaryTree<T> head;

  /** The tail node of the list */
  private BinaryTree<T> tail;

  /** Constructor for an empty list */
  public DLL() {
    head = tail = null;
  }

  /** Constructor for an existing list */
  public DLL(BinaryTree<T> h, BinaryTree<T> t) {
    head = h;
    tail = t;
  }

  /**
   * Accessor for head node
   * 
   * @return the head node
   */
  public BinaryTree<T> getHead() {
    return head;
  }

  /**
   * Accessor for tail node
   * 
   * @return the tail node
   */
  public BinaryTree<T> getTail() {
    return tail;
  }

  /**
   * Determines whether a list is empty
   * 
   * @return T/F is the list empty?
   */
  public boolean isEmpty() {
    return (head == null);
  }

  /** Converts to a string representation */
  public String toString() {
    String result = "[";
    if (isEmpty()) {
      result += "]";
    } else {
      BinaryTree<T> item;
      for (item = this.head; item != tail; item = item.getRight()) {
        result += item.getData() + ", ";
      }
      result += item.getData() + "]";
    }
    return result;
  }
}