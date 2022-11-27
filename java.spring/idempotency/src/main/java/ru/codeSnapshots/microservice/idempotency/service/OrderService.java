package ru.codeSnapshots.microservice.idempotency.service;


import ru.codeSnapshots.microservice.idempotency.model.domain.Order;

/**
 * Some CRUD entity service
 */
public interface OrderService {
    String create(Order order, String requestId);
}