package ru.codeSnapshots.microservice.idempotency.service.coreImpl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.codeSnapshots.microservice.idempotency.core.ChainHandlerPriorityValues;
import ru.codeSnapshots.microservice.idempotency.service.IdempotencySidecarServiceChainHandler;

import java.util.HashSet;

@Order(ChainHandlerPriorityValues.HIGH)
@Service
public class ReducerCollisionsChainHandler implements IdempotencySidecarServiceChainHandler {
    private final static int REGISTER_EXECUTION_MS = 100;
    private final static int REQUESTS_PER_SECOND = 100;

    private final static Object s_onProcessIdsLocker = new Object();
    private final static HashSet<String> s_onProcessIds = new HashSet<String>(REQUESTS_PER_SECOND * REGISTER_EXECUTION_MS / 1000);

    @Override
    public void onStart(String requestId) {
        try {
            synchronized (s_onProcessIdsLocker) {
                if (!s_onProcessIds.add(requestId)) {
                    Thread.sleep(REGISTER_EXECUTION_MS / 2);
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onFinally(String requestId) {
        synchronized (s_onProcessIdsLocker) {
            s_onProcessIds.remove(requestId);
        }
    }
}
