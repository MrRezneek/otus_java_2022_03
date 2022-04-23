package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        var firstEntry = map.firstEntry();

        return new Entry<Customer, String>(
                new Customer(firstEntry.getKey().getId(), firstEntry.getKey().getName(), firstEntry.getKey().getScores()),
                firstEntry.getValue()
        );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var next = map.higherEntry(customer);

        if (next == null)
            return null;

        return new Entry<Customer, String>(
                new Customer(next.getKey().getId(), next.getKey().getName(), next.getKey().getScores()),
                next.getValue()
        );
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    static boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }

    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            return o instanceof Map.Entry<?, ?> e
                    && valEquals(key,e.getKey())
                    && valEquals(value,e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }
}
