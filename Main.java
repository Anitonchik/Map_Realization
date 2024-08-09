import java.util.*;
import java.util.Scanner;

public class Main {
    public static MyMap<?,?> myMap;

    static int choice;
    static int typeKey;
    static int typeValue;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("select action:");
        System.out.println("1 -> create map");
        System.out.println("2 -> size of the map");
        System.out.println("3 -> is empty (true / false)");
        System.out.println("4 -> contains key (true / false)");
        System.out.println("5 -> contains value (true / false)");
        System.out.println("6 -> get value");
        System.out.println("7 -> put entry to map (key value)");
        System.out.println("8 -> remove entry for key");
        System.out.println("9 -> clear map");
        System.out.println("10 -> return set of keys");
        System.out.println("11 -> return collection of values");
        System.out.println("12 -> show map in console");

        while (true) {

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    myMap = new MyMap<>();
                    System.out.println("enter type of keys:");
                    System.out.println("1 -> Integer");
                    System.out.println("2 -> Character");
                    System.out.println("3 -> String");
                    typeKey = scanner.nextInt();
                    System.out.println("enter type of values");
                    System.out.println("1 -> Integer");
                    System.out.println("2 -> Character");
                    System.out.println("3 -> String");
                    System.out.println("4 -> Boolean");
                    typeValue = scanner.nextInt();

                    myMap = new MyMap<>();

                    System.out.println("done");
                    break;
                case 2:
                    System.out.println(myMap.size());
                    break;
                case 3:
                    System.out.println(myMap.isEmpty());
                    break;
                case 4:
                    System.out.println("enter key");
                    Object key = null;
                    if (typeKey == 1){
                        key = scanner.nextInt();
                    }
                    if (typeKey == 2){
                        key = scanner.next().charAt(0);
                    }
                    if (typeKey == 3){
                        key = scanner.next();
                    }
                    System.out.println(myMap.containsKey(key));
                    break;
                case 5:
                    System.out.println("enter value");
                    Object value = null;
                    if (typeValue == 1){
                        value = scanner.nextInt();
                    }
                    if (typeValue == 2){
                        value = scanner.next().charAt(0);
                    }
                    if (typeValue == 3){
                        value = scanner.next();
                    }
                    if (typeValue == 4){
                        value = scanner.nextBoolean();
                    }
                    System.out.println(myMap.containsKey(value));
                    break;
                case 6:
                    System.out.println("enter key");
                    Object _key = null;
                    if (typeKey == 1){
                        _key = scanner.nextInt();
                    }
                    if (typeKey == 2){
                        _key = scanner.next().charAt(0);
                    }
                    if (typeKey == 3){
                        _key = scanner.next();
                    }
                    System.out.println(myMap.get(_key));
                    break;
                case 7:
                    System.out.println("enter entry (key 'enter' value)");
                    Object keyPut = null;
                    if (typeKey == 1){
                        keyPut = scanner.nextInt();
                    }
                    if (typeKey == 2){
                        keyPut = scanner.next().charAt(0);
                    }
                    if (typeKey == 3){
                        keyPut = scanner.next();
                    }
                    Object valuePut = null;
                    if (typeValue == 1){
                        valuePut = scanner.nextInt();
                    }
                    if (typeValue == 2){
                        valuePut = scanner.next().charAt(0);
                    }
                    if (typeValue == 3){
                        valuePut = scanner.next();
                    }
                    if (typeValue == 4){
                        valuePut = scanner.nextBoolean();
                    }
                    System.out.println(myMap.put(keyPut, valuePut));
                    break;
                case 8:
                    System.out.println("enter the key to delete the corresponding pair");
                    Object keyRem = null;
                    if (typeKey == 1){
                        keyRem = scanner.nextInt();
                    }
                    if (typeKey == 2){
                        keyRem = scanner.next().charAt(0);
                    }
                    if (typeKey == 3){
                        keyRem = scanner.next();
                    }
                    System.out.println(myMap.remove(keyRem));
                    break;
                case 9:
                    myMap.clear();
                    System.out.println("done");
                    break;
                case 10:
                    System.out.println(myMap.keySet());
                    break;
                case 11:
                    System.out.println(myMap.values());
                    break;
                case 12:
                    System.out.println(myMap);
                    break;
            }
        }
    }
}
