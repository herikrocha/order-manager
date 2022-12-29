package com.example.ordermanager.controller;

import com.example.ordermanager.dto.StockMovementDTO;
import com.example.ordermanager.service.StockMovementService;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stockMovements")
public class StockMovementController {

    @Transactional
    @PostMapping
    public ResponseEntity<StockMovementDTO> save(@RequestBody @Valid StockMovementDTO stockDTO) {
        return ResponseEntity.ok(stockMovementService.create(stockDTO));
    }

    @GetMapping
    public ResponseEntity<List<StockMovementDTO>> findAll() {
        return ResponseEntity.ok(stockMovementService.findAll());
    }

    /* This endpoint was created to satisfy the exercise, but there is no reason to update a stockMovement
       previously created. In this way, when updating a stockMovement, no action will be taken on the stock
       of items or changes in orders.
    */
    @Transactional
    @PutMapping
    public ResponseEntity<StockMovementDTO> update(@RequestBody @Valid StockMovementDTO stockDTO) {
        return ResponseEntity.ok(stockMovementService.update(stockDTO));
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("stockId") Long stockId) {
        stockMovementService.delete(stockId);
        return new ResponseEntity<>(AppConstants.STOCK_DELETED, HttpStatus.OK);
    }

    @Resource
    StockMovementService stockMovementService;
}
