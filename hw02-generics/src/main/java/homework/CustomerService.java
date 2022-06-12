package homework;


import java.util.AbstractMap;
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

        return new AbstractMap.SimpleEntry<Customer, String>(
                new Customer(firstEntry.getKey().getId(), firstEntry.getKey().getName(), firstEntry.getKey().getScores()),
                firstEntry.getValue()
        );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var next = map.higherEntry(customer);

        if (next == null)
            return null;

        return new AbstractMap.SimpleEntry<Customer, String>(
                new Customer(next.getKey().getId(), next.getKey().getName(), next.getKey().getScores()),
                next.getValue()
        );
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
