package com.example.concurrencypractice.domain.example1;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "maximum_participant")
    private Integer maximumParticipant;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<Participant> participantList = new ArrayList<>();

    public Event(String name, Integer maximumParticipant) {
        this.name = name;
        this.maximumParticipant = maximumParticipant;
    }

    public boolean isClosed() {
        return participantList.size() >= maximumParticipant;
    }

}
