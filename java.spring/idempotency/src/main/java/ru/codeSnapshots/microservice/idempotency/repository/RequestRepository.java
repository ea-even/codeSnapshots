package ru.codeSnapshots.microservice.idempotency.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.codeSnapshots.microservice.idempotency.model.entity.RequestEntity;

import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, String> {
    Optional<RequestEntity> findByRequestId(String requestId);
}