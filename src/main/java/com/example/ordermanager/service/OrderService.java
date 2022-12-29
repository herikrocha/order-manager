package com.example.ordermanager.service;

import com.example.ordermanager.dto.OrderDTO;
import com.example.ordermanager.dto.StockMovementDTO;
import com.example.ordermanager.entity.Item;
import com.example.ordermanager.entity.Order;
import com.example.ordermanager.entity.User;
import com.example.ordermanager.enums.OrderStatus;
import com.example.ordermanager.enums.OrderType;
import com.example.ordermanager.exception.InvalidArgumentException;
import com.example.ordermanager.exception.ResourceNotFoundException;
import com.example.ordermanager.repository.ItemRepository;
import com.example.ordermanager.repository.OrderRepository;
import com.example.ordermanager.repository.UserRepository;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(stock -> OrderDTO.builder()
                        .id(stock.getId())
                        .creationDate(stock.getCreationDate())
                        .quantity(stock.getQuantity())
                        .userId(stock.getUser().getId())
                        .itemId(stock.getItem().getId())
                        .status(stock.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public OrderDTO create(OrderDTO orderDTO) {
        Item item = getItemById(orderDTO.getItemId());
        User user = getUserById(orderDTO.getUserId());
        Order order = createOrder(orderDTO, user, item);
        satisfyOrder(order);
        updateResponse(orderDTO, order);
        return orderDTO;
    }

    public OrderDTO changeStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidArgumentException(AppConstants.ORDER_NOT_FOUND));
        updateOrder(order, OrderStatus.CANCELED);
        return OrderDTO.builder()
                .id(orderId)
                .creationDate(order.getCreationDate())
                .itemId(order.getItem().getId())
                .quantity(order.getQuantity())
                .userId(order.getUser().getId())
                .status(order.getStatus())
                .build();
    }

    public void delete(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException(AppConstants.ORDER_NOT_FOUND);
        }

        orderRepository.deleteById(orderId);
    }

    private Order createOrder(OrderDTO orderDTO, User user, Item item) {
        Order order = Order.builder()
                .creationDate(LocalDateTime.now())
                .quantity(orderDTO.getQuantity())
                .user(user)
                .item(item)
                .build();
        setInitialOrderStatus(order);
        orderRepository.saveAndFlush(order);
        return order;
    }

    private void updateResponse(OrderDTO orderDTO, Order order) {
        orderDTO.setCreationDate(order.getCreationDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setId(order.getId());
    }

    private void updateOrder(Order order, OrderStatus orderStatus) {
        if (order.getStatus().equals(OrderStatus.AWAITING_STOCK)) {
            order.setStatus(orderStatus.toString());
            orderRepository.saveAndFlush(order);
        } else {
            throw new InvalidArgumentException(AppConstants.INVALID_ORDER_STATUS);
        }
    }

    private void satisfyOrder(Order order) {
        if (itemService.performStockWithdrawal(order.getItem(), order.getQuantity())) {
            stockMovementService.createStockMovement(order, OrderType.WITHDRAWAL);
            setFinishOrder(order);
        }
    }

    private void setInitialOrderStatus(Order order) {
        order.setStatus(OrderStatus.AWAITING_STOCK.toString());
    }

    private void setFinishOrder(Order order) {
        order.setStatus(OrderStatus.FINISHED.toString());
        // TODO: Notify order finished
    }

    private Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new InvalidArgumentException(AppConstants.ITEM_NOT_FOUND));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new InvalidArgumentException(AppConstants.USER_NOT_FOUND));
    }

    @Resource
    ItemService itemService;

    @Resource
    StockMovementService stockMovementService;

    @Resource
    OrderRepository orderRepository;

    @Resource
    ItemRepository itemRepository;

    @Resource
    UserRepository userRepository;

}
