package com.example.crowd_funding_backend.model.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventID;
    private UUID owner;
    private String eventTitle;
    private String eventDesc;
    private String image;
    private String tag;
    private double targetAmount;
    private double cAmount;
    private double percent;
    private int totalPeople;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private boolean withdraw;
    private LocalDateTime wTime;
    private boolean open;

    public Event(UUID owner, String eventTitle, String eventDesc, String image, String tag, double targetAmount, double cAmount, double percent, int totalPeople, LocalDateTime createdAt, LocalDateTime closedAt, boolean withdraw, LocalDateTime wTime, boolean open) {
        this.owner = owner;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.image = image;
        this.tag = tag;
        this.targetAmount = targetAmount;
        this.cAmount = cAmount;
        this.percent = percent;
        this.totalPeople = totalPeople;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.withdraw = withdraw;
        this.wTime = wTime;
        this.open = open;
    }
}
