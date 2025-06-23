package dsa.impl;

import dsa.iface.IEntry;
import dsa.iface.IPosition;
import dsa.iface.ISortedMap;

/**
 * AVL Tree implementation of a map with self-balancing capability.
 * This class extends BinarySearchTreeMap to provide a balanced tree structure.
 *
 * @param <K> The key type, must be comparable
 * @param <V> The value type
 */
public class AVLTreeMap<K extends Comparable<K>,V> extends BinarySearchTreeMap<K,V> implements ISortedMap<K,V> {

   /**
    * Default constructor for creating an empty AVL tree
    */
   public AVLTreeMap() {
      super();
   }

   /**
    * Retrieves the value associated with the given key
    *
    * @param k The key to search for
    * @return The value associated with the key, or null if the key is not found
    */
   @Override
   public V get(K k) {
      // Find the position containing the key
      IPosition<IEntry<K,V>> position = find(root(), k);
      // If the position is external, the key doesn't exist in the tree
      if (isExternal(position)) {
         return null;
      }
      // Otherwise, extract and return the value
      IEntry<K,V> entry = position.element();
      return entry.value();
   }

   /**
    * Associates the specified value with the specified key in this map.
    * If the map previously contained a mapping for the key, the old value is replaced.
    *
    * @param k The key with which the specified value is to be associated
    * @param v The value to be associated with the specified key
    * @return The previous value associated with the key, or null if there was no mapping
    */
   @Override
   public V put(K k, V v) {
      // Find the position where the key should be
      IPosition<IEntry<K,V>> position = find(root(), k);
      if (isExternal(position)) {
         // Key doesn't exist yet, create a new entry
         BinarySearchTreeMap.Entry entry = new BinarySearchTreeMap.Entry(k, v);
         // Convert external node to internal with the new entry
         expandExternal(position, entry);
         // Rebalance the tree starting from the new position
         balance(position);
         return null;
      } else {
         // Key already exists, update the value
         V lastValue = position.element().value();
         BinarySearchTreeMap.Entry entry = new BinarySearchTreeMap.Entry(k, v);
         // Replace the existing entry with the new one
         replace(position, entry);
         return lastValue;
      }
   }

   /**
    * Removes the mapping for a key from this map if it is present.
    *
    * @param k The key whose mapping is to be removed from the map
    * @return The previous value associated with the key, or null if there was no mapping
    */
   @Override
   public V remove(K k) {
      // Check if the tree is empty
      if (isEmpty()) {
         return null;
      }
      
      // Find the position of the key
      IPosition<IEntry<K,V>> position = find(root(), k);
      // If key not found, return null
      if (position == null || isExternal(position)) {
         return null;
      }
      
      // Store the value to return later
      V lastValue = position.element().value();
      
      // Get the children of the position
      IPosition<IEntry<K,V>> leftChild = left(position);
      IPosition<IEntry<K,V>> rightChild = right(position);
      
      // Check if the position has internal children
      boolean hasLeftChild = (leftChild != null && isInternal(leftChild));
      boolean hasRightChild = (rightChild != null && isInternal(rightChild));
      
      // Track the position to rebalance from after removal
      IPosition<IEntry<K,V>> parentBalance = null;
      
      if (hasLeftChild && hasRightChild) {
         // Case 3: Node has two children
         // Find the inorder successor (minimum key in right subtree)
         IPosition<IEntry<K,V>> successor = findMinimum(rightChild);
         if (successor != null) {
            // Determine the position to rebalance from
            parentBalance = parent(successor);
            if (parentBalance == position) {
               parentBalance = successor; // Special case for direct right child
            }
            
            // Replace the position's entry with the successor's entry
            replace(position, successor.element());
            
            // Remove the successor node
            if (successor == rightChild) {
               // Special case: right child is the successor
               if (isInternal(right(successor))) {
                  // The successor has a right child
                  replace(successor, right(successor).element());
                  remove(right(successor));
               } else {
                  // The successor has no children
                  remove(successor);
               }
            } else {
               // General case: successor is deeper in the tree
               if (isInternal(right(successor))) {
                  // The successor has a right child
                  replace(successor, right(successor).element());
                  remove(right(successor));
               } else {
                  // The successor has no children
                  remove(successor);
               }
            }
         }
      } else {
         // Case 1 & 2: Node has zero or one child
         IPosition<IEntry<K,V>> child = null;
         if (hasLeftChild) {
            child = leftChild;
         } else if (hasRightChild) {
            child = rightChild;
         }
         
         // Get parent for rebalancing
         parentBalance = parent(position);
         
         if (child != null) {
            // Case 2: Node has one child
            replace(position, child.element());
            remove(child);
         } else {
            // Case 1: Node is a leaf (has no children)
            remove(position);
         }
      }
      
      // Rebalance the tree after removal
      if (parentBalance != null) {
         balance(parentBalance);
      }
      
      return lastValue;
   }
   
   /**
    * Finds the node with the minimum key in the subtree rooted at the given position
    *
    * @param root The root of the subtree to search
    * @return The position containing the minimum key
    */
   private IPosition<IEntry<K,V>> findMinimum(IPosition<IEntry<K,V>> root) {
      if (root == null) return null;
      
      // Start from the root of the subtree
      IPosition<IEntry<K,V>> current = root;
      IPosition<IEntry<K,V>> left = null;
      
      try {
         // Get the left child of the current position
         left = left(current);
      } catch (Exception e) {
         return current; // No left child, so current is the minimum
      }
      
      // Keep going left until reaching a leaf
      while (left != null && isInternal(left)) {
         current = left;
         try {
            left = left(current);
         } catch (Exception e) {
            break; // No more left children
         }
      }
      
      // Return the position with the minimum key
      return current;
   }
   
   /**
    * Balances the AVL tree starting from the given position up to the root.
    * This method is called after an insertion or removal operation.
    *
    * @param position The position from which to start balancing
    */
   protected void balance(IPosition<IEntry<K,V>> position) {
      if (position == null) return;
      
      // Start from the provided position
      IPosition<IEntry<K,V>> current = position;
      boolean restructure = false;
      
      // Process all nodes up to the root
      while (current != null) {
         // Update the height of the current position
         updateHeight(current);
         
         // Calculate balance factor to check if rotation is needed
         int balanceFactor = getBalanceFactor(current);
         
         if (balanceFactor > 1) {
            // Left subtree is higher - right rotation needed
            IPosition<IEntry<K,V>> leftChild = left(current);
            int leftChildBalanceFactor = getBalanceFactor(leftChild);
            
            if (leftChildBalanceFactor >= 0) {
                // Left-Left case: single right rotation
                current = singleRightRotation(current);
            } else {
                // Left-Right case: double rotation (left, then right)
                singleLeftRotation(leftChild);
                current = singleRightRotation(current);
            }
            restructure = true;
         } else if (balanceFactor < -1) {
            // Right subtree is higher - left rotation needed
            IPosition<IEntry<K,V>> rightChild = right(current);
            int rightChildBalanceFactor = getBalanceFactor(rightChild);
            
            if (rightChildBalanceFactor <= 0) {
                // Right-Right case: single left rotation
                current = singleLeftRotation(current);
            } else {
                // Right-Left case: double rotation (right, then left)
                singleRightRotation(rightChild);
                current = singleLeftRotation(current);
            }
            restructure = true;
         }
         
         // If tree structure changed, update heights of affected nodes
         if (restructure) {
            updateHeightRecursive(current);
            restructure = false;
         }
         
         // Move up to the parent for next iteration
         try {
            current = parent(current);
         } catch (Exception e) {
            current = null; // Reached the root or an error occurred
         }
      }
   }
   
   /**
    * Updates the height of a single position
    *
    * @param position The position whose height needs to be updated
    */
   private void updateHeight(IPosition<IEntry<K,V>> position) {
      if (position instanceof AVLPosition) {
         // Cast to AVLPosition to access height field
         AVLPosition node = (AVLPosition) position;
         // Get heights of left and right subtrees
         int leftHeight = getHeight(left(position));
         int rightHeight = getHeight(right(position));
         // Set height to 1 + max height of children
         node.height = 1 + Math.max(leftHeight, rightHeight);
      }
   }
   
   /**
    * Recursively updates the heights of a position and all its descendants
    *
    * @param position The position to start updating from
    */
   private void updateHeightRecursive(IPosition<IEntry<K,V>> position) {
      if (position == null) return;
      
      if (position instanceof AVLPosition) {
         // Recursively update heights of children first
         if (isInternal(left(position))) {
            updateHeightRecursive(left(position));
         }
         if (isInternal(right(position))) {
            updateHeightRecursive(right(position));
         }
         
         // Then update height of this position
         updateHeight(position);
      }
   }
   
   /**
    * Calculates the balance factor of a position (height of left subtree - height of right subtree)
    *
    * @param position The position to calculate the balance factor for
    * @return The balance factor value
    */
   private int getBalanceFactor(IPosition<IEntry<K,V>> position) {
      if (position == null) return 0;
      // Balance factor = height of left subtree - height of right subtree
      return getHeight(left(position)) - getHeight(right(position));
   }
   
   /**
    * Gets the height of a position in the tree
    *
    * @param position The position to get the height for
    * @return The height of the position, or -1 if position is null
    */
   private int getHeight(IPosition<IEntry<K,V>> position) {
      if (position == null) return -1; // Null has height -1
      if (isExternal(position)) return 0; // External nodes have height 0
      // For internal nodes, get height from AVLPosition object
      return position instanceof AVLPosition ? ((AVLPosition) position).height : 0;
   }
   
   /**
    * Performs a single right rotation on the specified position
    *
    * @param z The position to rotate
    * @return The new position after rotation
    */
   private IPosition<IEntry<K,V>> singleRightRotation(IPosition<IEntry<K,V>> z) {
      if (z == null) return null;
      
      // Get the left child, which will become the new root of this subtree
      IPosition<IEntry<K,V>> y = left(z);
      if (y == null) return z;
      
      // Store the right child of y, which will become the left child of z
      IPosition<IEntry<K,V>> t = right(y);
      
      // Get the parent of z to update relationships after rotation
      IPosition<IEntry<K,V>> zParent = null;
      boolean zIsRoot = isRoot(z);
      boolean zIsLeftChild = false;
      
      if (!zIsRoot) {
         zParent = parent(z);
         zIsLeftChild = z == left(zParent);
      }
      
      // Update parent references
      if (zIsRoot) {
         // If z was the root, y becomes the new root
         root = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
         ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = null;
      } else {
         // Otherwise, y takes z's place as child of z's parent
         if (zIsLeftChild) {
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) zParent).left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
         } else {
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) zParent).right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
         }
         ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) zParent;
      }
      
      // Update child references
      // z becomes the right child of y
      ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) z;
      ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) z).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
      
      // t becomes the left child of z
      ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) z).left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) t;
      if (t != null) {
         ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) t).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) z;
      }
      
      // Update heights
      // Heights must be updated bottom-up (z first, then y)
      updateHeight(z);
      updateHeight(y);
      
      return y;
   }
   
   /**
    * Performs a single left rotation on the specified position
    *
    * @param x The position to rotate
    * @return The new position after rotation
    */
   private IPosition<IEntry<K,V>> singleLeftRotation(IPosition<IEntry<K,V>> x) {
      if (x == null) return null;
      
      // Get the right child, which will become the new root of this subtree
      IPosition<IEntry<K,V>> y = right(x);
      if (y == null) return x;
      
      // Store the left child of y, which will become the right child of x
      IPosition<IEntry<K,V>> t1 = left(y);
      
      // Get the parent of x to update relationships after rotation
      IPosition<IEntry<K,V>> xParent = null;
      boolean xIsRoot = isRoot(x);
      boolean xIsLeftChild = false;
      
      if (!xIsRoot) {
         xParent = parent(x);
         xIsLeftChild = x == left(xParent);
      }
      
      // Update parent references
      if (xIsRoot) {
         // If x was the root, y becomes the new root
         root = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
         ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = null;
      } else {
         // Otherwise, y takes x's place as child of x's parent
         if (xIsLeftChild) {
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) xParent).left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
         } else {
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) xParent).right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
         }
         ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) xParent;
      }
      
      // Update child references
      // x becomes the left child of y
      ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) x;
      ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) x).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
      
      // t1 becomes the right child of x
      ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) x).right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) t1;
      if (t1 != null) {
         ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) t1).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) x;
      }
      
      // Update heights
      // Heights must be updated bottom-up (x first, then y)
      updateHeight(x);
      updateHeight(y);
      
      return y;
   }

   // YOU SHOULD NEED TO ENTER ANYTHING BELOW THIS

   /**
    * Define a subclass of BTPosition so that we can also store the height
    *    of each position in its object.
    *
    * This will be more efficient than calculating the height every time
    *    we need it, but we will need to update heights whenever we change
    *    the structure of the tree.
    */
   class AVLPosition extends AbstractBinaryTree<IEntry<K,V>>.BTPosition {
      // store the height of this position, so that we can test for balance
      public int height = 0;

      /**
       * Constructor - create a new AVL node
       * @param element The element to store in the node.
       * @param parent The parent node of this node (or {@code null} if this is the root)
       */
      AVLPosition( IEntry<K,V> element, AbstractBinaryTree.BTPosition parent ) {
         super( element, parent );
      }
   }

   @Override
   protected AbstractBinaryTree<IEntry<K,V>>.BTPosition newPosition(IEntry<K,V> element, AbstractBinaryTree<IEntry<K,V>>.BTPosition parent) {
      return new AVLPosition(element, parent);
   }
}
