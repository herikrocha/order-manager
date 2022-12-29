package com.example.ordermanager.controller;

import com.example.ordermanager.dto.ItemDTO;
import com.example.ordermanager.dto.UserDTO;
import com.example.ordermanager.service.ItemService;
import com.example.ordermanager.service.UserService;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Transactional
    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO roleDTO) {
        return ResponseEntity.ok(userService.create(roleDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Transactional
    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO roleDTO) {
        return ResponseEntity.ok(userService.update(roleDTO));
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(AppConstants.USER_DELETED, HttpStatus.OK);
    }

    @Resource
    UserService userService;
}
