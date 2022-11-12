package com.spring.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime checkIn;

    @Column
    private LocalDateTime checkOut;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "reservation",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RoomReservation> reservationList;

    public Reservation(){}

    public Long getId() {
        return id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RoomReservation> getRoomReservationList() {
        if (this.reservationList == null){
            this.reservationList = new ArrayList<>();
        }
        return reservationList;
    }

    public void setReservationList(List<RoomReservation> reservationList) {
        this.reservationList = reservationList;
    }
}
