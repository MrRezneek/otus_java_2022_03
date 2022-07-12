package ru.otus.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class MeasurementMixIn {
    @JsonCreator
    public MeasurementMixIn(@JsonProperty("name") String name, @JsonProperty("value")  double value) {}
}
