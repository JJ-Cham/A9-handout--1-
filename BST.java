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

    //methods from BST_Ops interface stubs
    //goal: return the BST node containing data, or null if not found
    //? condtional operator
    @Override
    public void BST<E> lookup (E data){
        if (data == null)
            return null;
        int cmp = data.compareTo(this.root.getData());
        if (cmp == 0) 
            return this;
        else if (cmp < 0){
            //search left subtree
            return (this.getLeft() == null) ? null : this.getLeft().lookup(data);
        } else {
            //search right subtree
            return (this.getRight() == null) ? null : this.getRight().lookup(data);
        }

    
    }

    //no duplicates allowed, create new node when inserting
    @Override
    public void insert (E data){
        int cmp = data.compareTo(this.getData());

        if (cmp == 0){
            return; //duplicate, do nothing
        }
        else if (cmp < 0){
            //insert into left subtree
            if (this.getLeft() == null){
                this.setLeft(new BST<E>());
            }
            this.getLeft().insert(data);
        } else {
            //insert into right subtree
            if (this.getRight() == null){
                this.setRight(new BST<E>());
            }
            this.getRight().insert(data);
        }


    }

    //if the node to delete has two children, replace with the largest node in left subtree
    //recursive
    @Override
    public BST<E> deleteWithCopyLeft (E evictee){
        int cmp = evictee.compareTo(this.getData());
        if (cmp < 0){
            //delete from left subtree
            if (this.getLeft() != null){
                this.setLeft(this.getLeft().deleteWithCopyLeft(evictee));
            }
        } else if (cmp > 0){
            //delete from right subtree
            if (this.getRight() != null){
                this.setRight(this.getRight().deleteWithCopyLeft(evictee));
            }
        } else {
            //found the node to delete
            if (this.getLeft() == null){
                return this.getRight();
            } else if (this.getRight() == null){
                return this.getLeft();
            } else {
                //two children: find largest in left subtree
                BST<E> largest = this.getLeft();
                while (largest.getRight() != null){
                    largest = largest.getRight();
                }
                //copy largest's data to this node
                this.setData(largest.getData());
                //delete largest node from left subtree
                this.setLeft(this.getLeft().deleteWithCopyLeft(largest.getData()));
            }
        }
        
    }

    public BST<E> rotateLeft (){
        return null;
    }

    public BST<E> rotateRight (){
        return null;
    }

}
