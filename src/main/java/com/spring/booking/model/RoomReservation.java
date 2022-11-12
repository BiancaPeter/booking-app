package com.spring.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RoomReservation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="reservation_id")
    private Reservation reservation;

    @Column
    private LocalDateTime dateCreated;

    public RoomReservation(){}

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
