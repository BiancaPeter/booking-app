package com.spring.booking.service;

import com.spring.booking.DTO.AddReservationDTO;
import com.spring.booking.model.*;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.ReservationRepository;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    private UserRepository userRepository;

    private RoomRepository roomRepository;

    private HotelRepository hotelRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }


    public Reservation addReservation(AddReservationDTO addReservationDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        //verificam daca datele noastre interfereaza cu vreuna din rezervarile pentru fiecare camera


        Reservation reservation = new Reservation();
        reservation.setCheckIn(addReservationDTO.getCheckIn());
        reservation.setCheckOut(addReservationDTO.getCheckOut());
        reservation.setUser(foundUser);
        addReservationDTO.getRoomIds().forEach(roomId -> {
            Room currentRoom = roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setReservation(reservation);
            roomReservation.setRoom(currentRoom);
            roomReservation.setDateCreated(LocalDateTime.now());
            reservation.getRoomReservationList().add(roomReservation);
        });
        return reservationRepository.save(reservation);
    }

    public List<Room> getAvailableRooms(LocalDateTime startDate, LocalDateTime endDate, Integer numberOfPersons) {
        //ma duc prin toate hotelurile
        //ma duc prin toate camerele de la fiecare hotel
        //pentru o camera ma duc prin toate rezervarile
        //verific daca exista cel putin o rezervare care interfereaza cu startDate si endDate
        //o rezervare interfereaza cu sD si eD daca are checkin intre sD si eD sau daca are checkout intre sD si eD
        //daca nicio rezervare nu interfereaza cu sD si eD pentru camera curenta, adaug camera in lista rezultat (inseamna ca e available)

        List<Room> availableRooms = new ArrayList<>();
        List<Hotel> foundHotels = hotelRepository.findAll();
        for (Hotel hotel : foundHotels) {
            for (Room room : hotel.getRoomList()) {
                if (room.getNumberOfPerson() == numberOfPersons) {
                    if (room.getRoomReservationList().isEmpty()) {
                        availableRooms.add(room);
                    } else {
                        for (RoomReservation roomReservation : room.getRoomReservationList()) {
                            if ((roomReservation.getReservation().getCheckIn().isAfter(endDate) || roomReservation.getReservation().getCheckOut().isBefore(startDate)) && (!availableRooms.contains(room))) {
                                availableRooms.add(room);
                            }
                        }
                    }
                }

            }

        }
        return availableRooms;
    }
}
