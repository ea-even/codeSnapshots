package ru.codeSnapshots.microservice.idempotency.service;

import java.util.function.Supplier;

/**
 * Service of Idempotency any entity
 */
public interface IdempotencySidecarService {
    /**
     * find existed entity id by requestId
     *
     * @param requestId
     * @return entity id register by requestId or null if there are no creation
     */
    String findExisted(String requestId);


    /**
     * Associate requestId and entity id given by action
     *
     * @param requestId
     * @param action
     * @return entity id
     */
    String register(String requestId, Supplier<String> action);
}
