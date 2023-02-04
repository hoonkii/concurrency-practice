package com.example.concurrencypractice.repository;

import com.example.concurrencypractice.domain.example2.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
