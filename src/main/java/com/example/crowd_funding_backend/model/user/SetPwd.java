package com.example.crowd_funding_backend.model.user;

import lombok.Data;

import java.util.UUID;

@Data
public class SetPwd {
    private UUID id;
    private String pwd;
}
