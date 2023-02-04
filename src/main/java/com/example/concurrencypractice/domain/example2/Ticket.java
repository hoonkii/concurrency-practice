package com.example.concurrencypractice.domain.example2;

import com.example.concurrencypractice.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = Seat.class)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "price")
    private Integer price;

    public static Ticket createTicket(Seat seat, Member member, PaymentMethod method, Integer price){
        // 현재 사용자와 같고,
        seat.checkCanReserve(member);
        return Ticket.builder().seat(seat)
                .paymentMethod(method)
                .price(price).build();

    }

}
