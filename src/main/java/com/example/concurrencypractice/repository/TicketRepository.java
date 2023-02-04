package com.example.concurrencypractice.repository;

import com.example.concurrencypractice.domain.example2.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
