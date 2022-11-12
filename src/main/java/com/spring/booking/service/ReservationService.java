package com.spring.booking.service;

import com.spring.booking.DTO.AddReservationDTO;
import com.spring.booking.model.Reservation;
import com.spring.booking.model.Room;
import com.spring.booking.model.RoomReservation;
import com.spring.booking.model.User;
import com.spring.booking.repository.ReservationRepository;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    private UserRepository userRepository;

    private RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository=roomRepository;
    }

    public Reservation addReservation(AddReservationDTO addReservationDTO){
        User foundUser = userRepository.findById(addReservationDTO.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Reservation reservation = new Reservation();
        reservation.setCheckIn(addReservationDTO.getCheckIn());
        reservation.setCheckOut(addReservationDTO.getCheckOut());
        reservation.setUser(foundUser);
        addReservationDTO.getRoomIds().forEach(roomId -> {
            Room currentRoom = roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setReservation(reservation);
            roomReservation.setRoom(currentRoom);
            reservation.getRoomReservationList().add(roomReservation);
        });
        return reservationRepository.save(reservation);
    }
}
