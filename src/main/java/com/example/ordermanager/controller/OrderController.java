package com.example.ordermanager.controller;

import com.example.ordermanager.dto.OrderDTO;
import com.example.ordermanager.service.OrderService;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Transactional
    @PostMapping
    public ResponseEntity<OrderDTO> save(@RequestBody @Valid OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @Transactional
    @PutMapping("/cancel")
    public ResponseEntity<OrderDTO> cancel(@RequestParam("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.changeStatus(orderId));
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("orderId") Long orderId) {
        orderService.delete(orderId);
        return new ResponseEntity<>(AppConstants.STOCK_DELETED, HttpStatus.OK);
    }

    @Resource
    OrderService orderService;

}
