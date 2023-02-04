package com.example.concurrencypractice.domain.example2;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Movie.class)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "startAt")
    private LocalDateTime startAt;

    @Column(name = "endAt")
    private LocalDateTime endAt;

    public static Screening createScreening(Movie movie, LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new RuntimeException();
        }
        return Screening.builder().movie(movie).startAt(startAt).endAt(endAt).build();
    }

    @Builder
    private Screening(Movie movie, LocalDateTime startAt, LocalDateTime endAt) {
        this.movie = movie;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
