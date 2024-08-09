import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class MyMap<K, V> implements Map<K, V> {
    ArrayList<MyMap.MyEntry<K, V>> myMap;
    int count;
    ReentrantLock lock;

    public MyMap() {
        myMap = new ArrayList<>();
        lock = new ReentrantLock();
    }

    boolean equalsKeys(Object _key, Object Key) {
        lock.lock();
        try {
            if (_key == Key) {
                return true;
            }
            if (_key instanceof Integer && Key instanceof Integer) {
                return _key == Key;
            } else if (_key instanceof Character && Key instanceof Character) {
                char str_key = (char) _key;
                char strKey = (char) Key;

                return str_key == strKey;
            } else if (_key instanceof String str_key && Key instanceof String strKey) {
                if (str_key.length() != strKey.length()) {
                    return false;
                }

                for (int i = 0; i < str_key.length(); i++) {
                    if (str_key.charAt(i) != strKey.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public int size() {
        int _count = 0;
        lock.lock();
        for (MyEntry<K, V> pair : myMap) {
            _count++;
        }
        count = _count;
        lock.unlock();
        return count;
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        if (myMap == null) {
            return true;
        }
        lock.unlock();
        return count == 0;
    }

    @Override
    public boolean containsKey(Object _key) {
        if (_key == null) {
            throw new NullPointerException();
        }

        lock.lock();

        if (!myMap.isEmpty()) {
            K check_key = myMap.getFirst().getKey();
            if (!_key.getClass().isInstance(check_key)) {
                throw new ClassCastException();
            }
        }
        try {
            for (MyEntry<K, V> pair : myMap) {
                K Key = pair.getKey();
                switch (_key) {
                    case Integer integer when Key instanceof Integer -> {
                        if (_key == Key) {
                            return true;
                        }
                    }
                    case Character c when Key instanceof Character -> {
                        char str_key = (char) _key;
                        char strKey = (char) Key;

                        if (str_key == strKey) {
                            return true;
                        }
                    }
                    case String str_key when Key instanceof String -> {
                        String strKey = (String) Key;

                        if (str_key.length() != strKey.length()) {
                            continue;
                        }

                        int flag = 0;
                        for (int i = 0; i < str_key.length(); i++) {
                            if (str_key.charAt(i) != strKey.charAt(i)) {
                                flag = 1;
                                break;
                            }
                        }

                        if (flag == 0) {
                            return true;
                        }
                    }
                    default -> {
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean containsValue(Object _value) {
        V check_value = myMap.getFirst().getValue();
        if (!_value.getClass().isInstance(check_value)) {
            throw new ClassCastException();
        }
        lock.lock();
        try {
            for (MyEntry<K, V> pair : myMap) {
                V Value = pair.getValue();
                switch (_value) {
                    case Integer integer when Value instanceof Integer -> {
                        if (_value == Value) {
                            return true;
                        }
                    }
                    case Character c when Value instanceof Character -> {
                        char str_value = (char) _value;
                        char strKey = (char) Value;

                        if (str_value == strKey) {
                            return true;
                        }
                    }
                    case String str_value when Value instanceof String -> {
                        String strValue = (String) Value;

                        if (str_value.length() != strValue.length()) {
                            return false;
                        }

                        int flag = 0;
                        for (int i = 0; i < str_value.length(); i++) {
                            if (str_value.charAt(i) != strValue.charAt(i)) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            return true;
                        }
                    }
                    case Boolean b when Value instanceof Boolean -> {
                        if (_value == Value) {
                            return true;
                        }
                    }
                    default -> {
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public V get(Object key) {
        lock.lock();
        if (!myMap.isEmpty() && !key.getClass().isInstance(myMap.getFirst().getKey())) {
            throw new ClassCastException();
        }
        if (key == null) {
            throw new NullPointerException();
        }
        try {
            for (MyEntry<K, V> pair : myMap) {
                if (equalsKeys(key, pair.getKey())) {
                    return pair.getValue();
                }
            }
        }
        finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public V put(Object _key, Object _value) {
        if (_key == null) {
            throw new NullPointerException("The key has a value of null");
        }
        lock.lock();
        if (containsKey(_key)) {
            return get(_key);
        }
        try {
            K Key = (K) _key;
        } catch (ClassCastException ex) {
            throw new ClassCastException("The key of the value being added is not of the correct type");
        }
        try {
            V Value = (V) _value;
        } catch (ClassCastException ex) {
            throw new ClassCastException("The value has the wrong type");
        }
        MyEntry<K, V> pair;
        try {
            pair = new MyEntry<>((K) _key, (V) _value);
            myMap.add(pair);
            count++;
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public V remove(Object _key) {
        if (_key == null) {
            throw new NullPointerException("The key has a value of null");
        }
        if (!containsKey(_key)) {
            return null;
        }
        K check_key;
        try {
            check_key = (K) _key;
        } catch (ClassCastException ex) {
            throw new ClassCastException("The key has the wrong type");
        }
        lock.lock();
        try {
            for (int i = 0; i < myMap.size(); i++) {
                if (equalsKeys(myMap.get(i).getKey(), check_key)) {
                    V value = myMap.get(i).getValue();
                    myMap.remove(i);
                    count--;
                    return value;
                }
            }
        }
        finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        lock.lock();
        count = 0;
        for (Object Key : m.keySet()) {
            put(Key, m.get(Key));
        }
        lock.unlock();
    }

    @Override
    public void clear() {
        lock.lock();
        for (int i = 0; i < count; i++) {
            remove(myMap.get(i).getKey());
            i--;
        }
        lock.unlock();
    }

    @Override
    public Set<K> keySet() {
        lock.lock();
        Set<K> setKey = new HashSet<>();
        for (MyEntry<K, V> pair : myMap) {
            setKey.add(pair.getKey());
        }
        lock.unlock();
        return setKey;
    }

    @Override
    public Collection<V> values() {
        lock.lock();
        List<V> Values = new ArrayList<>();
        for (MyEntry<K, V> pair : myMap) {
            Values.add(pair.getValue());
        }
        lock.unlock();
        return Values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        lock.lock();
        Set<Entry<K, V>> setPair = new HashSet<>();
        for (Entry<K, V> pair : myMap) {
            setPair.add(pair);
        }
        lock.unlock();
        return setPair;
    }

    @Override
    public String toString(){
        lock.lock();
        StringBuilder res = new StringBuilder();
        res.append("{");
        for (int i = 0; i < count; i++){
            res.append(myMap.get(i).toString());
            if (i < count - 1){
                res.append(", ");
            }
        }
        res.append("}");
        lock.unlock();
        return res.toString();
    }

    static class MyEntry<K, V> implements Entry<K, V> {
        K Key;
        V Value;
        public MyEntry(K key, V value){
            Key = key;
            Value = value;
        }

        @Override
        public K getKey() {
            return Key;
        }

        @Override
        public V getValue() {
            return Value;
        }

        @Override
        public V setValue(Object value) {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this){
                return true;
            }
            if (o instanceof MyEntry<?,?> entry){
                if (entry.getValue() == Value && entry.getKey() == Key){
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode(){
            return 31 * Key.hashCode() + Value.hashCode();
        }

        @Override
        public String toString(){
            return Key + "=" + Value;
        }
    }
}
