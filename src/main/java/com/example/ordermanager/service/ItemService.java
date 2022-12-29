package com.example.ordermanager.service;

import com.example.ordermanager.dto.ItemDTO;
import com.example.ordermanager.entity.Item;
import com.example.ordermanager.exception.InvalidArgumentException;
import com.example.ordermanager.exception.ResourceNotFoundException;
import com.example.ordermanager.repository.ItemRepository;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    public List<ItemDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .stockAmount(item.getStockAmount())
                        .build())
                .collect(Collectors.toList());
    }

    public Item findById(Long itemId) {
        return repository.findById(itemId).orElseThrow(() -> new InvalidArgumentException(AppConstants.ITEM_NOT_FOUND));
    }

    public ItemDTO create(ItemDTO dto) {
        if (repository.existsItemByName(dto.getName())) {
            throw new InvalidArgumentException(AppConstants.INVALID_ITEM_NAME);
        }
        createItem(dto);
        return dto;
    }

    public ItemDTO update(ItemDTO dto) {
        Item item = repository.findById(dto.getId()).orElseThrow(() -> new InvalidArgumentException(AppConstants.ITEM_NOT_FOUND));
        updateItem(item, dto);
        return dto;
    }

    public void delete(Long itemId) {
        if (!repository.existsById(itemId)) {
            throw new ResourceNotFoundException(AppConstants.ITEM_NOT_FOUND);
        }
        repository.deleteById(itemId);
    }

    private void createItem(ItemDTO dto) {
        Item item = Item.builder()
                .name(dto.getName())
                .stockAmount(0)
                .build();
        repository.saveAndFlush(item);
        dto.setStockAmount(0);
        dto.setId(item.getId());
    }

    private void updateItem(Item item, ItemDTO dto) {
        item.setName(dto.getName());
        repository.saveAndFlush(item);
    }

    public boolean performStockWithdrawal(Item item, Integer quantity) {
        if (hasStockAmount(item, quantity)) {
            item.setStockAmount(item.getStockAmount() - quantity);
            repository.saveAndFlush(item);
            return true;
        }
        return false;
    }

    public boolean hasStockAmount(Item item, Integer quantity) {
        return quantity <= item.getStockAmount();
    }

    public void performStockEntry(Item item, Integer quantity) {
        item.setStockAmount(item.getStockAmount() + quantity);
        repository.saveAndFlush(item);
    }

    @Resource
    ItemRepository repository;
}
