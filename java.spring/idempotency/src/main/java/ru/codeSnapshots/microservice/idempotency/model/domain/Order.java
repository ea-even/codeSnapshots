package ru.codeSnapshots.microservice.idempotency.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("description")
    private String description;
}