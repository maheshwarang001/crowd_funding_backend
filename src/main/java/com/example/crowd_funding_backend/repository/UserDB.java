package com.example.crowd_funding_backend.repository;

import com.example.crowd_funding_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDB extends JpaRepository<User, UUID>{


    User findByEmail(String email);

    User findByUserID(UUID id);

}
