package com.spring.booking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String roomNumber;

    @Column
    private Integer price;

    @Column
    private Integer numberOfPerson;

    @OneToMany(mappedBy = "room",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RoomReservation> reservationList;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    public Room(){}

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(Integer numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public List<RoomReservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<RoomReservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
