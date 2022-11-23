package com.spring.booking.service;

import com.spring.booking.model.Hotel;
import com.spring.booking.model.Room;
import com.spring.booking.model.RoomReservation;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    private HotelRepository hotelRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Room addRoom(Room room, Long hotelId) {
        Hotel foundHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "hotel not found"));
        foundHotel.getRoomList().add(room);
        room.setHotel(foundHotel);
        return roomRepository.save(room);
    }

    public void deleteRoomFromHotel(Long roomId, Long hotelId) {
        //TODO: eroare - nu pot sterge un parinte
//        Room foundRoom = roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
//        roomRepository.delete(foundRoom);

        Hotel foundHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "hotel not found"));
        Room foundRoom = roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
        foundRoom.getReservationList().clear();
        foundHotel.getRoomList().remove(foundRoom);
        roomRepository.save(foundRoom);
    }

    public List<Room> getAllRoomsByHotel(Long hotelId) {
        Hotel foundHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "hotel not found"));
        return roomRepository.findAllByHotel(foundHotel);
    }

    public Room getUpdateRoomWithNewPrice(Long roomId, Integer newPrice){
        Room foundRoom = roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
        foundRoom.setPrice(newPrice);
        return roomRepository.save(foundRoom);
    }
}
