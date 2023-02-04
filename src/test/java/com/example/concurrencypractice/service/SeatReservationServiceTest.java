package com.example.concurrencypractice.service;

import com.example.concurrencypractice.domain.Member;
import com.example.concurrencypractice.domain.example2.Movie;
import com.example.concurrencypractice.domain.example2.Screening;
import com.example.concurrencypractice.domain.example2.Seat;
import com.example.concurrencypractice.repository.MemberRepository;
import com.example.concurrencypractice.repository.MovieRepository;
import com.example.concurrencypractice.repository.ScreeningRepository;
import com.example.concurrencypractice.repository.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SeatReservationServiceTest {

    @Autowired
    private SeatReservationService service;

    private List<Member> members = new ArrayList<>();

    private Movie movie;

    private Screening screening;

    private Seat seat;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private SeatRepository seatRepository;

    @BeforeEach
    @Transactional
    void setUpData() {
        for (int i = 0; i < 5; i++) {
            Member member = Member.createMember("abcd", "abcd");
            member = memberRepository.save(member);
            members.add(member);
        }
        movie = new Movie("abcd", "acd");
        movie = movieRepository.save(movie);

        screening = Screening.createScreening(movie, LocalDateTime.MIN, LocalDateTime.MAX);
        screening = screeningRepository.save(screening);

        seat = Seat.createSeat(screening, "B1");
        seat = seatRepository.save(seat);
    }

    @Test
    void 동시_찜_실행() throws InterruptedException {
        // When
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);
        AtomicInteger exceptionCount = new AtomicInteger();
        for (int i = 0; i <= 4; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    service.dibsOnSeat(members.get(finalI), seat.getId());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    exceptionCount.addAndGet(1);
                }
                latch.countDown();
            });
        }
        latch.await();
        // Then
            // 하나의 트랜잭션을 제외한 나머지 트랜잭션은 에러 발생
        Assertions.assertEquals(exceptionCount.get(), 4);
        Optional<Seat> updatedSeat = seatRepository.findById(seat.getId());
        Assertions.assertNotNull(updatedSeat.get().getMember());
        Assertions.assertNotNull(updatedSeat.get().getDibsOnAt());
    }
}
