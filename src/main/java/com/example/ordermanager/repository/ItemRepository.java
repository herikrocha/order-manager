package com.example.ordermanager.repository;

import com.example.ordermanager.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsItemByName(String name);
    Optional<Item> findById(Long itemId);
    Optional<Item> findByName(String itemName);
}
