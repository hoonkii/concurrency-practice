package com.example.concurrencypractice.repository;

import com.example.concurrencypractice.domain.example1.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
