package dsa.test;

import dsa.impl.SplayTreeMap;

/**
 * Splay Tree Get Operation Test Class
 * Used to test various get operations of Splay tree
 */
public class Test2 {

    public static void main(String[] args) {
        // Test get operations in different scenarios
        testGetOnEmptyTree();         // Test get on empty tree
        testGetOnSingleNodeTree();    // Test get on single node tree
        testGetFromMultiNodeTree();   // Test get on multi-node tree
        testGetNonExistingKey();      // Test get with non-existing key
    }

    // Test get operation on empty tree
    private static void testGetOnEmptyTree() {
        System.out.println("\n=== Testing get operation on empty tree ===");
        
        SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
        
        String result = map.get(10);
        System.out.println("Getting key 10 from empty tree: " + result);
        System.out.println("Expected result: null");
        System.out.println("Test result: " + (result == null ? "PASS" : "FAIL"));
    }
    
    // Test get operation on single node tree
    private static void testGetOnSingleNodeTree() {
        System.out.println("\n=== Testing get operation on single node tree ===");
        
        SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
        
        map.put(10, "Ten");
        
        String result1 = map.get(10);
        System.out.println("Getting root node key 10: " + result1);
        System.out.println("Expected result: Ten");
        System.out.println("Test result: " + ("Ten".equals(result1) ? "PASS" : "FAIL"));
    }
    
    // Test get operation on multi-node tree
    private static void testGetFromMultiNodeTree() {
        System.out.println("\n=== Testing get operation on multi-node tree ===");
        
        SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
        
        map.put(20, "Twenty");
        map.put(10, "Ten");
        map.put(30, "Thirty");
        map.put(5, "Five");
        map.put(15, "Fifteen");
        map.put(25, "Twenty-Five");
        map.put(35, "Thirty-Five");
       
        String result1 = map.get(20);
        System.out.println("\nGetting root node(20): " + result1);
        System.out.println("Expected result: Twenty");
        System.out.println("Test result: " + ("Twenty".equals(result1) ? "PASS" : "FAIL"));
        
        String result2 = map.get(5);
        System.out.println("\nGetting leaf node(5): " + result2);
        System.out.println("Expected result: Five");
        System.out.println("Test result: " + ("Five".equals(result2) ? "PASS" : "FAIL"));
    }
    
    // Test get operation with non-existing key
    private static void testGetNonExistingKey() {
        System.out.println("\n=== Testing get operation with non-existing key ===");
        
        SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
        
        map.put(20, "Twenty");
        map.put(10, "Ten");
        map.put(30, "Thirty");
        
        String result = map.get(25);
        System.out.println("\nGetting non-existing key(25): " + result);
        System.out.println("Expected result: null");
        System.out.println("Test result: " + (result == null ? "PASS" : "FAIL"));
    }
}