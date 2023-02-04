package com.example.concurrencypractice.domain.example2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Movie(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
