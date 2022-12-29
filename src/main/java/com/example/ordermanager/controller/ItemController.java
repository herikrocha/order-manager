package com.example.ordermanager.controller;

import com.example.ordermanager.dto.ItemDTO;
import com.example.ordermanager.service.ItemService;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Transactional
    @PostMapping
    public ResponseEntity<ItemDTO> save(@RequestBody @Valid ItemDTO itemDTO) {
        return ResponseEntity.ok(itemService.create(itemDTO));
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @Transactional
    @PutMapping
    public ResponseEntity<ItemDTO> update(@RequestBody @Valid ItemDTO itemDTO) {
        return ResponseEntity.ok(itemService.update(itemDTO));
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("itemId") Long itemId) {
        itemService.delete(itemId);
        return new ResponseEntity<>(AppConstants.ITEM_DELETED, HttpStatus.OK);
    }

    @Resource
    ItemService itemService;
}
