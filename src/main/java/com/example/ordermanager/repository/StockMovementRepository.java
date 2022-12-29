package com.example.ordermanager.repository;

import com.example.ordermanager.entity.StockMovement;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

//    boolean existsRoleByName(String name);
    Optional<StockMovement> findById(Long stockId);
}
