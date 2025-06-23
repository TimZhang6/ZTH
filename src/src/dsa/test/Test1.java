package dsa.test;

import dsa.impl.AVLTreeMap;

/**
 * AVL Tree Get Operation Test Class
 * Used to test various get operations of AVL tree
 */
public class Test1 {

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
        
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        
        String result = map.get(50);
        System.out.println("Getting key 50 from empty tree: " + result);
        System.out.println("Expected result: null");
        System.out.println("Test result: " + (result == null ? "PASS" : "FAIL"));
    }
    
    // Test get operation on single node tree
    private static void testGetOnSingleNodeTree() {
        System.out.println("\n=== Testing get operation on single node tree ===");
        
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        
        map.put(50, "Fifty");
        
        String result1 = map.get(50);
        System.out.println("Getting root node key 50: " + result1);
        System.out.println("Expected result: Fifty");
        System.out.println("Test result: " + ("Fifty".equals(result1) ? "PASS" : "FAIL"));
    }
    
    // Test get operation on multi-node tree
    private static void testGetFromMultiNodeTree() {
        System.out.println("\n=== Testing get operation on multi-node tree ===");
        
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        
        map.put(50, "Fifty");
        map.put(25, "Twenty-Five");
        map.put(75, "Seventy-Five");
        map.put(12, "Twelve");
        map.put(37, "Thirty-Seven");
        map.put(62, "Sixty-Two");
        map.put(87, "Eighty-Seven");
        map.put(6, "Six");
        map.put(18, "Eighteen");
        map.put(95, "Ninety-Five");
       
        String result1 = map.get(50);
        System.out.println("\nGetting root node(50): " + result1);
        System.out.println("Expected result: Fifty");
        System.out.println("Test result: " + ("Fifty".equals(result1) ? "PASS" : "FAIL"));
        
        String result2 = map.get(6);
        System.out.println("\nGetting deep leaf node(6): " + result2);
        System.out.println("Expected result: Six");
        System.out.println("Test result: " + ("Six".equals(result2) ? "PASS" : "FAIL"));
        
        String result3 = map.get(25);
        System.out.println("\nGetting middle layer node(25): " + result3);
        System.out.println("Expected result: Twenty-Five");
        System.out.println("Test result: " + ("Twenty-Five".equals(result3) ? "PASS" : "FAIL"));
    }
    
    // Test get operation with non-existing key
    private static void testGetNonExistingKey() {
        System.out.println("\n=== Testing get operation with non-existing key ===");
        
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        
        map.put(50, "Fifty");
        map.put(30, "Thirty");
        map.put(70, "Seventy");
        map.put(20, "Twenty");
        map.put(40, "Forty");
        
        String result = map.get(60);
        System.out.println("\nGetting non-existing key(60): " + result);
        System.out.println("Expected result: null");
        System.out.println("Test result: " + (result == null ? "PASS" : "FAIL"));
    }
} 