package com.example.crowd_funding_backend.model.role;

import com.example.crowd_funding_backend.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role_ID {

    @Id
    private Long id;
    private String name;
}
