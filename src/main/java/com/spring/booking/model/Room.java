package com.spring.booking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room implements Comparable<Room> {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String roomNumber;

    @Column
    private Integer price;

    @Column
    private Integer numberOfPerson;

    @OneToMany(mappedBy = "room",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference(value="room-roomreservation")
    private List<RoomReservation> roomReservationList;

    @ManyToOne
    @JsonBackReference(value = "hotel-room")
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    public Room(){}

    @Override
    public int compareTo(Room anotherRoom) {
        return Integer.compare(price, anotherRoom.getPrice());
    }

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

    public List<RoomReservation> getRoomReservationList() {
        return roomReservationList;
    }

    public void setRoomReservationList(List<RoomReservation> reservationList) {
        this.roomReservationList = reservationList;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
