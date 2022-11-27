package ru.codeSnapshots.microservice.idempotency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codeSnapshots.microservice.idempotency.model.domain.Order;
import ru.codeSnapshots.microservice.idempotency.service.OrderService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/order")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody Order order, @RequestHeader("X-Request-Id") String requestId, HttpServletResponse httpServletResponse) {
        String orderId = orderService.create(order, requestId);
        httpServletResponse.setHeader("X-Order-Id", orderId);
    }
}