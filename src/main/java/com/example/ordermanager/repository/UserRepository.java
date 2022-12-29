package com.example.ordermanager.repository;

import com.example.ordermanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsRoleByEmail(String email);
    Optional<User> findById(Long userId);
}
