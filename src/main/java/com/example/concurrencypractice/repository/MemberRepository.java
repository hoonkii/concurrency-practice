package com.example.concurrencypractice.repository;

import com.example.concurrencypractice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
