package dsa.test;

import dsa.impl.AVLTreeMap;

/**
 * AVL Tree Put Operation Test Class
 * Used to test various put operations of AVL tree
 */
public class Test3 {

    public static void main(String[] args) {
        // Test put operations in different scenarios
        testReplaceExistingValue();    // Test replacing existing values
        testNullValue();               // Test handling null values
    }

    // Test replacing existing values in the tree
    private static void testReplaceExistingValue() {
        System.out.println("=== Testing replacing existing values ===");
        
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        
        map.put(50, "A-Initial");
        map.put(30, "B-Initial");
        map.put(70, "C-Initial");
        map.put(20, "D-Initial");
        map.put(40, "E-Initial");
        map.put(60, "F-Initial");
        map.put(80, "G-Initial");
        
        System.out.println("Initial value - key 50: " + map.get(50));
        
        map.put(50, "A-Updated");
        
        String newValue = map.get(50);
        System.out.println("\nUpdated value - key 50: " + newValue);
        System.out.println("Expected value: A-Updated");
        System.out.println("Test result: " + ("A-Updated".equals(newValue) ? "PASS" : "FAIL"));
        
        map.put(20, "D-Updated");
        
        newValue = map.get(20);
        System.out.println("\nUpdated value - key 20: " + newValue);
        System.out.println("Expected value: D-Updated");
        System.out.println("Test result: " + ("D-Updated".equals(newValue) ? "PASS" : "FAIL"));
        
        map.put(30, "B-Update1");
        map.put(30, "B-Update2");
        map.put(30, "B-Update3");
        
        newValue = map.get(30);
        System.out.println("\nFinal value after multiple updates - key 30: " + newValue);
        System.out.println("Expected value: B-Update3");
        System.out.println("Test result: " + ("B-Update3".equals(newValue) ? "PASS" : "FAIL"));
    }

    // Test handling null values in the tree
    private static void testNullValue() {
        System.out.println("\n=== Testing null value case ===");
        
        AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
        
        map.put(100, null);
        String value = map.get(100);
        System.out.println("Inserted key 100 with null value");
        System.out.println("Value for key 100: " + value);
        System.out.println("Expected value: null");
        System.out.println("Test result: " + (value == null ? "PASS" : "FAIL"));
    }
}