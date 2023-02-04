package com.example.concurrencypractice.service;

import com.example.concurrencypractice.domain.Member;
import com.example.concurrencypractice.domain.example1.Event;
import com.example.concurrencypractice.domain.example1.Participant;
import com.example.concurrencypractice.repository.EventRepository;
import com.example.concurrencypractice.repository.MemberRepository;
import com.example.concurrencypractice.repository.ParticipantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplyEventServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ApplyEventService applyEventService;

    private List<Member> members = new ArrayList<>();

    private Event event;

    @BeforeEach
    @Transactional
    void setUpData() {
        for (int i = 0; i < 100; i++) {
            Member member = Member.createMember("abcd", "abcd");
            member = memberRepository.save(member);
            members.add(member);
        }

        event = eventRepository.save(new Event("선착순 10명!", 10));
    }

    @Test
    void 선착순_이벤트_응모_테스트() throws InterruptedException {
        // when
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100);
        AtomicInteger exceptionCount = new AtomicInteger();
        for (Member member : members) {
            executorService.execute(() -> {
                try {
                    applyEventService.applyEvent(member, event.getId());
                } catch (Exception e) {
                    exceptionCount.addAndGet(1);
                }
                latch.countDown();
            });
        }
        latch.await();

        // Then
        Assertions.assertEquals(90, exceptionCount.get());
        List<Participant> participants = participantRepository.findByEvent(event);
        Assertions.assertEquals(event.getMaximumParticipant(), participants.size(), 10);
    }


}
