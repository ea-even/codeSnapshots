package ru.codeSnapshots.microservice.idempotency.service;

public interface IdempotencySidecarServiceChainHandler {
    void onStart(String requestId);

    void onFinally(String requestId);
}
