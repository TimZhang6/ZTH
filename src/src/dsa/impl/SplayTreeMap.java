package dsa.impl;

import dsa.iface.IEntry;
import dsa.iface.IPosition;
import dsa.iface.ISortedMap;

/**
 * Splay Tree implementation of a map with self-adjusting capability.
 * This class extends BinarySearchTreeMap to provide a tree structure
 * that brings frequently accessed elements closer to the root.
 *
 * @param <K> The key type, must be comparable
 * @param <V> The value type
 */
public class SplayTreeMap<K extends Comparable<K>,V> extends BinarySearchTreeMap<K,V> implements ISortedMap<K,V> {

    /**
     * Default constructor for creating an empty Splay tree
     */
    public SplayTreeMap() {
        super();
    }

    /**
     * Retrieves the value associated with the given key and splays the tree.
     * If the key is not found, the last accessed node is splayed to the root.
     *
     * @param k The key to search for
     * @return The value associated with the key, or null if the key is not found
     */
    @Override
    public V get(K k) {
        // Check if tree is empty
        if (isEmpty()) return null;
        
        // Find the position containing the key
        IPosition<IEntry<K,V>> position = find(root(), k);
        
        if (isExternal(position)) {
            // Key not found, splay the parent of the external node
            if (!isRoot(position)) {
                splay(parent(position));
            }
            return null;
        }
        
        // Key found, splay the node to root and return its value
        splay(position);
        return position.element().value();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * The accessed node is splayed to the root after the operation.
     *
     * @param k The key with which the specified value is to be associated
     * @param v The value to be associated with the specified key
     * @return The previous value associated with the key, or null if there was no mapping
     */
    @Override
    public V put(K k, V v) {
        // Handle empty tree case
        if (isEmpty()) {
            BinarySearchTreeMap.Entry entry = new BinarySearchTreeMap.Entry(k, v);
            expandExternal(root(), entry);
            return null;
        }
        
        // Find the position where the key should be
        IPosition<IEntry<K,V>> position = find(root(), k);
        
        if (isExternal(position)) {
            // Key doesn't exist, create new entry
            BinarySearchTreeMap.Entry entry = new BinarySearchTreeMap.Entry(k, v);
            expandExternal(position, entry);
            splay(position);
            return null;
        } else {
            // Key exists, update value
            V lastValue = position.element().value();
            BinarySearchTreeMap.Entry entry = new BinarySearchTreeMap.Entry(k, v);
            replace(position, entry);
            splay(position);
            return lastValue;
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * The parent of the removed node is splayed to the root.
     *
     * @param k The key whose mapping is to be removed from the map
     * @return The previous value associated with the key, or null if there was no mapping
     */
    @Override
    public V remove(K k) {
        // Check if tree is empty
        if (isEmpty()) return null;
        
        // Find the position of the key
        IPosition<IEntry<K,V>> position = find(root(), k);
        
        if (isExternal(position)) {
            // Key not found, splay the parent of the external node
            if (!isRoot(position)) {
                splay(parent(position));
            }
            return null;
        }
        
        // Store the value to return
        V lastValue = position.element().value();
        
        // Splay the node to be removed to the root
        splay(position);
        
        // Case 1: Node has no children
        if (isExternal(left(position)) && isExternal(right(position))) {
            this.root = null;
            size = 0;
            return lastValue;
        }
        
        // Case 2: Node has only right child
        if (isExternal(left(position))) {
            IPosition<IEntry<K,V>> rightChild = right(position);
            AbstractBinaryTree<IEntry<K,V>>.BTPosition rightPosition = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) rightChild;
            root = rightPosition;
            rightPosition.parent = null;
            size--;
            return lastValue;
        }
        
        // Case 3: Node has only left child
        if (isExternal(right(position))) {
            IPosition<IEntry<K,V>> leftChild = left(position);
            AbstractBinaryTree<IEntry<K,V>>.BTPosition leftPosition = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) leftChild;
            root = leftPosition;
            leftPosition.parent = null;
            size--;
            return lastValue;
        }
        
        // Case 4: Node has both children
        // Find the maximum node in the left subtree
        IPosition<IEntry<K,V>> predecessor = treeMaximum(left(position));
        
        // Splay the predecessor to the root of the left subtree
        splay(predecessor);
        
        // Get references to the predecessor and right child of the removed node
        AbstractBinaryTree<IEntry<K,V>>.BTPosition predPosition = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) predecessor;
        AbstractBinaryTree<IEntry<K,V>>.BTPosition rightPosition = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) right(position);
        
        // Remove the external node if it exists
        if (isExternal(right(predecessor))) {
            remove(right(predecessor));
        }
        
        // Connect the predecessor to the right subtree
        predPosition.right = rightPosition;
        rightPosition.parent = predPosition;
        
        // Make the predecessor the new root
        root = predPosition;
        predPosition.parent = null;
        
        size--;
        return lastValue;
    }
    
    /**
     * Splays a node to the root of the tree using a series of rotations.
     * This method implements the splaying operation that brings frequently accessed nodes to the root.
     *
     * @param position The position to splay to the root
     */
    protected void splay(IPosition<IEntry<K,V>> position) {
        // Check if position is valid
        if (position == null || isExternal(position)) {
            return;
        }
        
        // Continue splaying until the position becomes the root
        while (!isRoot(position)) {
            IPosition<IEntry<K,V>> parent = parent(position);
            
            if (isRoot(parent)) {
                // Case 1: Zig - single rotation
                if (position == left(parent)) {
                    rightRotation(parent);
                } else {
                    leftRotation(parent);
                }
            } else {
                // Get the grandparent for double rotation cases
                IPosition<IEntry<K,V>> grandparent = parent(parent);
                
                if (position == left(parent) && parent == left(grandparent)) {
                    // Case 2: Zig-Zig - two right rotations
                    rightRotation(grandparent);
                    rightRotation(parent(position));
                } else if (position == right(parent) && parent == right(grandparent)) {
                    // Case 3: Zig-Zig - two left rotations
                    leftRotation(grandparent);
                    leftRotation(parent(position));
                } else if (position == right(parent) && parent == left(grandparent)) {
                    // Case 4: Zig-Zag - left then right rotation
                    leftRotation(parent);
                    rightRotation(parent(position));
                } else if (position == left(parent) && parent == right(grandparent)) {
                    // Case 5: Zig-Zag - right then left rotation
                    rightRotation(parent);
                    leftRotation(parent(position));
                }
            }
        }
    }
    
    /**
     * Finds the node with the maximum key in the subtree rooted at the given position
     *
     * @param position The root of the subtree to search
     * @return The position containing the maximum key
     */
    protected IPosition<IEntry<K,V>> treeMaximum(IPosition<IEntry<K,V>> position) {
        IPosition<IEntry<K,V>> current = position;
        // Keep going right until reaching a leaf
        while (isInternal(right(current))) {
            current = right(current);
        }
        return current;
    }
    
    /**
     * Performs a left rotation on the specified position
     *
     * @param x The position to rotate
     */
    protected void leftRotation(IPosition<IEntry<K,V>> x) {
        // Check if rotation is possible
        if (x == null || isExternal(x)) return;
        
        // Get the right child (y) and its left subtree (b)
        IPosition<IEntry<K,V>> y = right(x);
        if (y == null || isExternal(y)) return;
        
        IPosition<IEntry<K,V>> b = left(y);
        
        // Update parent references
        if (isRoot(x)) {
            // x is root, make y the new root
            root = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = null;
        } else {
            // Update x's parent to point to y
            AbstractBinaryTree<IEntry<K,V>>.BTPosition parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) parent(x);
            if (x == left(parent)) {
                parent.left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
            } else {
                parent.right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
            }
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = parent;
        }
        
        // Update child references
        // Make x the left child of y
        ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) x;
        ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) x).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
        
        // Make b the right child of x
        ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) x).right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) b;
        if (b != null) {
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) b).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) x;
        }
    }
    
    /**
     * Performs a right rotation on the specified position
     *
     * @param z The position to rotate
     */
    protected void rightRotation(IPosition<IEntry<K,V>> z) {
        // Check if rotation is possible
        if (z == null || isExternal(z)) return;
        
        // Get the left child (y) and its right subtree (b1)
        IPosition<IEntry<K,V>> y = left(z);
        if (y == null || isExternal(y)) return;
        
        IPosition<IEntry<K,V>> b1 = right(y);
        
        // Update parent references
        if (isRoot(z)) {
            // z is root, make y the new root
            root = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = null;
        } else {
            // Update z's parent to point to y
            AbstractBinaryTree<IEntry<K,V>>.BTPosition parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) parent(z);
            if (z == left(parent)) {
                parent.left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
            } else {
                parent.right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
            }
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).parent = parent;
        }
        
        // Update child references
        // Make z the right child of y
        ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) y).right = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) z;
        ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) z).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) y;
        
        // Make b1 the left child of z
        ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) z).left = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) b1;
        if (b1 != null) {
            ((AbstractBinaryTree<IEntry<K,V>>.BTPosition) b1).parent = (AbstractBinaryTree<IEntry<K,V>>.BTPosition) z;
        }
    }
}
