import java.util.*;

public class Main {
    public static HashMap<Integer, String> mp;

    public static void main(String[] args) {
        mp = new HashMap<>();
        mp.put(1, "1");
        mp.put(2, "2");
        mp.put(3, "909090");

        MyMap<Integer, String> Map = new MyMap<>();
        Map.put(1, "ghj");
        Map.put(2, "hvs;a");
        int h = mp.hashCode();
        var o = Map.get(2);



        /*Optional<MyMap> Map1 = Optional.of(Map);
        System.out.println(Map1.get());*/

    }

}
