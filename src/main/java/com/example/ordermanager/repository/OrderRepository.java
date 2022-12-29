package com.example.ordermanager.repository;

import com.example.ordermanager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    boolean existsRoleByName(String name);
    Optional<Order> findById(Long orderId);
    List<Order> findByStatusAndItemId(String orderStatus, Long itemId);
}
