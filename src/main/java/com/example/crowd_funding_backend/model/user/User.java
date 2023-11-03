package com.example.crowd_funding_backend.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;

    @Column(name = "firstName")
    private String fname;

    @Column(name = "lastName")
    private String lname;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "DOB")
    private String DOB;

    @Column(name = "password")
    private String password;

    @Column(name = "register_complete")
    private boolean cRegister;

    public User(String fname, String lname, String email, Address address, String DOB, boolean cRegister) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.DOB = DOB;
        this.cRegister = cRegister;
    }

    public User(UUID userID, String password) {
        this.userID = userID;
        this.password = password;
    }

}
