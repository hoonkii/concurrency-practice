package com.example.concurrencypractice.repository;

import com.example.concurrencypractice.domain.example2.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
