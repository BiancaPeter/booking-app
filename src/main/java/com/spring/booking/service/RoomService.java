package com.spring.booking.service;

import com.spring.booking.model.Hotel;
import com.spring.booking.model.Room;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    private HotelRepository hotelRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Room addRoom(Room room, Long hotelId){
        Hotel foundHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "hotel not found"));
        foundHotel.getRoomList().add(room);
        room.setHotel(foundHotel);
        return roomRepository.save(room);
    }
}
