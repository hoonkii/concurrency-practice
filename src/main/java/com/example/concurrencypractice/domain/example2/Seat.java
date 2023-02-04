package com.example.concurrencypractice.domain.example2;

import com.example.concurrencypractice.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "seats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Screening.class)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    private String seatNo;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime dibsOnAt;

    private static final Integer dibsOnTime = 10;

    public static Seat createSeat(Screening screening, String seatNo) {
        return Seat.builder().screening(screening).seatNo(seatNo).member(null).dibsOnAt(null).build();
    }

    @Builder
    public Seat(Screening screening, String seatNo, Member member, LocalDateTime dibsOnAt) {
        this.screening = screening;
        this.seatNo = seatNo;
        this.member = member;
        this.dibsOnAt = dibsOnAt;
    }

    public void dipsOn(Member member) {
        checkIfDibsOn();
        this.member = member;
        this.dibsOnAt = LocalDateTime.now();
    }

    public void checkCanReserve(Member member) {
        if (this.member != member || dibsOnAt.isBefore(dibsOnAt.plusMinutes(dibsOnTime))) {
            throw new RuntimeException();
        }
    }

    private void checkIfDibsOn() {
        if (this.member == null && this.dibsOnAt == null) return;

        if (this.member != null && dibsOnAt.isBefore(dibsOnAt.plusMinutes(dibsOnTime))) {
            throw new RuntimeException("cannot dibs on");
        }
    }

}
