package com.spring.booking.controller;

import com.spring.booking.model.Room;
import com.spring.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add/{hotelId}")
    public Room addRoom(@RequestBody Room room, @PathVariable Long hotelId) {
        return roomService.addRoom(room, hotelId);
    }
}
