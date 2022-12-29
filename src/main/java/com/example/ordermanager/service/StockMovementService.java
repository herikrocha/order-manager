package com.example.ordermanager.service;

import com.example.ordermanager.dto.StockMovementDTO;
import com.example.ordermanager.entity.Item;
import com.example.ordermanager.entity.Order;
import com.example.ordermanager.entity.StockMovement;
import com.example.ordermanager.enums.OrderStatus;
import com.example.ordermanager.enums.OrderType;
import com.example.ordermanager.exception.InvalidArgumentException;
import com.example.ordermanager.exception.ResourceNotFoundException;
import com.example.ordermanager.repository.OrderRepository;
import com.example.ordermanager.repository.StockMovementRepository;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMovementService {

    public List<StockMovementDTO> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(stock -> StockMovementDTO.builder()
                        .id(stock.getId())
                        .quantity(stock.getQuantity())
                        .type(stock.getType())
                        .creationDate(stock.getCreationDate())
                        .itemId(stock.getItem().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public StockMovementDTO create(StockMovementDTO dto) {
        createStockMovement(dto);
        satisfyPendingOrders(dto);
        return dto;
    }

    public StockMovementDTO update(StockMovementDTO dto) {
        StockMovement stock = stockRepository.findById(dto.getId()).orElseThrow(() -> new InvalidArgumentException(AppConstants.STOCK_NOT_FOUND));
        updateStockMovement(stock, dto);
        return dto;
    }

    public void delete(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException(AppConstants.STOCK_NOT_FOUND);
        }
        stockRepository.deleteById(stockId);
    }

    private void createStockMovement(StockMovementDTO dto) {
        Item item = itemService.findById(dto.getItemId());
        updateStockItem(dto, item);
        StockMovement stock = StockMovement.builder()
                .quantity(dto.getQuantity())
                .type(dto.getType())
                .creationDate(LocalDateTime.now())
                .item(item)
                .build();
        stockRepository.saveAndFlush(stock);
        dto.setCreationDate(stock.getCreationDate());
        dto.setId(stock.getId());
    }

    private void updateStockItem(StockMovementDTO dto, Item item) {
        if (dto.getType().equals(OrderType.WITHDRAWAL)) {
            stockWithdrawal(item, dto.getQuantity());
        } else if (dto.getType().equals(OrderType.ENTRY)) {
            itemService.performStockEntry(item, dto.getQuantity());
        }
    }

    private void stockWithdrawal(Item item, Integer quantity) {
        if (!itemService.performStockWithdrawal(item, quantity)) {
            throw new InvalidArgumentException(AppConstants.INVALID_STOCK_WITHDRAWAL);
        }
    }

    public void createStockMovement(Order order, OrderType ordertype) {
        StockMovement stock = StockMovement.builder()
                .quantity(order.getQuantity())
                .type(ordertype)
                .creationDate(LocalDateTime.now())
                .item(order.getItem())
                .build();
        stockRepository.saveAndFlush(stock);
    }

    private void updateStockMovement(StockMovement stock, StockMovementDTO dto) {
        Item item = itemService.findById(dto.getItemId());
        stock.setQuantity(dto.getQuantity());
        stock.setType(dto.getType());
        stock.setItem(item);
        stockRepository.saveAndFlush(stock);
        dto.setCreationDate(stock.getCreationDate());
    }

    private void satisfyPendingOrders(StockMovementDTO dto) {
        List<Order> ordersAwaitingStock = orderRepository.findByStatusAndItemId(OrderStatus.AWAITING_STOCK, dto.getItemId());
        ordersAwaitingStock.forEach(order -> {
            if (itemService.performStockWithdrawal(order.getItem(), order.getQuantity())) {
                order.setStatus(OrderStatus.FINISHED);
            }
        });
    }

    @Resource
    StockMovementRepository stockRepository;

    @Resource
    ItemService itemService;

    @Resource
    OrderRepository orderRepository;
}
