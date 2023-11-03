package com.example.crowd_funding_backend.model.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EventQuery {

    private UUID owner;
    private String eventTitle;
    private String eventDesc;
    private String image;
    private String tag;
    private double targetAmount;
}
