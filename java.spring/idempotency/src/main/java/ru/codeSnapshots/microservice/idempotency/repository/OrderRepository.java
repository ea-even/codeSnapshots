package ru.codeSnapshots.microservice.idempotency.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.codeSnapshots.microservice.idempotency.model.entity.OrderEntity;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {
}