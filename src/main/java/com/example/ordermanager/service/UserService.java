package com.example.ordermanager.service;

import com.example.ordermanager.dto.ItemDTO;
import com.example.ordermanager.dto.UserDTO;
import com.example.ordermanager.entity.Item;
import com.example.ordermanager.entity.User;
import com.example.ordermanager.exception.InvalidArgumentException;
import com.example.ordermanager.exception.ResourceNotFoundException;
import com.example.ordermanager.repository.UserRepository;
import com.example.ordermanager.utils.AppConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO dto) {
        if (repository.existsRoleByEmail(dto.getEmail())) {
            throw new InvalidArgumentException(AppConstants.INVALID_USER_EMAIL);
        }
        createUser(dto);
        return dto;
    }

    public UserDTO update(UserDTO dto) {
        User user = repository.findById(dto.getId()).orElseThrow(() -> new InvalidArgumentException(AppConstants.USER_NOT_FOUND));
        updateUser(user, dto);
        return dto;
    }

    public void delete(Long itemId) {
        if (!repository.existsById(itemId)) {
            throw new ResourceNotFoundException(AppConstants.ITEM_NOT_FOUND);
        }
        repository.deleteById(itemId);
    }

    private void createUser(UserDTO dto) {
        User user = User.builder().name(dto.getName()).email(dto.getEmail()).build();
        repository.saveAndFlush(user);
        dto.setId(user.getId());
    }

    private void updateUser(User user, UserDTO dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        repository.saveAndFlush(user);
    }

    @Resource
    UserRepository repository;
}
