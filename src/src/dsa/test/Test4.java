package dsa.test;

import dsa.impl.SplayTreeMap;

/**
 * Splay Tree Put Operation Test Class
 * Used to test various put operations of Splay tree
 */
public class Test4 {

    public static void main(String[] args) {
        // Test put operations in different scenarios
        testReplaceExistingValue();    // Test replacing existing values
        testNullValue();               // Test handling null values
    }

    // Test replacing existing values in the tree
    private static void testReplaceExistingValue() {
        System.out.println("=== Testing replacing existing values ===");
        
        SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
        
        map.put(200, "A-Initial");
        map.put(100, "B-Initial");
        map.put(300, "C-Initial");
        map.put(50, "D-Initial");
        map.put(150, "E-Initial");
        map.put(250, "F-Initial");
        map.put(350, "G-Initial");
        
        System.out.println("Initial value - key 200: " + map.get(200));
        
        map.put(200, "A-Updated");
        
        String newValue = map.get(200);
        System.out.println("\nUpdated value - key 200: " + newValue);
        System.out.println("Expected value: A-Updated");
        System.out.println("Test result: " + ("A-Updated".equals(newValue) ? "PASS" : "FAIL"));
        
        map.put(50, "D-Updated");
        
        newValue = map.get(50);
        System.out.println("\nUpdated value - key 50: " + newValue);
        System.out.println("Expected value: D-Updated");
        System.out.println("Test result: " + ("D-Updated".equals(newValue) ? "PASS" : "FAIL"));
        
        map.put(100, "B-Update1");
        map.put(100, "B-Update2");
        map.put(100, "B-Update3");
        
        newValue = map.get(100);
        System.out.println("\nFinal value after multiple updates - key 100: " + newValue);
        System.out.println("Expected value: B-Update3");
        System.out.println("Test result: " + ("B-Update3".equals(newValue) ? "PASS" : "FAIL"));
    }

    // Test handling null values in the tree
    private static void testNullValue() {
        System.out.println("\n=== Testing null value case ===");
        
        SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
        
        map.put(999, null);
        String value = map.get(999);
        System.out.println("Inserted key 999 with null value");
        System.out.println("Value for key 999: " + value);
        System.out.println("Expected value: null");
        System.out.println("Test result: " + (value == null ? "PASS" : "FAIL"));
    }
} 