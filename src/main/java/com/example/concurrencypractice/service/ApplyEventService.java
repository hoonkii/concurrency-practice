package com.example.concurrencypractice.service;

import com.example.concurrencypractice.domain.Member;
import com.example.concurrencypractice.domain.example1.Event;
import com.example.concurrencypractice.domain.example1.Participant;
import com.example.concurrencypractice.repository.EventRepository;
import com.example.concurrencypractice.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ApplyEventService {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final RedissonClient redissonClient;


    @Transactional
    public void applyEvent(Member member, Long eventId) {
        /*
         * 이벤트 응모 기능
         * 이벤트에는 응모 가능한 최대 멤버수가 정해져있습니다.
         * 여러 사람이 동시에 이벤트를 응모하려고 할 때 정확히 응모 가능한 최대 멤버수까지만 허용하기 위해서 Redis 기반의 분산 락을 활용합니다.
         * */
        RLock lock = redissonClient.getLock("event/" + eventId);
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("접속자가 많아 lock을 획득하지 못하였습니다. ");
            }
            /* 비즈니스 로직 */
            Event event = eventRepository.findById(eventId).orElseThrow();
            if (event.isClosed()) {
                throw new RuntimeException("마감 되었습니다.");
            }
            participantRepository.save(new Participant(event, member));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
