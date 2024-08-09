import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyMapTest {
    MyMap<Integer, String> testMap;
    Map<Integer, String> map;
    int count = 10;
    Random rnd;
    String first_value;
    List<String> listValues;
    Set<Map.Entry<Integer, String>> setOfEntry;

    int capacityOfOneThread = 50;
    Lock lock = new ReentrantLock();

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

    // тест на потокобезопасность
    @Test
    void test_thread(){
        testMap.clear();
        testMap = new MyMap<>();
        Runnable runnable1 = () -> {
            for (int i = 0; i < capacityOfOneThread; i++){
                System.out.println(testMap.size());
                testMap.put(i, "///");
                //lock = new ReentrantLock();
                //lock.lock();
                System.out.println(Thread.currentThread().getName() + ":" + testMap);
                System.out.println(testMap.size());
                //lock.unlock();
            }
        };

        Runnable runnable2 = () -> {
            for (int i = 0; i < capacityOfOneThread; i++){
                //lock.lock();
                if (testMap.count > 0){
                    testMap.remove(testMap.myMap.getFirst().Key);
                }
                System.out.println(Thread.currentThread().getName() + ":" + testMap);
                System.out.println(testMap.size());
                //lock.unlock();
            }
        };
        //MyThread1 myThread = new MyThread1();
        //MyThread.keyNumber = 0;
        Thread firstThread = new Thread(runnable1);
        firstThread.start();
        Thread secondThread = new Thread(runnable2);
        secondThread.start();
    }

   /* class MyThread1 extends Thread{
        int capacityOfOneThread = 50;
        static int keyNumber; //в мап будут добавлены ключи от 1 до 100
        public void run(){
            for (int i = 0; i < capacityOfOneThread; i++){
                lock.lock();
                keyNumber++;
                lock.unlock();
                testMap.put(keyNumber, "...");
                System.out.println(Thread.currentThread().getName() + ":" + keyNumber);

            }
            System.out.println(testMap.keySet());
            System.out.println(testMap.size());
        }

    }*/

    @Test
    void test_equals_Keys() {
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
        System.out.println("Testing method clear()");
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