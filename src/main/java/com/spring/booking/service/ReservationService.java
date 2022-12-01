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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Room> foundRooms = roomRepository.findAllById(addReservationDTO.getRoomIds());
        if (foundRooms.size() != addReservationDTO.getRoomIds().size()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one or more of the rooms was not found");
        }
        for (Room room : foundRooms) {
            if (!isAvailableRoom(room, addReservationDTO.getCheckIn(), addReservationDTO.getCheckOut())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of the rooms is not avaiable for requested period");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setCheckIn(addReservationDTO.getCheckIn());
        reservation.setCheckOut(addReservationDTO.getCheckOut());
        reservation.setUser(foundUser);
        foundRooms.forEach(currentRoom -> {
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

//        List<Room> availableRooms = new ArrayList<>();
        List<Hotel> foundHotels = hotelRepository.findAll();
//        for (Hotel hotel : foundHotels) {
//            for (Room room : hotel.getRoomList()) {
//                if (room.getNumberOfPerson() == numberOfPersons) {
//                    if (isAvailableRoom(room, startDate, endDate)) {
//                        availableRooms.add(room);
//                    }
//                }
//            }
//        }
//        return availableRooms;
        return foundHotels.stream()
                .flatMap(hotel -> hotel.getRoomList().stream())
                .filter(room -> room.getNumberOfPerson() == numberOfPersons)
                .filter(room -> isAvailableRoom(room, startDate, endDate))
                .collect(Collectors.toList());

    }

    public boolean isAvailableRoom(Room room, LocalDateTime startDate, LocalDateTime endDate) {
        if (room.getRoomReservationList().isEmpty()) {
            return true;
        } else {
            return hasRoomNonInterferingReservations(room, startDate, endDate);
        }
    }

    public boolean hasRoomNonInterferingReservations(Room room, LocalDateTime startDate, LocalDateTime endDate) {
        for (RoomReservation roomReservation : room.getRoomReservationList()) {
            if (!(roomReservation.getReservation().getCheckIn().isAfter(endDate) || roomReservation.getReservation().getCheckOut().isBefore(startDate))) {
                return false;
            }
        }
        return true;
    }

    public long getNumberOfAvailableRooms(LocalDateTime startDate, LocalDateTime endDate, Long idHotel) {
        Hotel foundHotel = hotelRepository.findById(idHotel).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the hotel was not found"));
//        int numberOfAvailableRooms = 0;
//        for (Room room : foundHotel.getRoomList()) {
//            if (isAvailableRoom(room, startDate, endDate)) {
//                numberOfAvailableRooms++;
//            }
//        }
//        return numberOfAvailableRooms;

        return foundHotel.getRoomList().stream()
                .filter(room -> isAvailableRoom(room, startDate, endDate))
                .count();
    }

    public long getPriceForAllReservationsBetween(LocalDateTime startDate, LocalDateTime endDate, Long idHotel) {
        Hotel foundHotel = hotelRepository.findById(idHotel).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the hotel was not found"));
        long totalPrice = 0;
        for (Room room : foundHotel.getRoomList()) {
            totalPrice += getPriceForARoomReservationBetween(room, startDate, endDate);
        }
        return totalPrice;
    }

    public long getPriceForARoomReservationBetween(Room room, LocalDateTime startDate, LocalDateTime endDate) {
        long totalNumberOfDaysReserved = 0;
        long numberOfDaysReserved = 0;
        for (RoomReservation roomReservation : room.getRoomReservationList()) {
            //am determinat numarul de zile rezervate aferente unei camere, intr-o anumita perioada data, in functie de
            //suprapunerea perioadei date (startDate, endDate) cu datele de checkIn si checkOut din rezervare
            //in functie de axa timpului avem 4 cazuri:
            //1. checkIn si checkOut sunt in afara perioadei date (adica checkIn e inainte de startDate si checkOut e dupa endDate)
            //2. checkIn si checkOut sunt in interiorul perioadei date
            //3. checkIn si checkOut sunt partial situate in intervalul dat, adica:
            //        -checkIn e intre startDate si endDate, iar checkOut e dupa endDate
            //4.      -checkIn e inainte de startDate si checkOut e intre startDate si endDate

            if (roomReservation.getReservation().getCheckIn().isBefore(startDate) && roomReservation.getReservation().getCheckOut().isAfter(endDate)) {
                numberOfDaysReserved = ChronoUnit.DAYS.between(startDate, endDate);
            }
            if (roomReservation.getReservation().getCheckIn().isAfter(startDate) && roomReservation.getReservation().getCheckOut().isBefore(endDate)) {
                numberOfDaysReserved = ChronoUnit.DAYS.between(roomReservation.getReservation().getCheckIn(), roomReservation.getReservation().getCheckOut());
            }
            if ((roomReservation.getReservation().getCheckIn().isAfter(startDate) && roomReservation.getReservation().getCheckIn().isBefore(endDate)) && roomReservation.getReservation().getCheckOut().isAfter(endDate)) {
                numberOfDaysReserved = ChronoUnit.DAYS.between(roomReservation.getReservation().getCheckIn(), endDate);
            }
            if (roomReservation.getReservation().getCheckIn().isBefore(startDate) && (roomReservation.getReservation().getCheckOut().isAfter(startDate) && roomReservation.getReservation().getCheckOut().isBefore(endDate))) {
                numberOfDaysReserved = ChronoUnit.DAYS.between(startDate, roomReservation.getReservation().getCheckOut());
            }
            totalNumberOfDaysReserved += numberOfDaysReserved;

        }
        return room.getPrice() * totalNumberOfDaysReserved;
    }

    public List<Room> getAvailableRoomsOrderedByPriceBy(LocalDateTime startDate, LocalDateTime endDate, Integer numberOfPersons) {
        List<Room> sortedListOfAvailableRooms = getAvailableRooms(startDate, endDate, numberOfPersons);
//        //OBS: clasa Room implementeaza Comparable<Room>
//        // in clasa Room am suprascris metoda compareTo (aceasta va face comparatia dupa pretul camerei)
//        Collections.sort(sortedListOfAvailableRooms);
//        return sortedListOfAvailableRooms;
        if (sortedListOfAvailableRooms.isEmpty() || sortedListOfAvailableRooms == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no available rooms");
        }
        return sortedListOfAvailableRooms.stream()
                .sorted(Comparator.comparingInt(Room::getPrice))
                .collect(Collectors.toList());
    }
}
