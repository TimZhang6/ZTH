package src.dsa.example;

import dsa.iface.IBinaryTree;
import dsa.iface.IEntry;
import dsa.iface.IPosition;
import dsa.iface.ISortedMap;
import dsa.impl.BinarySearchTreeMap;
import dsa.impl.SplayTreeMap;
import dsa.impl.TreePrinter;

/**
 * Splay Tree Structure Test Class
 * Used to test various splaying and deletion operations of Splay tree
 */
public class SplayTreeStructureTest {

    public static void main(String[] args) {
        // Test basic splaying operations
        System.out.println("=== Basic Splaying Operation Tests ===");
        testZigRotation();        // Test Zig rotation
        testZigZigRotation();     // Test Zig-Zig rotation
        testZigZagRotation();     // Test Zig-Zag rotation
        
        // Test deep splaying operations
        System.out.println("\n=== Deep Splaying Tests ===");
        testDeepZigRotation();    // Test deep Zig rotation
        testDeepZigZigRotation(); // Test deep Zig-Zig rotation
        testDeepZigZagRotation(); // Test deep Zig-Zag rotation
        
        // Test basic deletion operations
        System.out.println("\n=== Basic Deletion Tests ===");
        testDeleteLeaf();                // Test deleting a leaf node
        testDeleteNodeWithOneChild();    // Test deleting a node with one child
        testDeleteNodeWithTwoChildren(); // Test deleting a node with two children
        testDeleteRoot();                // Test deleting the root node
        
        // Test restructuring after deletion
        System.out.println("\n=== Restructuring After Deletion Tests ===");
        testDeleteWithZigRestructuring();     // Test Zig restructuring after deletion
        testDeleteWithZigZigRestructuring();  // Test Zig-Zig restructuring after deletion
        testDeleteWithZigZagRestructuring();  // Test Zig-Zag restructuring after deletion
    }
    
    // Test Zig rotation (single rotation)
    private static void testZigRotation() {
        System.out.println("\nTest Zig Rotation (Single Rotation):");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] splayOrder = {30, 20};
        int[] bstOrder = {20, 30};
        
        insertAndCompare(splay, bst, splayOrder, bstOrder);
    }
    
    // Test Zig-Zig rotation (double rotation, same direction)
    private static void testZigZigRotation() {
        System.out.println("\nTest Zig-Zig Rotation (Double Rotation, Same Direction):");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] splayOrder = {30, 20, 10};
        int[] bstOrder = {10, 20, 30};
        
        insertAndCompare(splay, bst, splayOrder, bstOrder);
    }
    
    // Test Zig-Zag rotation (double rotation, different directions)
    private static void testZigZagRotation() {
        System.out.println("\nTest Zig-Zag Rotation (Double Rotation, Different Directions):");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] splayOrder = {30, 10, 20};
        int[] bstOrder = {20, 10, 30};
        
        insertAndCompare(splay, bst, splayOrder, bstOrder);
    }
    
    // Test deep Zig rotation
    private static void testDeepZigRotation() {
        System.out.println("\nTest Deep Zig Rotation:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] splayOrder = {50, 30, 70, 20, 40, 60, 80, 25};
        int[] bstOrder = {25, 20, 70, 40, 30, 60, 50, 80};
        
        insertAndCompare(splay, bst, splayOrder, bstOrder);
    }
    
    // Test deep Zig-Zig rotation
    private static void testDeepZigZigRotation() {
        System.out.println("\nTest Deep Zig-Zig Rotation:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] splayOrder = {50, 30, 70, 20, 40, 60, 80, 10};
        int[] bstOrder = {10, 80, 60, 20, 40, 30, 50, 70};
        
        insertAndCompare(splay, bst, splayOrder, bstOrder);
    }
    
    // Test deep Zig-Zag rotation
    private static void testDeepZigZagRotation() {
        System.out.println("\nTest Deep Zig-Zag Rotation:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] splayOrder = {50, 30, 70, 20, 40, 60, 80, 35};
        int[] bstOrder = {35, 30, 20, 70, 40, 60, 50, 80};
        
        insertAndCompare(splay, bst, splayOrder, bstOrder);
    }
    
    // Test deleting a leaf node
    private static void testDeleteLeaf() {
        System.out.println("\nTest Delete Leaf Node:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialValues = {50, 30, 70, 20, 40, 60, 80};
        for (int v : initialValues) {
            splay.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(splay, bst);
        
        splay.remove(20);
        bst.remove(20);
        
        bst = new BinarySearchTreeMap<>();
        int[] newBstOrder = {70, 40, 30, 60, 50, 80};
        for (int v : newBstOrder) {
            bst.put(v, v);
        }
        
        System.out.println("\nTree after deleting leaf node(20):");
        printAndCompare(splay, bst);
    }
    
    // Test deleting a node with one child
    private static void testDeleteNodeWithOneChild() {
        System.out.println("\nTest Delete Node with One Child:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialValues = {50, 30, 70, 20, 60};
        for (int v : initialValues) {
            splay.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(splay, bst);
        
        splay.remove(30);
        bst.remove(30);
        
        bst = new BinarySearchTreeMap<>();
        int[] newBstOrder = {20, 60, 50, 70};
        for (int v : newBstOrder) {
            bst.put(v, v);
        }
        
        System.out.println("\nTree after deleting node with one child(30):");
        printAndCompare(splay, bst);
    }
    
    // Test deleting an internal node with two children
    private static void testDeleteNodeWithTwoChildren() {
        System.out.println("\nTest Delete Internal Node with Two Children:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialValues = {50, 30, 70, 20, 40, 60, 80};
        for (int v : initialValues) {
            splay.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(splay, bst);
        
        splay.remove(30);
        bst.remove(30);
        
        bst = new BinarySearchTreeMap<>();
        int[] newBstOrder = {20, 80, 60, 40, 50, 70};
        for (int v : newBstOrder) {
            bst.put(v, v);
        }
        
        System.out.println("\nTree after deleting internal node(30):");
        printAndCompare(splay, bst);
    }
    
    // Test deleting the root node
    private static void testDeleteRoot() {
        System.out.println("\nTest Delete Root Node:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        ISortedMap<Integer, Integer> bst = new BinarySearchTreeMap<>();
        
        int[] initialValues = {50, 30, 70, 20, 40, 60, 80};
        for (int v : initialValues) {
            splay.put(v, v);
            bst.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        printTrees(splay, bst);
        
        splay.remove(50);
        bst.remove(50);
        
        bst = new BinarySearchTreeMap<>();
        int[] newBstOrder = {40, 20, 30, 70, 60, 80};
        for (int v : newBstOrder) {
            bst.put(v, v);
        }
        
        System.out.println("\nTree after deleting root node(50):");
        printAndCompare(splay, bst);
    }
    
    // Test Zig restructuring after deletion
    private static void testDeleteWithZigRestructuring() {
        System.out.println("\nTest Zig Restructuring After Deletion:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60, 80, 35};
        for (int v : initialOrder) {
            splay.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) splay);
        
        splay.remove(40);
        System.out.println("\nTree after deleting node(40):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) splay);
        
        ISortedMap<Integer, Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {35, 30, 20, 70, 60, 50, 80};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) splay, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) splay).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }

    // Test Zig-Zig restructuring after deletion
    private static void testDeleteWithZigZigRestructuring() {
        System.out.println("\nTest Zig-Zig Restructuring After Deletion:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        
        int[] initialOrder = {50, 30, 70, 20, 40, 60, 80, 10};
        for (int v : initialOrder) {
            splay.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) splay);
        
        splay.remove(30);
        System.out.println("\nTree after deleting node(30):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) splay);
        
        ISortedMap<Integer, Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {20, 10, 60, 40, 50, 80, 70};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) splay, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) splay).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }

    // Test Zig-Zag restructuring after deletion
    private static void testDeleteWithZigZagRestructuring() {
        System.out.println("\nTest Zig-Zag Restructuring After Deletion:");
        ISortedMap<Integer, Integer> splay = new SplayTreeMap<>();
        
        int[] initialOrder = {50, 20, 70, 10, 40, 30, 45, 60, 80};
        for (int v : initialOrder) {
            splay.put(v, v);
        }
        
        System.out.println("Tree before deletion:");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) splay);
        
        splay.remove(20);
        System.out.println("\nTree after deleting node(20):");
        TreePrinter.printTree((IBinaryTree<IEntry<Integer, Integer>>) splay);
        
        ISortedMap<Integer, Integer> expected = new BinarySearchTreeMap<>();
        int[] expectedOrder = {10, 70, 45, 30, 40, 60, 50, 80};
        for (int v : expectedOrder) {
            expected.put(v, v);
        }
        
        System.out.println("\nAre structures identical? " + 
            (areEqual((IBinaryTree<IEntry<Integer, Integer>>) splay, 
                     ((IBinaryTree<IEntry<Integer, Integer>>) splay).root(),
                     (IBinaryTree<IEntry<Integer, Integer>>) expected,
                     ((IBinaryTree<IEntry<Integer, Integer>>) expected).root()) ? "Yes! :-D" : "No! :-("));
    }
    
    // Insert nodes and compare Splay tree and BST structures
    private static void insertAndCompare(ISortedMap<Integer, Integer> splay, ISortedMap<Integer, Integer> bst, 
                                        int[] splayOrder, int[] bstOrder) {
        for (int v : splayOrder)
            splay.put(v, v);
        for (int v : bstOrder)
            bst.put(v, v);
        
        printAndCompare(splay, bst);
    }
    
    // Print and compare Splay tree and BST structures
    private static void printAndCompare(ISortedMap<Integer, Integer> splay, ISortedMap<Integer, Integer> bst) {
        IBinaryTree<IEntry<Integer,Integer>> t1 = (IBinaryTree<IEntry<Integer,Integer>>) splay;
        IBinaryTree<IEntry<Integer,Integer>> t2 = (IBinaryTree<IEntry<Integer,Integer>>) bst;
        
        System.out.println("Splay Tree Structure:");
        TreePrinter.printTree(t1);
        System.out.println("\nExpected Final Structure:");
        TreePrinter.printTree(t2);
        
        System.out.println("\nAre structures identical? " + 
            (areEqual(t1, t1.root(), t2, t2.root()) ? "Yes! :-D" : "No! :-("));
    }
    
    // Print Splay tree and BST structures
    private static void printTrees(ISortedMap<Integer, Integer> splay, ISortedMap<Integer, Integer> bst) {
        IBinaryTree<IEntry<Integer,Integer>> t1 = (IBinaryTree<IEntry<Integer,Integer>>) splay;
        IBinaryTree<IEntry<Integer,Integer>> t2 = (IBinaryTree<IEntry<Integer,Integer>>) bst;
        
        System.out.println("Splay Tree Structure:");
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
                   p1.element().value().equals(p2.element().value()) &&
                   areEqual(t1, t1.left(p1), t2, t2.left(p2)) && 
                   areEqual(t1, t1.right(p1), t2, t2.right(p2));
        }
        else {
            return false;
        }
    }
} 