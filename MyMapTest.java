import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class MyMapTest {
    MyMap<Integer, String> testMap;
    Map<Integer, String> map;
    int count = 10;
    Random rnd;
    String first_value;
    List<String> listValues;
    Set<Map.Entry<Integer, String>> setOfEntry;

    int capacityOfOneThread = 25;

    @BeforeEach
    void createMap(){
        testMap = new MyMap<>();
        map = new HashMap<>();
        rnd = new Random();
        listValues = new ArrayList<>();
        setOfEntry = new HashSet<>();
        var characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int lenOfCharacters = characters.length();
        for (int i = 0; i < count; i++){
            int lenOfString = rnd.nextInt(1, 8);
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < lenOfString; j++){
                str.append(characters.charAt(rnd.nextInt(1, lenOfCharacters)));
            }
            testMap.put(i, str.toString());
            map.put(i, str.toString());
            listValues.add(str.toString());
        }
        first_value = testMap.myMap.getFirst().getValue();
    }

    @AfterEach
    void clearMap(){
        testMap.clear();
        listValues.clear();
    }

    @Test
    void test_threadSafety() throws InterruptedException {
        testMap.clear();
        testMap = new MyMap<>();

        Runnable run1 = () -> {
            for (int i = 0; i < capacityOfOneThread; i++){
                testMap.put(i, "...");
            }
        };

        Runnable run2 = () -> {
            for (int i = capacityOfOneThread; i < capacityOfOneThread * 2; i++){
                testMap.put(i, "...");
            }
        };

        Runnable run3 = () -> {
            for (int i = capacityOfOneThread * 2; i < capacityOfOneThread * 3; i++){
                testMap.put(i, "...");
            }
        };

        Runnable run4 = () -> {
            for (int i = capacityOfOneThread * 3; i < capacityOfOneThread * 4; i++){
                testMap.put(i, "...");
            }
        };

        Thread firstThread = new Thread(run1);
        firstThread.start();
        Thread secondThread = new Thread(run2);
        secondThread.start();
        Thread thirdThread = new Thread(run3);
        thirdThread.start();
        Thread fourthThread = new Thread(run4);
        fourthThread.start();

        firstThread.join();
        secondThread.join();
        thirdThread.join();
        fourthThread.join();
        assertEquals(capacityOfOneThread * 4, testMap.size());
    }

    @Test
    void test_equalsKeys() {
        Object first = "fgh";
        Object second = "fkj";
        assertFalse(testMap.equalsKeys(first, second));
    }

    @Test
    void test_size() {
        assertEquals(count, testMap.size());
    }
    @Test
    void test_isEmpty(){
        assertFalse(testMap.isEmpty());
    }

    @Test
    void test_containsKey() {
        assertTrue(testMap.containsKey(rnd.nextInt(0, count)));
    }

    @Test
    void test_containsValue() {
        assertTrue(testMap.containsValue(first_value));
    }

    @Test
    void test_get() {
        assertEquals(first_value, testMap.get(0));
    }

    @Test
    void test_putExistingKey() {
        assertEquals(first_value, testMap.get(0));
    }

    @Test
    void test_putNonExistentKey(){
        assertNull(testMap.put(20, "..."));
    }

    @Test
    void test_removeValue() {
        assertEquals(first_value, testMap.remove(0));
    }

    @Test
    void test_removeAvailability(){
        testMap.remove(0);
        assertFalse(testMap.containsKey(0));
    }

    @Test
    void test_putAll() {
        System.out.println("Testing method putAll():");
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "First");
        map.put(2, "Second");
        map.put(3, "Third");
        System.out.println("First Map content:\n" + map);
        testMap = new MyMap<>();
        testMap.putAll(map);
        System.out.println("Second Map content:\n" + testMap + "\n");
        assertEquals(3, testMap.size());
    }

    @Test
    void test_clear() {
        System.out.println("Testing method clear():");
        System.out.println("Map content before purification");
        System.out.println(testMap.toString());
        testMap.clear();
        System.out.println("Map content after purification");
        System.out.println(testMap.toString());
        assertEquals(0, testMap.size());
    }

    @Test
    void test_keySet() {
        Set<Integer> k_set = map.keySet();
        assertEquals(k_set, testMap.keySet());
    }

    @Test
    void test_values() {
        assertEquals(listValues, testMap.values());
    }

    @Test
    void test_entrySet() {
        assertEquals(map.entrySet(), testMap.entrySet());
    }
}