package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> map = data.stream()
                .collect(Collectors.groupingBy(Measurement::getName, Collectors.summingDouble(Measurement::getValue)));

        return new TreeMap<String, Double>(map);
    }
}
