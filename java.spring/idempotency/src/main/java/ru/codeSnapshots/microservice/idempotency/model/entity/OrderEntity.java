package ru.codeSnapshots.microservice.idempotency.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tb_orders")
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_", updatable = false, nullable = false)
    private String id;
    @Column(name = "name_")
    private String name;
    @Column(name = "price_")
    private Double price;
    @Column(name = "description_")
    private String description;
}