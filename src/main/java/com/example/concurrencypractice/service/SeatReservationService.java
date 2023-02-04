package com.example.concurrencypractice.service;

import com.example.concurrencypractice.domain.Member;
import com.example.concurrencypractice.domain.example2.Seat;
import com.example.concurrencypractice.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatReservationService {

    private final SeatRepository seatRepository;

    @Transactional
    public void dibsOnSeat(Member member, Long seatId){
        /*
        * 좌석 찜 기능.
        * 하나의 좌석에는 가장 빨리 찜한 사용자가 점유할 수 있으며 그 이후 사용자는 점유할 수 없습니다.
        * 여러 사용자가 동시에 찜을 할 때의 동시성 문제를 해결하기 위해서 PESSIMISTIC_LOCK을 활용합니다.
        * */
        Optional<Seat> seat = seatRepository.findById(seatId);
        seat.get().dipsOn(member);
    }
}
