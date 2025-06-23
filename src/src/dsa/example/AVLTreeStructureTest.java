package src.dsa.example;

import dsa.iface.IBinaryTree;
import dsa.iface.IEntry;
import dsa.iface.IPosition;
import dsa.iface.ISortedMap;
import dsa.impl.AVLTreeMap;
import dsa.impl.BinarySearchTreeMap;
import dsa.impl.TreePrinter;

/**
 * AVL Tree Structure Test Class
 * Used to test various rotation and deletion operations of AVL tree
 */
public class AVLTreeStructureTest {

    public static void main(String[] args) {
        // Test basic rotation operations
        System.out.println("=== Basic Rotation Tests ===");
        testLLRotation();    // Test Left-Left rotation
        testRRRotation();    // Test Right-Right rotation
        testLRRotation();    // Test Left-Right rotation
        testRLRotation();    // Test Right-Left rotation

        // Test deep rotation operations
        System.out.println("\n=== Deep Rotation Tests ===");
        testDeepLLRotation();    // Test deep Left-Left rotation
        testDeepRRRotation();    // Test deep Right-Right rotation
        testDeepLRRotation();    // Test deep Left-Right rotation
        testDeepRLRotation();    // Test deep Right-Left rotation

        // Test basic deletion operations
        System.out.println("\n=== Basic Deletion Tests ===");
        testDeleteLeaf();                // Test deleting a leaf node
        testDeleteNodeWithOneChild();    // Test deleting a node with one child
        testDeleteNodeWithTwoChildren(); // Test deleting a node with two children
        testDeleteRoot();                // Test deleting the root node
        
        // Test restructuring after deletion
        System.out.println("\n=== Restructuring After Deletion Tests ===");
        testDeleteWithLLRestructuring(); // Test LL restructuring after deletion
        testDeleteWithRRRestructuring(); // Test RR restructuring after deletion
        testDeleteWithLRRestructuring(); // Test LR restructuring after deletion
        testDeleteWithRLRestructuring(); // Test RL restructuring after deletion
    }

    // Test Left-Left rotation (root node)
    private static void testLLRotation() {
        System.out.println("\nTest Left-Left Rotation (Root):");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {50, 30, 20};
        int[] bstOrder = {30, 20, 50};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test Right-Right rotation (root node)
    private static void testRRRotation() {
        System.out.println("\nTest Right-Right Rotation (Root):");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {20, 30, 50};
        int[] bstOrder = {30, 20, 50};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test Left-Right rotation (root node)
    private static void testLRRotation() {
        System.out.println("\nTest Left-Right Rotation (Root):");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {50, 20, 30};
        int[] bstOrder = {30, 20, 50};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test Right-Left rotation (root node)
    private static void testRLRotation() {
        System.out.println("\nTest Right-Left Rotation (Root):");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {20, 50, 30};
        int[] bstOrder = {30, 20, 50};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test deep Left-Left rotation
    private static void testDeepLLRotation() {
        System.out.println("\nTest Deep Left-Left Rotation:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {50, 30, 70, 20, 40, 10};
        int[] bstOrder = {30, 20, 10, 50, 40, 70};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test deep Right-Right rotation
    private static void testDeepRRRotation() {
        System.out.println("\nTest Deep Right-Right Rotation:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {30, 20, 50, 40, 70, 80};
        int[] bstOrder = {50, 30, 20, 40, 70, 80};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test deep Left-Right rotation
    private static void testDeepLRRotation() {
        System.out.println("\nTest Deep Left-Right Rotation:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {50, 30, 70, 20, 35, 40};
        int[] bstOrder = {35, 30, 20, 50, 40, 70};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test deep Right-Left rotation
    private static void testDeepRLRotation() {
        System.out.println("\nTest Deep Right-Left Rotation:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] avlOrder = {30, 20, 50, 40, 70, 45};
        int[] bstOrder = {40, 30, 20, 50, 45, 70};
        
        insertAndCompare(avl, bst, avlOrder, bstOrder);
    }

    // Test deleting a leaf node
    private static void testDeleteLeaf() {
        System.out.println("\nTest Delete Leaf Node:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60, 80};
        for (int v : initialOrder) {
            avl.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(avl, bst);
        
        avl.remove(20);
        bst.remove(20);
        
        System.out.println("\nTree after deleting leaf node(20):");
        printAndCompare(avl, bst);
    }

    // Test deleting a node with one child
    private static void testDeleteNodeWithOneChild() {
        System.out.println("\nTest Delete Node with One Child:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60};
        for (int v : initialOrder) {
            avl.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(avl, bst);
        
        avl.remove(70);
        bst.remove(70);
        
        System.out.println("\nTree after deleting node with one child(70):");
        printAndCompare(avl, bst);
    }

    // Test deleting an internal node with two children
    private static void testDeleteNodeWithTwoChildren() {
        System.out.println("\nTest Delete Internal Node with Two Children:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60, 80, 15, 25, 35, 45};
        for (int v : initialOrder) {
            avl.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(avl, bst);
        
        avl.remove(30);
        bst.remove(30);
        
        System.out.println("\nTree after deleting internal node(30):");
        printAndCompare(avl, bst);
    }

    // Test deleting the root node
    private static void testDeleteRoot() {
        System.out.println("\nTest Delete Root Node:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        ISortedMap<Integer,Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60, 80};
        for (int v : initialOrder) {
            avl.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(avl, bst);
        
        avl.remove(50);
        bst.remove(50);
        
        System.out.println("\nTree after deleting root node(50):");
        printAndCompare(avl, bst);
    }

    // Test LL restructuring after deletion
    private static void testDeleteWithLLRestructuring() {
        System.out.println("\nTest LL Restructuring After Deletion:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60, 10, 5};
        for (int v : initialOrder) {
            avl.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        avl.remove(70);
        System.out.println("\nTree after deleting node(70):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        ISortedMap<Integer,Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {50, 30, 10, 5, 20, 40, 60};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) avl, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) avl).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }

    // Test RR restructuring after deletion
    private static void testDeleteWithRRRestructuring() {
        System.out.println("\nTest RR Restructuring After Deletion:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        
        int[] initialOrder = {30, 20, 50, 10, 40, 70, 60, 80, 90};
        for (int v : initialOrder) {
            avl.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        avl.remove(10);
        System.out.println("\nTree after deleting node(10):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        ISortedMap<Integer,Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {70, 30, 20, 50, 40, 60, 80, 90};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) avl, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) avl).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }

    // Test LR restructuring after deletion
    private static void testDeleteWithLRRestructuring() {
        System.out.println("\nTest LR Restructuring After Deletion:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        
        int[] initialOrder = {50, 20, 70, 10, 40, 30, 45, 80};
        for (int v : initialOrder) {
            avl.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        avl.remove(70);
        System.out.println("\nTree after deleting node(70):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        ISortedMap<Integer,Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {40, 20, 10, 30, 50, 45, 80};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) avl, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) avl).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }

    // Test RL restructuring after deletion
    private static void testDeleteWithRLRestructuring() {
        System.out.println("\nTest RL Restructuring After Deletion:");
        ISortedMap<Integer,Integer> avl = new AVLTreeMap<>();
        
        int[] initialOrder = {30, 10, 60, 5, 50, 80, 40, 55};
        for (int v : initialOrder) {
            avl.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        avl.remove(5);
        System.out.println("\nTree after deleting node(5):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) avl);
        
        ISortedMap<Integer,Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {50, 30, 10, 40, 60, 55, 80};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) avl, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) avl).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }

    // Insert nodes and compare AVL tree and BST structures
    private static void insertAndCompare(ISortedMap<Integer,Integer> avl, ISortedMap<Integer,Integer> bst, 
                                       int[] avlOrder, int[] bstOrder) {
        for (int v : avlOrder)
            avl.put(v, v);
        for (int v : bstOrder)
            bst.put(v, v);
        
        printAndCompare(avl, bst);
    }

    // Print and compare AVL tree and BST structures
    private static void printAndCompare(ISortedMap<Integer,Integer> avl, ISortedMap<Integer,Integer> bst) {
        IBinaryTree<IEntry<Integer,Integer>> t1 = (IBinaryTree<IEntry<Integer,Integer>>) avl;
        IBinaryTree<IEntry<Integer,Integer>> t2 = (IBinaryTree<IEntry<Integer,Integer>>) bst;
        
        System.out.println("AVL Tree Structure:");
        TreePrinter.printTree(t1);
        System.out.println("\nBST Structure (Expected Final Structure):");
        TreePrinter.printTree(t2);
        
        System.out.println("\nAre structures identical? " + 
            (areEqual(t1, t1.root(), t2, t2.root()) ? "Yes! :-D" : "No! :-("));
    }

    // Print AVL tree and BST structures
    private static void printTrees(ISortedMap<Integer,Integer> avl, ISortedMap<Integer,Integer> bst) {
        IBinaryTree<IEntry<Integer,Integer>> t1 = (IBinaryTree<IEntry<Integer,Integer>>) avl;
        IBinaryTree<IEntry<Integer,Integer>> t2 = (IBinaryTree<IEntry<Integer,Integer>>) bst;
        
        System.out.println("AVL Tree Structure:");
        TreePrinter.printTree(t1);
        System.out.println("\nBST Structure:");
        TreePrinter.printTree(t2);
    }

    // Recursively compare if two tree structures are identical
    private static <K extends Comparable<K>,V> boolean areEqual(IBinaryTree<IEntry<K,V>> t1, 
            IPosition<IEntry<K,V>> p1, IBinaryTree<IEntry<K,V>> t2, IPosition<IEntry<K,V>> p2) {
        if (t1.isExternal(p1) && t2.isExternal(p2))
            return true;
        else if (t1.isInternal(p1) && t2.isInternal(p2)) {
            return p1.element().key().equals(p2.element().key()) && 
                   areEqual(t1, t1.left(p1), t2, t2.left(p2)) && 
                   areEqual(t1, t1.right(p1), t2, t2.right(p2));
        }
        else {
            return false;
        }
    }
}
