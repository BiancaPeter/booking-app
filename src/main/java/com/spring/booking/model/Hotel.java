package com.spring.booking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hotel {
    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column
    @ApiModelProperty(notes = "Hotel name", example = "Continental", required = true)
    private String name;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "hotel-room")
    @ApiModelProperty(hidden = true)
    private List<Room> roomList;

    public Hotel() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRoomList() {
        if (this.roomList == null) {
            this.roomList = new ArrayList<>();
        }
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
