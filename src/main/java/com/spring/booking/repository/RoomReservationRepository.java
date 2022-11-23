package com.spring.booking.repository;

import com.spring.booking.model.Room;
import com.spring.booking.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    List<RoomReservation>findAllByRoom_Id(Room room);


    //List<RoomReservation>deleteAllByRoom_Id(Long roomId);
}
