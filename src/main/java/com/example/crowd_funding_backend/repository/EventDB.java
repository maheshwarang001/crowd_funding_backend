package com.example.crowd_funding_backend.repository;


import com.example.crowd_funding_backend.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.UUID;

public interface EventDB extends JpaRepository<Event, UUID> {

    Optional<List<Event>> findByOwner(UUID id);
}
