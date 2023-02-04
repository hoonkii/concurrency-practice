package com.example.concurrencypractice.service;

import com.example.concurrencypractice.domain.Member;
import com.example.concurrencypractice.domain.example2.PaymentMethod;
import com.example.concurrencypractice.domain.example2.Seat;
import com.example.concurrencypractice.domain.example2.Ticket;
import com.example.concurrencypractice.repository.SeatRepository;
import com.example.concurrencypractice.repository.TicketRepository;
import com.example.concurrencypractice.service.external.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketingService {

    private final PaymentService paymentService;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Long reserveTicket(Member member, Long seatId, PaymentMethod method, Integer price) throws InterruptedException {
        Optional<Seat> seat = seatRepository.findById(seatId);

        Ticket ticket = Ticket.createTicket(seat.get(), member, method, price);
        paymentService.doPay();
        ticketRepository.save(ticket);
        return ticket.getId();
    }
}
