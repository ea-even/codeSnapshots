package ru.codeSnapshots.microservice.idempotency.service.dbImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import ru.codeSnapshots.microservice.idempotency.model.domain.Order;
import ru.codeSnapshots.microservice.idempotency.model.entity.OrderEntity;
import ru.codeSnapshots.microservice.idempotency.model.entity.RequestEntity;
import ru.codeSnapshots.microservice.idempotency.repository.OrderRepository;
import ru.codeSnapshots.microservice.idempotency.service.IdempotencySidecarService;
import ru.codeSnapshots.microservice.idempotency.service.OrderService;

@Service
public class DbOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final PlatformTransactionManager transactionManager;
    private final IdempotencySidecarService idempotencySidecarService;

    @Autowired
    public DbOrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager, IdempotencySidecarService idempotencySidecarService) {
        this.transactionManager = transactionManager;
        this.orderRepository = orderRepository;
        this.idempotencySidecarService = idempotencySidecarService;
    }

    @Override
    public String create(Order order, String requestId) {
        try {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);

            return transactionTemplate.execute(new TransactionCallback<String>() {
                public String doInTransaction(TransactionStatus status) {
                    return idempotencySidecarService.register(requestId, () -> CreateOrder(order));
                }
            });
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getMessage().contains(RequestEntity.UniqueConstraintName)) {
                return idempotencySidecarService.findExisted(requestId);
            }
            throw e;
        }
    }

    private String CreateOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName(order.getName());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setDescription(order.getDescription());
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }
}

