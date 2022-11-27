package ru.codeSnapshots.microservice.idempotency.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tb_requests", indexes = {
        @Index(columnList = "request_id_")
}, uniqueConstraints = {
        @UniqueConstraint(name = RequestEntity.UniqueConstraintName, columnNames = {"request_id_"})
})
@Getter
@Setter
public class RequestEntity {
    public final static String UniqueConstraintName = "uc_requests_request_id_";
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_", updatable = false, nullable = false)
    private String id;
    @Column(name = "request_id_")
    private String requestId;
    @Column(name = "order_id_")
    private String orderId;
}