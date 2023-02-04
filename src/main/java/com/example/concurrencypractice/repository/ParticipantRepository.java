package com.example.concurrencypractice.repository;

import com.example.concurrencypractice.domain.example1.Event;
import com.example.concurrencypractice.domain.example1.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByEvent(Event event);
}
