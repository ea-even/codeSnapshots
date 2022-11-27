package ru.codeSnapshots.microservice.idempotency.service.dbImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.codeSnapshots.microservice.idempotency.model.entity.RequestEntity;
import ru.codeSnapshots.microservice.idempotency.repository.RequestRepository;
import ru.codeSnapshots.microservice.idempotency.service.IdempotencySidecarService;
import ru.codeSnapshots.microservice.idempotency.service.IdempotencySidecarServiceChainHandler;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Service
public class DbIdempotencySidecarService implements IdempotencySidecarService {
    @Autowired
    private List<IdempotencySidecarServiceChainHandler> chain;

    private final RequestRepository requestRepository;

    public DbIdempotencySidecarService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public String findExisted(String requestId) {
        return requestRepository.findByRequestId(requestId).orElse(new RequestEntity()).getOrderId();
    }

    @Override
    public String register(String requestId, Supplier<String> action) {

        try {
            chain.forEach(x->x.onStart(requestId));
            String orderId = findExisted(requestId);
            if (Objects.nonNull(orderId)) return orderId;

            orderId = action.get();

            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setOrderId(orderId);
            requestEntity.setRequestId(requestId);
            requestRepository.save(requestEntity);
            return orderId;
        } finally {
            chain.forEach(x->x.onFinally(requestId));
        }
    }
}
